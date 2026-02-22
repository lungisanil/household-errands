package co.za.household.web.controller;

import co.za.household.domain.model.Item;
import co.za.household.domain.model.SuccessResponse;
import co.za.household.persistance.model.Backlog;
import co.za.household.translator.BacklogTranslator;
import co.za.household.web.constants.HttpConstants;
import co.za.household.domain.model.ErrorResponse;
import co.za.household.service.BacklogService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Backlog-Controller", description = "Backlog Controller")
@RestController
@RequestMapping(value = HttpConstants.HOUSEHOLD_URI + "/{householdId}" + HttpConstants.BACKLOG_URI,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BacklogController {

    private final BacklogService backlogService;

    public BacklogController(BacklogService backlogService) {
        this.backlogService = backlogService;
    }

    @Operation(
            summary = "Get an item"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found an item", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Item.class))),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/item/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable Long householdId, @PathVariable Long itemId) {
        log.info("Fetching item with id: {} for householdId: {}", itemId, householdId);
        co.za.household.persistance.model.Item itemEntity = backlogService.retrieve(itemId, householdId);
        return ResponseEntity.ok(BacklogTranslator.getItemResponse(itemEntity));
    }


    @Operation(
            summary = "Get a backlog"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the backlog", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = co.za.household.domain.model.Backlog.class))),
            @ApiResponse(responseCode = "404", description = "Backlog not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public ResponseEntity<co.za.household.domain.model.Backlog> getBacklog(@PathVariable Long householdId) {
        log.info("Fetching backlog for householdId: {}", householdId);
        Backlog itemsList = backlogService.retrieveAll(householdId);
        return ResponseEntity.ok(BacklogTranslator.getBacklog(itemsList));
    }


    @Operation(
            summary = "Remove an item"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully removed an item", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<SuccessResponse> removeItem(@PathVariable Long householdId, @PathVariable Long itemId) {
        log.info("Removing item with id: {} for householdId: {}", itemId, householdId);
        backlogService.remove(itemId, householdId);
        log.info("Successfully removed item with id: {} for householdId: {}", itemId, householdId);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Add an item"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added an item", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Item.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/item")
    public ResponseEntity<Item> addItem(@PathVariable Long householdId, @RequestBody Item item) {
        log.info("Adding item: {} to householdId: {}", item.getItemName(), householdId);
        co.za.household.persistance.model.Item itemEntity = BacklogTranslator.getItemEntity(item);
        co.za.household.persistance.model.Item savedItem = backlogService.add(itemEntity, householdId);
        log.info("Successfully added item with id: {} to householdId: {}", savedItem.getItemId(), householdId);
        return ResponseEntity.status(HttpStatus.CREATED).body(BacklogTranslator.getItemResponse(savedItem));
    }
}
