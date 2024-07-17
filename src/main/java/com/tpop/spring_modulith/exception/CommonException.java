package com.tpop.spring_modulith.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class CommonException extends Exception{

    private static final long serialVersionUID = -3258098393078013278L;

    private String message;
    private String errorCode;
    private HttpStatus statusCode = HttpStatus.OK;
    private List<APIErrorDetail> errorDetails;

    public CommonException() {
        super();
    }
    public CommonException(String message) {
        super(message);
    }

    public CommonException(String errorCode, String message, HttpStatus statusCode) {
        super(message);
        this.setErrorCode(errorCode);
        this.setStatusCode(statusCode);
    }

    public CommonException(String errorCode, String message, HttpStatus statusCode, List<APIErrorDetail> errorDetails) {
        super(message);
        this.setErrorCode(errorCode);
        this.setStatusCode(statusCode);
        this.setErrorDetails(errorDetails);
    }

    public CommonException setMessage(String message) {
        this.message = message;
        return this;
    }

    public CommonException setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }


    public CommonException setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }


    public CommonException setErrorDetails(List<APIErrorDetail> errorDetails) {
        this.errorDetails = errorDetails;
        return this;
    }
}
