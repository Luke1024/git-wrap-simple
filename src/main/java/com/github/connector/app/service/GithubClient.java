package com.github.connector.app.service;

import com.github.connector.app.WebClientException;
import com.github.connector.app.wrappers.GitErrorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Service
public class GithubClient {
    private final WebClient webClient;
    private final LinkExtractor linkExtractor;
    private final Logger log;

    public GithubClient(WebClient webClient,
                        LinkExtractor linkExtractor) {
        this.webClient = webClient;
        this.linkExtractor = linkExtractor;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public <T> Flux<T> fetchAll(String uri, Class<T> responseClass, Optional<String> tokenOptional) {
        WebClient.RequestHeadersSpec<?> spec = webClient.get().uri(uri);
        tokenOptional.ifPresent(s -> spec.header("Authorization", "Bearer " + s));
        return spec.exchangeToFlux(
                clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        Flux<T> dataFlux = clientResponse.bodyToFlux(responseClass);
                        Optional<String> link = linkExtractor.extractLinks(clientResponse.headers().asHttpHeaders());
                        if (link.isPresent()) {
                            log.info("Used pagination. " + link.get());
                            return dataFlux.concatWith(fetchAll(link.get(), responseClass, tokenOptional));
                        } else {
                            return dataFlux;
                        }
                    } else if(clientResponse.statusCode().is4xxClientError()) {
                        return clientResponse.bodyToFlux(GitErrorWrapper.class)
                                .flatMap(errorWrapper -> Flux.error(new WebClientException(clientResponse.statusCode().value(),
                                        errorWrapper.getMessage())));
                    } else if(clientResponse.statusCode().is5xxServerError()) {
                        return clientResponse.bodyToFlux(GitErrorWrapper.class)
                                .flatMap(errorWrapper -> Flux.error(new WebClientException(clientResponse.statusCode().value(),
                                        errorWrapper.getMessage())));
                    } else {
                        int errorCode = clientResponse.statusCode().value();
                        return Flux.error(new WebClientException(errorCode, "Something went wrong"));
                    }
                }
        );
    }
}
