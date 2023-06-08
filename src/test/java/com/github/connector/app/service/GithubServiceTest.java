package com.github.connector.app.service;

import com.github.connector.app.model.Repository;
import com.github.connector.app.wrappers.BranchWrapper;
import com.github.connector.app.wrappers.CommitWrapper;
import com.github.connector.app.wrappers.OwnerWrapper;
import com.github.connector.app.wrappers.RepositoryWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class GithubServiceTest {

    private GithubService githubService;

    @Mock
    private GithubClient githubClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        githubService = new GithubService(githubClient);
    }

    @Test
    void testGetReposBasic() {
        HttpHeaders headers = new HttpHeaders();

        ServerWebExchange serverWebExchange = MockServerWebExchange.from(MockServerHttpRequest
                .get("")
                .headers(headers)
                .build());


        var userName = "testUser";
        var token = "token";

        RepositoryWrapper repoWrapper = new RepositoryWrapper(
                "Repo",
                new OwnerWrapper(),
                false,
                "");

        var branchWrapper = new BranchWrapper("Branch",new CommitWrapper());

        when(githubClient.fetchAll(anyString(), eq(RepositoryWrapper.class), any(Optional.class)))
                .thenReturn(Flux.just(repoWrapper));
        when(githubClient.fetchAll(anyString(), eq(BranchWrapper.class), any(Optional.class)))
                .thenReturn(Flux.just(branchWrapper,branchWrapper,branchWrapper));

        Flux<Repository> repositoryFlux = githubService.getRepos(userName, serverWebExchange);

        StepVerifier.create(repositoryFlux)
                .expectNextMatches(repository ->
                    repository.getRepoName().equals("Repo") &&
                        repository.getBranches().size()==3)
                .verifyComplete();
    }
}