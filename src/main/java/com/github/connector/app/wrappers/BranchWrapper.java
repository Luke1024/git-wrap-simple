package com.github.connector.app.wrappers;

public class BranchWrapper {
    private String name;
    private CommitWrapper commit;

    public BranchWrapper() {
    }

    public BranchWrapper(String name, CommitWrapper commit) {
        this.name = name;
        this.commit = commit;
    }

    public String getName() {
        return name;
    }

    public CommitWrapper getCommit() {
        return commit;
    }
}
