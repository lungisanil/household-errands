package co.za.lungisani.service.impl;

import co.za.lungisani.domain.exceptions.NotFoundException;
import co.za.lungisani.persistance.model.Item;
import co.za.lungisani.persistance.repository.BacklogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
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
    public void testThatAnArtistIsRetrievedSuccessful() {
        Item stubbedItem = new Item()
                .setItemId(1)
                .setItemName("T");

        //Stubbing a backlog repository behavior
        when(this.backlogRepository.findByItemId(1)).thenReturn(stubbedItem);

        //making a service call to get an item for the itemId in question
        Item item = this.teamServiceImpl.retrieve(1);
        verify(this.backlogRepository, times(1)).findByItemId(anyInt());
        assertEquals(stubbedItem, item);
    }

    @Test
    public void testThatExceptionIsThrownWhenMemberIsNotFound() {
        //Stubbing a backlog repository behavior
        when(this.backlogRepository.findByItemId(1)).thenReturn(null);

        try {
            //making a service call to get an item for the itemId in question
            this.teamServiceImpl.retrieve(1);
        } catch (Exception ex) {
            verify(this.backlogRepository, times(1)).findByItemId(anyInt());
            assertEquals(NotFoundException.class, ex.getClass());
        }
    }

    @Test
    public void testThatExceptionIsThrownWhenTeamIsNotFound() {
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

}