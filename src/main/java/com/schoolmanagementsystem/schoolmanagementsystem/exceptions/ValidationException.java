package com.schoolmanagementsystem.schoolmanagementsystem.exceptions;

public class ValidationException extends Exception{
    private String exceptionMessage;

    public ValidationException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
