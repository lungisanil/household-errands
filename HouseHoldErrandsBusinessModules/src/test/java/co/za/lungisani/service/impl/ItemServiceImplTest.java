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
    public void testThatAnArtistIsRetrievedSuccessful() {
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
    public void testThatExceptionIsThrownWhenMemberIsNotFound() {
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