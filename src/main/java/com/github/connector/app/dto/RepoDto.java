package com.github.connector.app.dto;

public class RepoDto {
    private String name;
    private OwnerDto owner;
    private boolean fork;
    private String branches_url;

    public RepoDto() {
    }

    public RepoDto(String name, OwnerDto owner, boolean fork, String branches_url) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
        this.branches_url = branches_url;
    }

    public String getName() {
        return name;
    }

    public OwnerDto getOwner() {
        return owner;
    }

    public boolean isFork() {
        return fork;
    }

    public String getBranches_url() {
        return branches_url;
    }
}
