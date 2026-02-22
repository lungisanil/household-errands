package co.za.household.web.controller;

import co.za.household.domain.model.Item;
import co.za.household.domain.model.Backlog;
import co.za.household.service.BacklogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static co.za.household.web.constants.TestConstants.ITEM_FILE_PATH;
import static co.za.household.web.constants.TestConstants.BACKLOG_FILE_PATH;
import static co.za.household.web.util.MockServiceResponse.mockServiceResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    private static final Long HOUSEHOLD_ID = 1L;

    @Mock
    private BacklogService backlogService;

    @InjectMocks
    private BacklogController backlogController;

    @Test
    public void testThatAnItemIsRetrievedSuccessfully() throws Exception {
        co.za.household.persistance.model.Item item = mockServiceResponse(ITEM_FILE_PATH, co.za.household.persistance.model.Item.class);

        when(backlogService.retrieve(4L, HOUSEHOLD_ID)).thenReturn(item);

        Item itemResponse = backlogController.getItem(HOUSEHOLD_ID, 4L).getBody();

        verify(backlogService, times(1)).retrieve(anyLong(), anyLong());
        assertNotNull(itemResponse);
        assertEquals(item.getItemName(), itemResponse.getItemName());
    }

    @Test
    public void testThatTheBacklogIsRetrievedSuccessfully() throws Exception {
        co.za.household.persistance.model.Backlog backlog = mockServiceResponse(BACKLOG_FILE_PATH, co.za.household.persistance.model.Backlog.class);

        when(backlogService.retrieveAll(HOUSEHOLD_ID)).thenReturn(backlog);

        Backlog backlogResponse = backlogController.getBacklog(HOUSEHOLD_ID).getBody();

        verify(backlogService, times(1)).retrieveAll(anyLong());
        assertNotNull(backlogResponse);
        assertEquals(4, backlogResponse.getItemsList().size());
    }

    @Test
    public void testThatAnItemIsRemovedSuccessfully() {
        doNothing().when(backlogService).remove(4L, HOUSEHOLD_ID);

        backlogController.removeItem(HOUSEHOLD_ID, 4L);

        verify(backlogService, times(1)).remove(anyLong(), anyLong());
    }

    @Test
    public void testThatAnItemIsAddedSuccessfully() throws Exception {
        Item stubbedItem = new Item().setItemName("L");
        co.za.household.persistance.model.Item item = mockServiceResponse(ITEM_FILE_PATH, co.za.household.persistance.model.Item.class);

        when(backlogService.add(any(co.za.household.persistance.model.Item.class), anyLong())).thenReturn(item);

        Item itemResponse = backlogController.addItem(HOUSEHOLD_ID, stubbedItem).getBody();

        verify(backlogService, times(1)).add(any(co.za.household.persistance.model.Item.class), anyLong());
        assertNotNull(itemResponse);
        assertEquals(item.getItemName(), itemResponse.getItemName());
    }

}