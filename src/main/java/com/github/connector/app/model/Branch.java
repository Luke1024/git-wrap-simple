package com.github.connector.app.model;

public class Branch {
    private String name;
    private String lastCommitSHA;

    public Branch(String name, String lastCommitSHA) {
        this.name = name;
        this.lastCommitSHA = lastCommitSHA;
    }

    public String getName() {
        return name;
    }

    public String getLastCommitSHA() {
        return lastCommitSHA;
    }
}
