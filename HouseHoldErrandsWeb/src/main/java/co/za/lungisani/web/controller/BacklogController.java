package co.za.lungisani.web.controller;

import co.za.lungisani.domain.model.Item;
import co.za.lungisani.domain.model.SuccessResponse;
import co.za.lungisani.persistance.model.Backlog;
import co.za.lungisani.web.constants.HttpConstants;
import co.za.lungisani.domain.model.ErrorResponse;
import co.za.lungisani.service.BacklogService;
import co.za.lungisani.web.translator.TeamTranslator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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


@Tag(name = "Backlog-Controller", description = "Backlog Controller")
@RestController
@RequestMapping(value = HttpConstants.BACKLOG_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class BacklogController {

    private final BacklogService backlogService;

    @Autowired
    public BacklogController(@Qualifier("backlogServiceImpl") BacklogService backlogService) {
        this.backlogService = backlogService;
    }

    @Operation(
            summary = "Get an item"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found an item", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Item.class)))),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/item/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable Long itemId) {
        co.za.lungisani.persistance.model.Item itemEntity = this.backlogService.retrieve(itemId);
        Item item = TeamTranslator.getItemResponse(itemEntity);

        return ResponseEntity.status(HttpStatus.OK).body(item);
    }


    @Operation(
            summary = "Get a backlog"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the team", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = co.za.lungisani.domain.model.Backlog.class)))),
            @ApiResponse(responseCode = "404", description = "team list not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public ResponseEntity<co.za.lungisani.domain.model.Backlog> getBacklog() {
        Backlog itemsList = this.backlogService.retrieveAll();
        co.za.lungisani.domain.model.Backlog backlog = TeamTranslator.getBacklog(itemsList);

        return ResponseEntity.status(HttpStatus.OK).body(backlog);
    }


    @Operation(
            summary = "Remove an item"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully removed an item", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<SuccessResponse> removeItem(@PathVariable Long itemId) {
        this.backlogService.remove(itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new SuccessResponse());
    }


    @Operation(
            summary = "Add an item"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added an item", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Item.class)))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/item")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {

        co.za.lungisani.persistance.model.Item itemEntity = TeamTranslator.getItemEntity(item);
        co.za.lungisani.persistance.model.Item savedItemEntity = this.backlogService.add(itemEntity);

        Item savedItem = TeamTranslator.getItemResponse(savedItemEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

}
