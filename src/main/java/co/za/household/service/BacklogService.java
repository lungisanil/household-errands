package co.za.household.service;

import co.za.household.persistance.model.Backlog;
import co.za.household.persistance.model.Item;

public interface BacklogService {

    Item retrieve(Long itemId, Long householdId);

    Backlog retrieveAll(Long householdId);

    void remove(Long itemId, Long householdId);

    Item add(Item item, Long householdId);
}
