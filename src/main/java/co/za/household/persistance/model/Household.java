package co.za.household.persistance.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HOUSEHOLDS")
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOUSEHOLD_ID", updatable = false, nullable = false)
    private Long householdId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    public Long getHouseholdId() {
        return householdId;
    }

    public Household setHouseholdId(Long householdId) {
        this.householdId = householdId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Household setName(String name) {
        this.name = name;
        return this;
    }

    public List<User> getMembers() {
        return members;
    }

    public Household setMembers(List<User> members) {
        this.members = members;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public Household setItems(List<Item> items) {
        this.items = items;
        return this;
    }
}
