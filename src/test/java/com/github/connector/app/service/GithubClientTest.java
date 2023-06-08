package com.github.connector.app.service;

import com.github.connector.app.wrappers.RepositoryWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GithubClientTest {

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    WebClient webClient;

    @InjectMocks
    GithubClient githubClient;

    @Test
    public void basicTest() {
        String uri = "";
        ClientResponse clientResponse = ClientResponse.create(HttpStatus.OK).
                header("", "").build();
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.exchangeToFlux(any())).thenReturn(Flux.just(clientResponse));

        // When
        Flux<String> resultFlux = githubClient.fetchAll(uri, String.class, Optional.empty());

        // Then
        StepVerifier.create(resultFlux)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }
}
