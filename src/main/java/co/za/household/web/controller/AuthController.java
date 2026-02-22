package co.za.household.web.controller;

import co.za.household.domain.model.LoginRequest;
import co.za.household.domain.model.LoginResponse;
import co.za.household.domain.model.RegisterRequest;
import co.za.household.service.AuthService;
import co.za.household.web.constants.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Auth-Controller", description = "Authentication Controller")
@RestController
@RequestMapping(value = HttpConstants.AUTH_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login with email and password")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());
        ResponseEntity<LoginResponse> response = ResponseEntity.ok(authService.login(request));
        log.info("Login successful for email: {}", request.getEmail());
        return response;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        log.info("Registration attempt for email: {}", request.getEmail());
        ResponseEntity<LoginResponse> response = ResponseEntity.ok(authService.register(request));
        log.info("Registration successful for email: {}", request.getEmail());
        return response;
    }
}
