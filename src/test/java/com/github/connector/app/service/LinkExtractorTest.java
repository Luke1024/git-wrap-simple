package com.github.connector.app.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.*;

class LinkExtractorTest {

    LinkExtractor linkExtractor = new LinkExtractor();

    @Test
    void testLinkExtraction(){

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("random","");
        httpHeaders.add("link", "<https://api.github.com/user/9892522/repos?per_page=100&page=2>; rel=\"next\", <https://api.github.com/user/9892522/repos?per_page=100&page=3>; rel=\"last\"");
        httpHeaders.add("random","");

        String requiredLink = "https://api.github.com/user/9892522/repos?per_page=100&page=2";

        assertEquals(requiredLink, linkExtractor.extractLinks(httpHeaders).get());
    }
}