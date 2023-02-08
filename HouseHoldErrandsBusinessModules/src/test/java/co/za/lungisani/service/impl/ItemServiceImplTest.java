package co.za.lungisani.service.impl;

import co.za.lungisani.domain.exceptions.InternalServerErrorException;
import co.za.lungisani.domain.exceptions.NotFoundException;
import co.za.lungisani.persistance.model.Backlog;
import co.za.lungisani.persistance.model.Item;
import co.za.lungisani.persistance.repository.BacklogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private BacklogRepository backlogRepository;

    @InjectMocks
    private BacklogServiceImpl teamServiceImpl;

    @Test
    public void testThatAnItemIsRetrievedSuccessful() {
        Item stubbedItem = new Item()
                .setItemId(1L)
                .setItemName("T");

        //Stubbing a backlog repository behavior
        when(this.backlogRepository.findByItemId(1L)).thenReturn(stubbedItem);

        //making a service call to get an item for the itemId in question
        Item item = this.teamServiceImpl.retrieve(1L);
        verify(this.backlogRepository, times(1)).findByItemId(anyLong());
        assertEquals(stubbedItem, item);
    }

    @Test
    public void testThatBacklogIsRetrievedSuccessful() {
        Item stubbedItem1 = new Item()
                .setItemId(1L)
                .setItemName("T");

        Item stubbedItem2 = new Item()
                .setItemId(2L)
                .setItemName("B");

        List<Item> backlog1 = Arrays.asList(stubbedItem1, stubbedItem2);

        //Stubbing a backlog repository behavior
        when(this.backlogRepository.findAll()).thenReturn(backlog1);

        //making a service call to get backlog
        final Backlog backlog2 = this.teamServiceImpl.retrieveAll();
        verify(this.backlogRepository, times(1)).findAll();
        assertEquals(2, backlog2.getItemsList().size());
    }

    @Test
    public void testThatAnItemIsRemovedSuccessful() {
        //Stubbing a backlog repository behavior
        Mockito.doNothing().when(this.backlogRepository).deleteByItemId(1L);

        //making a service call to get an item for the itemId in question
        this.teamServiceImpl.remove(1L);
        verify(this.backlogRepository, times(1)).deleteByItemId(anyLong());
    }

    @Test
    public void testThatAnItemIsAddedSuccessful() {
        Item stubbedItem = new Item()
                .setItemId(1L)
                .setItemName("T");

        //Stubbing a backlog repository behavior
        when(this.backlogRepository.save(stubbedItem)).thenReturn(stubbedItem);

        //making a service call to get an item for the itemId in question
        Item addedItem = this.teamServiceImpl.add(stubbedItem);

        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);

        verify(this.backlogRepository, times(1)).save(any(Item.class));
        verify(this.backlogRepository).save(itemArgumentCaptor.capture());
        assertEquals(stubbedItem.getItemName(), addedItem.getItemName());
    }

    @Test
    public void testThatExceptionIsThrownWhenItemIsNotFound() {
        //Stubbing a backlog repository behavior
        when(this.backlogRepository.findByItemId(1L)).thenReturn(null);

        try {
            //making a service call to get an item for the itemId in question
            this.teamServiceImpl.retrieve(1L);
        } catch (Exception ex) {
            verify(this.backlogRepository, times(1)).findByItemId(anyLong());
            assertEquals(NotFoundException.class, ex.getClass());
        }
    }

    @Test
    public void testThatExceptionIsThrownWhenBacklogIsNotFound() {
        //Stubbing a backlog repository behavior
        when(this.backlogRepository.findAll()).thenReturn(null);

        try {
            //making a service call to get the items list
            this.teamServiceImpl.retrieveAll();
        } catch (Exception ex) {
            verify(this.backlogRepository, times(1)).findAll();
            assertEquals(NotFoundException.class, ex.getClass());
        }
    }

    @Test
    public void testThatExceptionIsThrownWhenItemCannotBeRemoved() {
        //Stubbing a backlog repository behavior
        Mockito.doThrow(new NotFoundException("Cannot find the item")).when(this.backlogRepository).deleteByItemId(1L);

        try {
            //making a service call to get an item for the itemId in question
            this.teamServiceImpl.remove(1L);
        } catch (Exception ex) {
            verify(this.backlogRepository, times(1)).deleteByItemId(anyLong());
            assertEquals(NotFoundException.class, ex.getClass());
        }
    }

    @Test
    public void testThatExceptionIsThrownWhenItemCannotBeAdded() {
        Item stubbedItem = new Item()
                .setItemId(1L)
                .setItemName("T");

        //Stubbing a backlog repository behavior
        when(this.backlogRepository.save(stubbedItem)).thenThrow(new InternalServerErrorException("Cannot add the new item"));

        try {
            //making a service call to get an item for the itemId in question
            this.teamServiceImpl.add(stubbedItem);
        } catch (Exception ex) {
            verify(this.backlogRepository, times(1)).save(any(Item.class));
            assertEquals(InternalServerErrorException.class, ex.getClass());
        }
    }

}