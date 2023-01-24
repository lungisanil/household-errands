package co.za.lungisani.service.translator;

import co.za.lungisani.persistance.model.Item;
import co.za.lungisani.persistance.model.Backlog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BacklogServiceTranslatorTest {

    @Test
    void getTeam() {
        Item itemOne = new Item().setItemId(1).setItemName("item1");
        Item itemTwo = new Item().setItemId(2).setItemName("item2");

        List<Item> itemsList = Arrays.asList(itemOne, itemTwo);

        Backlog backlogListResponse = TeamServiceTranslator.getBacklog(itemsList);

        assertNotNull(backlogListResponse);
    }

}