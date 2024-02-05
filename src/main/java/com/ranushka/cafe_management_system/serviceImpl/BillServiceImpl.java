package com.ranushka.cafe_management_system.serviceImpl;

import com.ranushka.cafe_management_system.constents.CafeConstant;
import com.ranushka.cafe_management_system.service.BillService;
import com.ranushka.cafe_management_system.util.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class BillServiceImpl implements BillService {
    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("inside generateReport--->");
        try{
            String fileName;

            if(validateRequestMap(requestMap)){

            }
            return CafeUtils.getResponseEntity("Required data not found !!", HttpStatus.BAD_REQUEST);

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {

        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("productDetails") &&
                requestMap.containsKey("totalAmount");
    }
}
