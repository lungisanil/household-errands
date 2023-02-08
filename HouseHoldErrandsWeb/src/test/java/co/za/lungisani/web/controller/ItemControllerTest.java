package co.za.lungisani.web.controller;

import co.za.lungisani.domain.model.Item;
import co.za.lungisani.domain.model.Backlog;
import co.za.lungisani.service.BacklogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static co.za.lungisani.web.constants.TestConstants.ITEM_FILE_PATH;
import static co.za.lungisani.web.constants.TestConstants.BACKLOG_FILE_PATH;
import static co.za.lungisani.web.util.MockServiceResponse.mockServiceResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private BacklogService backlogService;

    @InjectMocks
    private BacklogController backlogController;

    @Test
    public void testThatAnItemIsRetrievedSuccessfully() throws Exception {
        co.za.lungisani.persistance.model.Item item = mockServiceResponse(ITEM_FILE_PATH, co.za.lungisani.persistance.model.Item.class);

        when(this.backlogService.retrieve(4L)).thenReturn(item);

        Item itemResponse = this.backlogController.getItem(4L).getBody();

        verify(this.backlogService, times(1)).retrieve(anyLong());
        assertNotNull(itemResponse);
        assertEquals(item.getItemName(), itemResponse.getItemName());
    }

    @Test
    public void testThatTheBacklogIsRetrievedSuccessfully() throws Exception {
        co.za.lungisani.persistance.model.Backlog backlog = mockServiceResponse(BACKLOG_FILE_PATH, co.za.lungisani.persistance.model.Backlog.class);

        when(this.backlogService.retrieveAll()).thenReturn(backlog);

        Backlog backlogResponse = this.backlogController.getBacklog().getBody();

        verify(this.backlogService, times(1)).retrieveAll();
        assertNotNull(backlogResponse);
        assertEquals(4, backlogResponse.getItemsList().size());
    }

}