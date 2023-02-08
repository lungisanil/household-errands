package co.za.lungisani.persistance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEMS")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ITEM_ID", updatable = false, nullable = false)
    private Long itemId;

    @Column(name = "ITEM_NAME", nullable = false)
    private String itemName;

    public Long getItemId() {
        return itemId;
    }

    public Item setItemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public Item setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                '}';
    }

}
