package co.za.household.domain.model;

public class RegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Long householdId;

    public String getEmail() {
        return email;
    }

    public RegisterRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public RegisterRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public RegisterRequest setHouseholdId(Long householdId) {
        this.householdId = householdId;
        return this;
    }
}
