package com.github.connector.app.model;

public class ErrorResponse extends Response {
    private int status;
    private String Message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.Message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return Message;
    }
}
