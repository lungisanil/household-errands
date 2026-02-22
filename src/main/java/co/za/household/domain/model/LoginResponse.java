package co.za.household.domain.model;

public class LoginResponse {

    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private Long householdId;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public LoginResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public LoginResponse setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public LoginResponse setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public LoginResponse setHouseholdId(Long householdId) {
        this.householdId = householdId;
        return this;
    }
}
