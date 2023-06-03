package com.github.connector.app.dto;

public class BranchDto {
    private String name;
    private CommitDto commit;

    public BranchDto() {
    }

    public BranchDto(String name, CommitDto commit) {
        this.name = name;
        this.commit = commit;
    }

    public String getName() {
        return name;
    }

    public CommitDto getCommit() {
        return commit;
    }
}
