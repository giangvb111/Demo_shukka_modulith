package com.tpop.spring_modulith.exception;

import com.tpop.spring_modulith.component.ResponseDataConfiguration;
import com.tpop.spring_modulith.constant.ResponseStatusConst;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ApiError> handleAllException(CommonException ex, WebRequest request) {
        ApiError error = new ApiError(ex.getErrorCode(), ex.getMessage(), ex.getErrorDetails());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, ex.getStatusCode());
    }
}
