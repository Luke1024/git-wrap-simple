package com.github.connector.app.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LinkExtractor {

    public Optional<String> extractLinks(HttpHeaders headers) {
        if (!headers.containsKey("Link")) {
            return Optional.empty();
        }

        var linksArray = new String[]{};

        var linkHeader = headers.getFirst("Link");
        if(linkHeader != null) {
            linksArray = linkHeader.split(", ");
        }

        var linkMap = Arrays.stream(linksArray)
                .map(link -> link.split("; "))
                .collect(Collectors.toMap(
                        link -> link[1].substring(5, link[1].length() - 1),
                        link -> link[0].substring(1, link[0].length() - 1),
                        (a, b) -> b
                ));

        return Optional.ofNullable(linkMap.get("next"));
    }
}
