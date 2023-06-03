package com.github.connector.app;

import com.github.connector.app.dto.BranchDto;
import com.github.connector.app.dto.RepoDto;
import com.github.connector.app.model.Branch;
import com.github.connector.app.model.Repo;
import com.github.connector.app.model.RepoResponse;
import com.github.connector.app.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GithubService {
    private final WebClient webClient;
    private static final String ROOT_URL = "https://api.github.com";
    private static final String USER_REPOS_URL_TEMPLATE = "/users/%s/repos";

    @Autowired
    public GithubService(WebClient webClient) {
        this.webClient = webClient;
    }
    public Mono<Response> getRepos(String userName){
        return collectRepos(userName)
                .filter(repoDtos -> ! repoDtos.isFork())
                .flatMap(repoDto ->
                        collectBranches(repoDto.getBranches_url())
                                .map(branches -> new Repo(repoDto.getName(),
                                        repoDto.getOwner().getLogin(),
                                        branches)))
                .collectList()
                .map(RepoResponse::new);
    }

    private Flux<RepoDto> collectRepos(String userName){
        String uri = ROOT_URL + String.format(USER_REPOS_URL_TEMPLATE, userName);
        return executeGetRequest(uri, RepoDto.class);
    }

    private Mono<List<Branch>> collectBranches(String branchesUrl){
        String uri = removeCurlyBrace(branchesUrl);
        return executeGetRequest(uri, BranchDto.class)
                .map(branchDto -> new Branch(branchDto.getName(), branchDto.getCommit().getSha()))
                .collectList();
    }

    private <T> Flux<T> executeGetRequest(String uri, Class<T> responseClass) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        Mono.error(new WebClientException(clientResponse.statusCode().value(),
                                clientResponse.statusCode().toString())))
                .bodyToFlux(responseClass);
    }

    private String removeCurlyBrace(String input){
        return input.replaceAll("\\{.*?\\}", "");
    }
}
