package co.za.lungisani.persistance.repository;


import co.za.lungisani.persistance.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogRepository extends JpaRepository<Item, Integer> {

    /**
     * Find an item by its Id
     * @param itemId - item Id
     * @return Item
     */
    Item findByItemId(final Integer itemId);
}
