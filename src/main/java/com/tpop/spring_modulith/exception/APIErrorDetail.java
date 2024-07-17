package com.tpop.spring_modulith.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIErrorDetail {
    private Long index ;
    private String field;
    private String errorCode;
    private String message;
}
