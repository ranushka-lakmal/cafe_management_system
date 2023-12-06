package com.ranushka.cafe_management_system.serviceImpl;

import antlr.StringUtils;
import com.google.common.base.Strings;
import com.ranushka.cafe_management_system.JWT.JwtFilter;
import com.ranushka.cafe_management_system.POJO.Category;
import com.ranushka.cafe_management_system.constents.CafeConstant;
import com.ranushka.cafe_management_system.dao.CategoryDao;
import com.ranushka.cafe_management_system.service.CategoryService;
import com.ranushka.cafe_management_system.util.CafeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.base.Strings.*;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{

            if(jwtFilter.isAdmin()){
                log.info("inside addNewCategory::: isAdmin()");

                if(validateCategoryMap(requestMap, false)){

                    categoryDao.save(getCategoryFromMap(requestMap,false));
                    return CafeUtils.getResponseEntity("Category added Successfully", HttpStatus.OK);
                }
            }else{
                // current user is not Admin
                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {

        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd){

        Category category = new Category();

        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }

        category.setName(requestMap.get("name"));
        return category;
    }


    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {

        try {

            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){


                log.info("inside if :::: getAllCategory");
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
            }

            return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){

                if(validateCategoryMap(requestMap, true)){

                   Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));

                   if(!optional.isEmpty()){
                       categoryDao.save(getCategoryFromMap(requestMap, true));
                       return CafeUtils.getResponseEntity("Category Updated Successfully", HttpStatus.OK);
                   }else {
                       return CafeUtils.getResponseEntity("Category ID does not exist", HttpStatus.OK);
                   }
                }
                return CafeUtils.getResponseEntity(CafeConstant.INVALIDATE_DATA, HttpStatus.BAD_REQUEST);
            }else {

                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
