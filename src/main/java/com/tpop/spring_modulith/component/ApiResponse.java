package com.tpop.spring_modulith.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonInclude
public class ApiResponse<T> implements Serializable {
    private Integer status;
    private String message;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(Integer status, String message , T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public ApiResponse<T> setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ApiResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse<T> setData(T data) {
        this.data = data;
        return this;
    }
}
