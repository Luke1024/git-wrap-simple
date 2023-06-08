package com.github.connector.app.model;

import java.util.List;

public class Repository {
    private String repoName;
    private String ownerLogin;
    private List<Branch> branches;

    public Repository(String repoName, String ownerLogin, List<Branch> branches) {
        this.repoName = repoName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
