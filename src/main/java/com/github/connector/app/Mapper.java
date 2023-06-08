package com.github.connector.app;

import com.github.connector.app.dto.BranchDto;
import com.github.connector.app.dto.RepositoryDto;
import com.github.connector.app.model.Branch;
import com.github.connector.app.model.Repository;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public RepositoryDto toDto(Repository repository){
        return new RepositoryDto(
                repository.getRepoName(),
                repository.getOwnerLogin(),
                repository.getBranches().stream().map(this::mapToBranchDto).toList());
    }

    public BranchDto mapToBranchDto(Branch branch){
        return new BranchDto(
                branch.getName(),
                branch.getLastCommitSHA()
        );
    }
}
