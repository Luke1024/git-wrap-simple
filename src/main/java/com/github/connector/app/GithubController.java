package com.github.connector.app;

import com.github.connector.app.model.ErrorResponse;
import com.github.connector.app.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class GithubController {
    private static final String correctHeader = "application/json";

    @Autowired
    private GithubService githubService;

    @GetMapping(value = "/repos/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> getRepos(@PathVariable String username, @RequestHeader("Accept") String acceptHeader) {
        if (isHeaderAcceptable(acceptHeader)) {
            return githubService.getRepos(username)
                    .map(repoList -> ResponseEntity.ok().body(repoList))
                    .onErrorResume(throwable -> Mono.just(webErrorResponse(throwable)));
        } else {
            return Mono.just(ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .header("Message",
                            "Invalid Accept Header, only application/json is accepted")
                    .build());
        }
    }

    private ResponseEntity<Response> webErrorResponse(Throwable throwable) {
        int statusCode = ((WebClientException) throwable).getStatusCode();
        String message = "User not found";
        if(statusCode != 404){
            message = throwable.getMessage();
        }
        return ResponseEntity.status(statusCode).body(
                new ErrorResponse(statusCode, message));
    }

    private boolean isHeaderAcceptable(String acceptHeader) {
        return correctHeader.equals(acceptHeader);
    }
}
