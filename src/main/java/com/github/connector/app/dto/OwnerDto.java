package com.github.connector.app.dto;

public class OwnerDto {
    private String login;

    public OwnerDto() {
    }

    public OwnerDto(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
