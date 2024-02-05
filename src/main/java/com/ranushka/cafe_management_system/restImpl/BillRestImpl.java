package com.ranushka.cafe_management_system.restImpl;

import com.ranushka.cafe_management_system.constents.CafeConstant;
import com.ranushka.cafe_management_system.rest.BillRest;
import com.ranushka.cafe_management_system.service.BillService;
import com.ranushka.cafe_management_system.util.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BillRestImpl implements BillRest {

    @Autowired
    BillService billService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
            return billService.generateReport(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
