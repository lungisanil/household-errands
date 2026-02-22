package co.za.household.service;

import co.za.household.domain.exceptions.NotFoundException;
import co.za.household.domain.model.HouseholdRequest;
import co.za.household.domain.model.HouseholdResponse;
import co.za.household.domain.model.Item;
import co.za.household.domain.model.MemberResponse;
import co.za.household.persistance.model.Household;
import co.za.household.persistance.repository.HouseholdRepository;
import co.za.household.persistance.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HouseholdService {

    private final HouseholdRepository householdRepository;
    private final UserRepository userRepository;

    public HouseholdService(HouseholdRepository householdRepository, UserRepository userRepository) {
        this.householdRepository = householdRepository;
        this.userRepository = userRepository;
    }

    public HouseholdResponse create(HouseholdRequest request) {
        log.info("Creating household with name: {}", request.getName());
        Household household = new Household().setName(request.getName());
        householdRepository.save(household);
        log.info("Household created with id: {}", household.getHouseholdId());
        return toResponse(household);
    }

    public HouseholdResponse getById(Long householdId) {
        log.debug("Fetching household with id: {}", householdId);
        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new NotFoundException("Household not found"));
        return toResponse(household);
    }

    public List<HouseholdResponse> getAll() {
        log.debug("Fetching all households");
        List<HouseholdResponse> households = householdRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.debug("Found {} households", households.size());
        return households;
    }

    public HouseholdResponse update(Long householdId, HouseholdRequest request) {
        log.info("Updating household with id: {}", householdId);
        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new NotFoundException("Household not found"));
        household.setName(request.getName());
        householdRepository.save(household);
        log.info("Successfully updated household with id: {}", householdId);
        return toResponse(household);
    }

    public void delete(Long householdId) {
        log.info("Deleting household with id: {}", householdId);
        if (!householdRepository.existsById(householdId)) {
            log.warn("Household not found for deletion, id: {}", householdId);
            throw new NotFoundException("Household not found");
        }
        householdRepository.deleteById(householdId);
        log.info("Successfully deleted household with id: {}", householdId);
    }

    public List<MemberResponse> getMembers(Long householdId) {
        log.debug("Fetching members for householdId: {}", householdId);
        if (!householdRepository.existsById(householdId)) {
            throw new NotFoundException("Household not found");
        }
        List<MemberResponse> members = userRepository.findAllByHousehold_HouseholdId(householdId).stream()
                .map(u -> new MemberResponse()
                        .setUserId(u.getUserId())
                        .setEmail(u.getEmail())
                        .setFirstName(u.getFirstName())
                        .setLastName(u.getLastName()))
                .collect(Collectors.toList());
        log.debug("Found {} members for householdId: {}", members.size(), householdId);
        return members;
    }

    private HouseholdResponse toResponse(Household household) {
        List<Item> items = household.getItems().stream()
                .map(i -> new Item().setItemId(i.getItemId()).setItemName(i.getItemName()))
                .collect(Collectors.toList());

        List<MemberResponse> members = household.getMembers().stream()
                .map(u -> new MemberResponse()
                        .setUserId(u.getUserId())
                        .setEmail(u.getEmail())
                        .setFirstName(u.getFirstName())
                        .setLastName(u.getLastName()))
                .collect(Collectors.toList());

        return new HouseholdResponse()
                .setHouseholdId(household.getHouseholdId())
                .setName(household.getName())
                .setItems(items)
                .setMembers(members);
    }
}
