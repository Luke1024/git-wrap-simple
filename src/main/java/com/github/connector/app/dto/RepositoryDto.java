package com.github.connector.app.dto;

import java.util.List;

public class RepositoryDto {
    private String repoName;
    private String ownerLogin;
    private List<BranchDto> branchDtos;

    public RepositoryDto(String repoName, String ownerLogin, List<BranchDto> branchDtos) {
        this.repoName = repoName;
        this.ownerLogin = ownerLogin;
        this.branchDtos = branchDtos;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<BranchDto> getBranchDtos() {
        return branchDtos;
    }
}
