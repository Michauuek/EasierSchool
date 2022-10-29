package com.example.EasierSchool.exception;


import lombok.Data;
import lombok.Getter;

@Getter
public class CustomServiceException extends RuntimeException{

    private String errorCode;
    public CustomServiceException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
