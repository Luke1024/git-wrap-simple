package com.github.connector.app.wrappers;

public class RepositoryWrapper {
    private String name;
    private OwnerWrapper owner;
    private boolean fork;
    private String branches_url;

    public RepositoryWrapper() {
    }

    public RepositoryWrapper(String name, OwnerWrapper owner, boolean fork, String branches_url) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
        this.branches_url = branches_url;
    }

    public String getName() {
        return name;
    }

    public OwnerWrapper getOwner() {
        return owner;
    }

    public boolean isFork() {
        return fork;
    }

    public String getBranches_url() {
        return branches_url;
    }

    @Override
    public String toString() {
        return "RepositoryWrapper{" +
                "name='" + name + '\'' +
                ", owner=" + owner +
                ", fork=" + fork +
                ", branches_url='" + branches_url + '\'' +
                '}';
    }
}
