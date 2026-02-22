package co.za.household.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Member data model", name = "MemberResponse")
public class MemberResponse {

    @Schema(description = "The member's user ID", example = "1")
    private Long userId;

    @Schema(description = "The member's email address", example = "john.smith@example.com")
    private String email;

    @Schema(description = "The member's first name", example = "John")
    private String firstName;

    @Schema(description = "The member's last name", example = "Smith")
    private String lastName;

    public Long getUserId() {
        return userId;
    }

    public MemberResponse setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MemberResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public MemberResponse setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public MemberResponse setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
