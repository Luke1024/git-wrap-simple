package com.github.connector.app.dto;

public class CommitDto {
    private String sha;

    public CommitDto() {
    }

    public CommitDto(String sha) {
        this.sha = sha;
    }

    public String getSha() {
        return sha;
    }
}
