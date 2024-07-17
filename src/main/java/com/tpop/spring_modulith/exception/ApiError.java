package com.tpop.spring_modulith.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ApiError {
    private String errorCode;
    private String message;
    private List<APIErrorDetail> errorDetails;

    public ApiError() {
    }

    public ApiError(String errorCode, String message, List<APIErrorDetail> errorDetails) {
        this.errorCode = errorCode;
        this.message = message;
        this.errorDetails = errorDetails;
    }

    public ApiError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
