package co.za.household.web.controller;

import co.za.household.domain.model.HouseholdRequest;
import co.za.household.domain.model.HouseholdResponse;
import co.za.household.domain.model.MemberResponse;
import co.za.household.service.HouseholdService;
import co.za.household.web.constants.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "Household-Controller", description = "Household Controller")
@RestController
@RequestMapping(value = HttpConstants.HOUSEHOLD_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @Operation(summary = "Create a new household")
    @PostMapping
    public ResponseEntity<HouseholdResponse> create(@RequestBody HouseholdRequest request) {
        log.info("Creating household with name: {}", request.getName());
        HouseholdResponse response = householdService.create(request);
        log.info("Household created with id: {}", response.getHouseholdId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a household by ID")
    @GetMapping("/{householdId}")
    public ResponseEntity<HouseholdResponse> getById(@PathVariable Long householdId) {
        log.info("Fetching household with id: {}", householdId);
        return ResponseEntity.ok(householdService.getById(householdId));
    }

    @Operation(summary = "Get all households")
    @GetMapping
    public ResponseEntity<List<HouseholdResponse>> getAll() {
        log.info("Fetching all households");
        List<HouseholdResponse> households = householdService.getAll();
        log.info("Returning {} households", households.size());
        return ResponseEntity.ok(households);
    }

    @Operation(summary = "Update a household")
    @PutMapping("/{householdId}")
    public ResponseEntity<HouseholdResponse> update(@PathVariable Long householdId,
                                                     @RequestBody HouseholdRequest request) {
        log.info("Updating household with id: {}", householdId);
        HouseholdResponse response = householdService.update(householdId, request);
        log.info("Successfully updated household with id: {}", householdId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a household")
    @DeleteMapping("/{householdId}")
    public ResponseEntity<Void> delete(@PathVariable Long householdId) {
        log.info("Deleting household with id: {}", householdId);
        householdService.delete(householdId);
        log.info("Successfully deleted household with id: {}", householdId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all members of a household")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved members",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MemberResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Household not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{householdId}/members")
    public ResponseEntity<List<MemberResponse>> getMembers(@PathVariable Long householdId) {
        log.info("Fetching members for householdId: {}", householdId);
        List<MemberResponse> members = householdService.getMembers(householdId);
        log.info("Returning {} members for householdId: {}", members.size(), householdId);
        return ResponseEntity.ok(members);
    }
}
