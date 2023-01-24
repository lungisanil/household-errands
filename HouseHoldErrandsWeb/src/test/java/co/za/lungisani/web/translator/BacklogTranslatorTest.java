package co.za.lungisani.web.translator;

import co.za.lungisani.domain.model.Item;
import co.za.lungisani.domain.model.Backlog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static co.za.lungisani.web.constants.TestConstants.ITEM_FILE_PATH;
import static co.za.lungisani.web.constants.TestConstants.BACKLOG_FILE_PATH;
import static co.za.lungisani.web.util.MockServiceResponse.mockServiceResponse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
class BacklogTranslatorTest {

    @Test
    void getItemResponse() throws Exception{
        co.za.lungisani.persistance.model.Item item = mockServiceResponse(ITEM_FILE_PATH, co.za.lungisani.persistance.model.Item.class);

        Item itemResponse = TeamTranslator.getItemResponse(item);

        assertNotNull(itemResponse);
    }

    @Test
    void getBacklog() throws Exception{
        co.za.lungisani.persistance.model.Backlog backlogList = mockServiceResponse(BACKLOG_FILE_PATH, co.za.lungisani.persistance.model.Backlog.class);

        Backlog backlogListResponse = TeamTranslator.getBacklog(backlogList);

        assertNotNull(backlogListResponse);

    }
}