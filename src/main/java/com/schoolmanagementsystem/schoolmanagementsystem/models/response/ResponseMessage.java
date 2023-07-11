package com.schoolmanagementsystem.schoolmanagementsystem.models.response;

public class ResponseMessage {

    private int statusCode;

    private ResponseStatus responseStatus;

    private String description;

    public ResponseMessage(int statusCode, ResponseStatus responseStatus, String description) {
        this.statusCode = statusCode;
        this.responseStatus = responseStatus;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseStatus getStatus() {
        return responseStatus;
    }

    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

