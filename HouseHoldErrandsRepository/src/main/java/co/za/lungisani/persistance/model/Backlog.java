package co.za.lungisani.persistance.model;

import java.util.List;

public class Backlog {

    List<Item> itemsList;

    public List<Item> getItemsList() {
        return itemsList;
    }

    public Backlog setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
        return this;
    }
}
