package co.za.lungisani.translator;

import co.za.lungisani.persistance.model.Item;
import co.za.lungisani.persistance.model.Backlog;

import java.util.List;

public class TeamServiceTranslator {

    private TeamServiceTranslator() {
    }

    public static Backlog getBacklog(List<Item> itemsList) {
        return new Backlog().setItemsList(itemsList);
    }
}
