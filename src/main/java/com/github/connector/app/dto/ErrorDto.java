package com.github.connector.app.dto;

public class ErrorDto {
    private int status;
    private String message;

    public ErrorDto() {
    }

    public ErrorDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
