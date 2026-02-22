package co.za.household.persistance.repository;

import co.za.household.persistance.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BacklogRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByItemIdAndHousehold_HouseholdId(Long itemId, Long householdId);

    List<Item> findAllByHousehold_HouseholdId(Long householdId);

    @Transactional
    void deleteByItemIdAndHousehold_HouseholdId(Long itemId, Long householdId);
}


