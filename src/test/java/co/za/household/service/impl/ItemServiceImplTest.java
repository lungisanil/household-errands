package co.za.household.service.impl;

import co.za.household.domain.exceptions.InternalServerErrorException;
import co.za.household.domain.exceptions.NotFoundException;
import co.za.household.persistance.model.Backlog;
import co.za.household.persistance.model.Household;
import co.za.household.persistance.model.Item;
import co.za.household.persistance.repository.BacklogRepository;
import co.za.household.persistance.repository.HouseholdRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    private static final Long HOUSEHOLD_ID = 1L;

    @Mock
    private BacklogRepository backlogRepository;

    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private BacklogServiceImpl backlogServiceImpl;

    @Test
    public void testThatAnItemIsRetrievedSuccessful() {
        Item stubbedItem = new Item().setItemId(1L).setItemName("T");

        when(backlogRepository.findByItemIdAndHousehold_HouseholdId(1L, HOUSEHOLD_ID))
                .thenReturn(Optional.of(stubbedItem));

        Item item = backlogServiceImpl.retrieve(1L, HOUSEHOLD_ID);

        verify(backlogRepository, times(1)).findByItemIdAndHousehold_HouseholdId(anyLong(), anyLong());
        assertEquals(stubbedItem, item);
    }

    @Test
    public void testThatBacklogIsRetrievedSuccessful() {
        Item stubbedItem1 = new Item().setItemId(1L).setItemName("T");
        Item stubbedItem2 = new Item().setItemId(2L).setItemName("B");
        List<Item> itemList = Arrays.asList(stubbedItem1, stubbedItem2);

        when(backlogRepository.findAllByHousehold_HouseholdId(HOUSEHOLD_ID)).thenReturn(itemList);

        Backlog backlog = backlogServiceImpl.retrieveAll(HOUSEHOLD_ID);

        verify(backlogRepository, times(1)).findAllByHousehold_HouseholdId(anyLong());
        assertEquals(2, backlog.getItemsList().size());
    }

    @Test
    public void testThatAnItemIsRemovedSuccessful() {
        Item stubbedItem = new Item().setItemId(1L).setItemName("T");

        when(backlogRepository.findByItemIdAndHousehold_HouseholdId(1L, HOUSEHOLD_ID))
                .thenReturn(Optional.of(stubbedItem));
        Mockito.doNothing().when(backlogRepository).deleteByItemIdAndHousehold_HouseholdId(1L, HOUSEHOLD_ID);

        backlogServiceImpl.remove(1L, HOUSEHOLD_ID);

        verify(backlogRepository, times(1)).deleteByItemIdAndHousehold_HouseholdId(anyLong(), anyLong());
    }

    @Test
    public void testThatAnItemIsAddedSuccessful() {
        Household household = new Household().setHouseholdId(HOUSEHOLD_ID).setName("Test");
        Item stubbedItem = new Item().setItemId(1L).setItemName("T");

        when(householdRepository.findById(HOUSEHOLD_ID)).thenReturn(Optional.of(household));
        when(backlogRepository.save(any(Item.class))).thenReturn(stubbedItem);

        Item addedItem = backlogServiceImpl.add(stubbedItem, HOUSEHOLD_ID);

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(backlogRepository, times(1)).save(any(Item.class));
        verify(backlogRepository).save(captor.capture());
        assertEquals(stubbedItem.getItemName(), addedItem.getItemName());
    }

    @Test
    public void testThatExceptionIsThrownWhenItemIsNotFound() {
        when(backlogRepository.findByItemIdAndHousehold_HouseholdId(1L, HOUSEHOLD_ID))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> backlogServiceImpl.retrieve(1L, HOUSEHOLD_ID));
    }

    @Test
    public void testThatExceptionIsThrownWhenItemCannotBeRemoved() {
        when(backlogRepository.findByItemIdAndHousehold_HouseholdId(1L, HOUSEHOLD_ID))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> backlogServiceImpl.remove(1L, HOUSEHOLD_ID));
    }

    @Test
    public void testThatExceptionIsThrownWhenItemCannotBeAdded() {
        Household household = new Household().setHouseholdId(HOUSEHOLD_ID).setName("Test");

        when(householdRepository.findById(HOUSEHOLD_ID)).thenReturn(Optional.of(household));
        when(backlogRepository.save(any(Item.class)))
                .thenThrow(new InternalServerErrorException("Cannot add the new item"));

        Item item = new Item().setItemName("T");
        assertThrows(InternalServerErrorException.class, () -> backlogServiceImpl.add(item, HOUSEHOLD_ID));
    }

}