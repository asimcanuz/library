package com.asodev.library.dto;

public class CreateAuthorDTO {
    private String name;

    public CreateAuthorDTO() {
    }

    public CreateAuthorDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
