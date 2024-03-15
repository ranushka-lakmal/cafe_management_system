package com.ranushka.cafe_management_system.serviceImpl;

import com.ranushka.cafe_management_system.dao.BillDao;
import com.ranushka.cafe_management_system.dao.CategoryDao;
import com.ranushka.cafe_management_system.dao.ProductDao;
import com.ranushka.cafe_management_system.dao.UserDao;
import com.ranushka.cafe_management_system.service.CategoryService;
import com.ranushka.cafe_management_system.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    BillDao billDao;

    @Autowired
    UserDao userDao;


    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("category", categoryDao.count());
        map.put("product", productDao.count());
        map.put(("bill"), billDao.count());
        /*map.put("users", userDao.count());*/

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
