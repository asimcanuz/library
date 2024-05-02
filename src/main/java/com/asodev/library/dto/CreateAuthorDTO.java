package com.asodev.library.dto;

public class CreateAuthorDTO {
    private String firstName;
    private String lastName;

    public CreateAuthorDTO() {
    }
    public CreateAuthorDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
