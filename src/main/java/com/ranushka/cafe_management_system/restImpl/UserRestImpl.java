package com.ranushka.cafe_management_system.restImpl;

import com.ranushka.cafe_management_system.constents.CafeConstant;
import com.ranushka.cafe_management_system.rest.UserRest;
import com.ranushka.cafe_management_system.service.UserService;
import com.ranushka.cafe_management_system.util.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

 @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

     try{
            return userService.signUp(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
     
     return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
