package com.github.connector.app.model;

import java.util.List;

public class RepoResponse extends Response {
    private List<Repo> repos;

    public RepoResponse(List<Repo> repos) {
        this.repos = repos;
    }

    public List<Repo> getRepos() {
        return repos;
    }
}
