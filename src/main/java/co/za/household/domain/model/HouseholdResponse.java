package co.za.household.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(title = "Household data model", name = "HouseholdResponse")
public class HouseholdResponse {

    private Long householdId;
    private String name;
    private List<Item> items;
    private List<MemberResponse> members;

    public Long getHouseholdId() {
        return householdId;
    }

    public HouseholdResponse setHouseholdId(Long householdId) {
        this.householdId = householdId;
        return this;
    }

    public String getName() {
        return name;
    }

    public HouseholdResponse setName(String name) {
        this.name = name;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public HouseholdResponse setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public List<MemberResponse> getMembers() {
        return members;
    }

    public HouseholdResponse setMembers(List<MemberResponse> members) {
        this.members = members;
        return this;
    }
}
