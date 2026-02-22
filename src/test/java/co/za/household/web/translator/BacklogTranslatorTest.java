package co.za.household.web.translator;

import co.za.household.domain.model.Item;
import co.za.household.domain.model.Backlog;
import co.za.household.translator.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static co.za.household.web.constants.TestConstants.ITEM_FILE_PATH;
import static co.za.household.web.constants.TestConstants.BACKLOG_FILE_PATH;
import static co.za.household.web.util.MockServiceResponse.mockServiceResponse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
class BacklogTranslatorTest {

    @Test
    void getItemResponse() throws Exception{
        co.za.household.persistance.model.Item item = mockServiceResponse(ITEM_FILE_PATH, co.za.household.persistance.model.Item.class);

        Item itemResponse = BacklogTranslator.getItemResponse(item);

        assertNotNull(itemResponse);
    }

    @Test
    void getBacklog() throws Exception{
        co.za.household.persistance.model.Backlog backlogList = mockServiceResponse(BACKLOG_FILE_PATH, co.za.household.persistance.model.Backlog.class);

        Backlog backlogListResponse = BacklogTranslator.getBacklog(backlogList);

        assertNotNull(backlogListResponse);

    }
}