package co.za.household.translator;

import co.za.household.persistance.model.Backlog;
import co.za.household.persistance.model.Item;

import java.util.List;
import java.util.stream.Collectors;

public class BacklogTranslator {

    private BacklogTranslator() {
    }

    public static Backlog getBacklog(List<Item> itemsList) {
        return new Backlog().setItemsList(itemsList);
    }

    public static co.za.household.domain.model.Item getItemResponse(Item item) {
        return new co.za.household.domain.model.Item()
                .setItemName(item.getItemName())
                .setItemId(item.getItemId());
    }

    public static co.za.household.domain.model.Backlog getBacklog(Backlog backlog) {
        return new co.za.household.domain.model.Backlog()
                .setItemsList(backlog.getItemsList().stream()
                        .map(item -> new co.za.household.domain.model.Item()
                                .setItemName(item.getItemName())
                                .setItemId(item.getItemId()))
                        .collect(Collectors.toList()));
    }

    public static Item getItemEntity(co.za.household.domain.model.Item item) {
        return new Item().setItemName(item.getItemName());
    }
}
