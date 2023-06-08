package com.github.connector.app;

import com.github.connector.app.dto.ErrorDto;
import com.github.connector.app.dto.RepositoryDto;
import com.github.connector.app.service.GithubService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/user")
public class GithubController {

    private final GithubService githubService;
    private final Mapper mapper;

    public GithubController(GithubService githubService, Mapper mapper){
        this.githubService = githubService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/repos/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<RepositoryDto>  getRepos(@PathVariable String username, ServerWebExchange serverWebExchange) {
        return githubService.getRepos(username, serverWebExchange)
                .map(mapper::toDto)
                .onErrorResume(throwable -> {
                    try {
                        throw throwable;
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @ExceptionHandler(WebClientException.class)
    public ResponseEntity<ErrorDto> handleException(WebClientException e) {
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorDto(e.getStatusCode(), e.getMessage()));
    }
}
