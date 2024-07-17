package com.tpop.spring_modulith.component;

import com.tpop.spring_modulith.constant.ResponseStatusConst;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleResponseSuccessData {
    public  static  <T> ResponseEntity<T> handleResponseData(String message , T  data , HttpStatus httpStatus) {
        return ResponseDataConfiguration.success(ResponseStatusConst.SUCCESS,message , data, httpStatus );
    }
}
