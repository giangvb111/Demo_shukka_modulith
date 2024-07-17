package com.tpop.spring_modulith.component;

import com.tpop.spring_modulith.exception.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public class ResponseDataConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    public static <T> ResponseEntity<T> error(Integer status, ApiError error, HttpStatus httpStatus) {
        ResponseData<T> responseData = new ResponseData<>(status, error);
        return new ResponseEntity(responseData, httpStatus);
    }

    public static <T> ResponseEntity<T> success(Integer status,String message ,T data , HttpStatus httpStatus) {
        ApiResponse<T> responseData = new ApiResponse<>(status ,message ,data);
        return new ResponseEntity(responseData, httpStatus);
    }
}
