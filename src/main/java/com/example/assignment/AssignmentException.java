package com.example.assignment;


import org.springframework.http.HttpStatus;

public class AssignmentException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;


    public AssignmentException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }
    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }






}
