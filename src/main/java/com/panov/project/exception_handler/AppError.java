package com.panov.project.exception_handler;

import lombok.Data;

@Data
public class AppError {
    private String message;
    private int status;

    public AppError(String messages, int status) {
        this.message = messages;
        this.status = status;
    }
}
