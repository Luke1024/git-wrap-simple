package com.github.connector.app.wrappers;

public class OwnerWrapper {
    private String login;

    public OwnerWrapper() {
    }

    public OwnerWrapper(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
