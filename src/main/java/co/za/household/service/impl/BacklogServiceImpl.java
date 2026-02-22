package co.za.household.service.impl;

import co.za.household.domain.exceptions.InternalServerErrorException;
import co.za.household.domain.exceptions.NotFoundException;
import co.za.household.persistance.model.Backlog;
import co.za.household.persistance.model.Household;
import co.za.household.persistance.model.Item;
import co.za.household.persistance.repository.BacklogRepository;
import co.za.household.persistance.repository.HouseholdRepository;
import co.za.household.service.BacklogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BacklogServiceImpl implements BacklogService {

    private final BacklogRepository backlogRepository;
    private final HouseholdRepository householdRepository;

    public BacklogServiceImpl(BacklogRepository backlogRepository, HouseholdRepository householdRepository) {
        this.backlogRepository = backlogRepository;
        this.householdRepository = householdRepository;
    }

    @Override
    public Item retrieve(Long itemId, Long householdId) {
        log.debug("Retrieving item with id: {} for householdId: {}", itemId, householdId);
        return backlogRepository.findByItemIdAndHousehold_HouseholdId(itemId, householdId)
                .orElseThrow(() -> new NotFoundException("Cannot find the item"));
    }

    @Override
    public Backlog retrieveAll(Long householdId) {
        log.debug("Retrieving all items for householdId: {}", householdId);
        List<Item> items = backlogRepository.findAllByHousehold_HouseholdId(householdId);
        log.debug("Found {} items for householdId: {}", items.size(), householdId);
        return new Backlog().setItemsList(items);
    }

    @Override
    public void remove(Long itemId, Long householdId) {
        log.info("Removing item with id: {} for householdId: {}", itemId, householdId);
        if (backlogRepository.findByItemIdAndHousehold_HouseholdId(itemId, householdId).isEmpty()) {
            log.warn("Item not found for removal - itemId: {}, householdId: {}", itemId, householdId);
            throw new NotFoundException("Cannot find the item");
        }
        try {
            backlogRepository.deleteByItemIdAndHousehold_HouseholdId(itemId, householdId);
            log.info("Successfully removed item with id: {} for householdId: {}", itemId, householdId);
        } catch (Exception e) {
            log.error("Error removing item with id: {} for householdId: {}", itemId, householdId, e);
            throw new InternalServerErrorException("Could not remove the item");
        }
    }

    @Override
    public Item add(Item item, Long householdId) {
        log.info("Adding item: {} to householdId: {}", item.getItemName(), householdId);
        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new NotFoundException("Household not found"));
        item.setHousehold(household);
        try {
            Item saved = backlogRepository.save(item);
            log.info("Successfully added item with id: {} to householdId: {}", saved.getItemId(), householdId);
            return saved;
        } catch (Exception e) {
            log.error("Error adding item to householdId: {}", householdId, e);
            throw new InternalServerErrorException("Cannot add the new item");
        }
    }
}
