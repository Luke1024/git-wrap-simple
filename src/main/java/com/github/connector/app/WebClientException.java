package com.github.connector.app;

public class WebClientException extends Exception {

    private final int statusCode;

    public WebClientException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
