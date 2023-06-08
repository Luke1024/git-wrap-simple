package com.github.connector.app.service;

import com.github.connector.app.wrappers.BranchWrapper;
import com.github.connector.app.wrappers.RepositoryWrapper;
import com.github.connector.app.model.Branch;
import com.github.connector.app.model.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;


@Service
public class GithubService {
    private final GithubClient githubClient;
    private static final String ROOT_URL = "https://api.github.com";
    private static final String USER_REPOS_URL_TEMPLATE = "/users/%s/repos";
    private static final String PER_PAGE_MAX = "?per_page=100";
    private static final String GIT_TOKEN_KEY = "Gittoken";

    private final Logger log;

    public GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public Flux<Repository> getRepos(String userName, ServerWebExchange serverWebExchange){
        var tokenOptional = extractGitToken(serverWebExchange);

        return collectRepos(userName, tokenOptional)
                .onErrorResume(Flux::error)
                .filter(repoDtos -> ! repoDtos.isFork())
                .flatMap(repositoryWrapper -> collectBranches(repositoryWrapper.getBranches_url(), tokenOptional).collectList()
                        .map(branches -> new Repository(repositoryWrapper.getName(),
                                repositoryWrapper.getOwner().getLogin(),
                                branches)));
    }

    private Flux<RepositoryWrapper> collectRepos(String userName, Optional<String> token){
        var uri = ROOT_URL + String.format(USER_REPOS_URL_TEMPLATE, userName) + PER_PAGE_MAX;
        return githubClient.fetchAll(uri, RepositoryWrapper.class, token).onErrorResume(Flux::error);
    }

    private Flux<Branch> collectBranches(String branchesUrl, Optional<String> tokenOptional){
        var uri = removeCurlyBrace(branchesUrl) + PER_PAGE_MAX;
        return githubClient.fetchAll(uri, BranchWrapper.class, tokenOptional)
                .map(branchWrapper -> new Branch(branchWrapper.getName(), branchWrapper.getCommit().getSha()));
    }

    private String removeCurlyBrace(String input){
        return input.replaceAll("\\{.*?\\}", "");
    }

    private Optional<String> extractGitToken(ServerWebExchange serverWebExchange){
        var headers = serverWebExchange.getRequest().getHeaders();
        var tokens = headers.get(GIT_TOKEN_KEY);

        if(tokens != null){
            if(!tokens.isEmpty()) {
                var token = tokens.get(0);
                log.info("Used github token: " + token);
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }
}
