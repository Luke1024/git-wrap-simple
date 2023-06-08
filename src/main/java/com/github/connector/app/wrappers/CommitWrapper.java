package com.github.connector.app.wrappers;

public class CommitWrapper {
    private String sha;

    public CommitWrapper() {
    }

    public CommitWrapper(String sha) {
        this.sha = sha;
    }

    public String getSha() {
        return sha;
    }
}
