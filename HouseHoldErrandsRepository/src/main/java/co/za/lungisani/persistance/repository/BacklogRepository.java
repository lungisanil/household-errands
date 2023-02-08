package co.za.lungisani.persistance.repository;


import co.za.lungisani.persistance.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BacklogRepository extends JpaRepository<Item, Long> {

    /**
     * Find an item by its Id
     * @param itemId - item Id
     * @return Item
     */
    Item findByItemId(final Long itemId);

    /**
     * Remove an item by its Id
     * @param itemId - item Id
     */
    @Transactional
    void deleteByItemId(final Long itemId);
}
