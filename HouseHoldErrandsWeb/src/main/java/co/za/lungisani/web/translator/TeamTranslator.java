package co.za.lungisani.web.translator;

import co.za.lungisani.domain.model.Item;
import co.za.lungisani.persistance.model.Backlog;

import java.util.stream.Collectors;

public class TeamTranslator {

    private TeamTranslator() {
    }

    public static Item getItemResponse(co.za.lungisani.persistance.model.Item item) {
        return new Item().setItemName(item.getItemName()).setItemId(item.getItemId());
    }

    public static co.za.lungisani.domain.model.Backlog getBacklog(Backlog itemsList) {
        return new co.za.lungisani.domain.model.Backlog().setItemsList(itemsList.getItemsList().stream()
                .map(item -> new Item().setItemName(item.getItemName()))
                .collect(Collectors.toList()));
    }

    public static co.za.lungisani.persistance.model.Item getItemEntity(Item item){
        return new co.za.lungisani.persistance.model.Item().setItemName(item.getItemName());
    }

}
