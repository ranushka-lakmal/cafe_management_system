package com.ranushka.cafe_management_system.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtils {

    //private constructor cannot create a new object
    private CafeUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>( "{\"message\":\""+responseMessage+"\"}", httpStatus);
    }
}
