package co.za.lungisani.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(title = "Item data model", name = "Item")
public class Item {

    @Schema(
            title = "The name representing an item",
            example = "Toaster"
    )
    private String itemName;

    @Schema(
            title = "The Id representing an item",
            example = "1"
    )
    private Long itemId;

    public String getItemName() {
        return itemName;
    }

    public Item setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public Long getItemId() {
        return itemId;
    }

    public Item setItemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

}
