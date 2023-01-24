package co.za.lungisani.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(title = "Backlog data model", name = "Backlog")
public class Backlog {

    @Schema(
            title = "A list of items",
            example = "Stand, Juice"
    )
    List<Item> itemsList;

    public List<Item> getItemsList() {
        return itemsList;
    }

    public Backlog setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
        return this;
    }
}
