package co.za.household.service;

import co.za.household.domain.model.LoginRequest;
import co.za.household.domain.model.LoginResponse;
import co.za.household.domain.model.RegisterRequest;
import co.za.household.domain.exceptions.BadRequestException;
import co.za.household.domain.exceptions.NotFoundException;
import co.za.household.persistance.model.Household;
import co.za.household.persistance.model.User;
import co.za.household.persistance.repository.HouseholdRepository;
import co.za.household.persistance.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final HouseholdRepository householdRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthService(UserRepository userRepository,
                       HouseholdRepository householdRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.householdRepository = householdRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public LoginResponse login(LoginRequest request) {
        log.debug("Authenticating user with email: {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);
        log.debug("JWT token generated for user: {}", user.getEmail());

        return new LoginResponse()
                .setToken(token)
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setHouseholdId(user.getHousehold() != null ? user.getHousehold().getHouseholdId() : null);
    }

    public LoginResponse register(RegisterRequest request) {
        log.debug("Registering new user with email: {}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed - email already in use: {}", request.getEmail());
            throw new BadRequestException("Email already in use");
        }

        Household household = null;
        if (request.getHouseholdId() != null) {
            log.debug("Linking user to householdId: {}", request.getHouseholdId());
            household = householdRepository.findById(request.getHouseholdId())
                    .orElseThrow(() -> new NotFoundException("Household not found"));
        }

        User user = new User()
                .setEmail(request.getEmail())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setHousehold(household);

        userRepository.save(user);
        log.debug("User saved with email: {}", user.getEmail());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return new LoginResponse()
                .setToken(token)
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setHouseholdId(household != null ? household.getHouseholdId() : null);
    }
}
