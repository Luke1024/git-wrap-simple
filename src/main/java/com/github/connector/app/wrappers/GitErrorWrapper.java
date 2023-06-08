package com.github.connector.app.wrappers;

public class GitErrorWrapper {
    private String message;

    public GitErrorWrapper() {
    }

    public GitErrorWrapper(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
