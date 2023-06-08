package com.github.connector.app.dto;

public class BranchDto {
    private String name;
    private String lastCommitSHA;

    public BranchDto() {
    }

    public BranchDto(String name, String lastCommitSHA) {
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
