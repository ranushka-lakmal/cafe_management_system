package com.ranushka.cafe_management_system.serviceImpl;

import com.ranushka.cafe_management_system.JWT.JwtFilter;
import com.ranushka.cafe_management_system.POJO.Category;
import com.ranushka.cafe_management_system.POJO.Product;
import com.ranushka.cafe_management_system.constents.CafeConstant;
import com.ranushka.cafe_management_system.dao.ProductDao;
import com.ranushka.cafe_management_system.service.ProductService;
import com.ranushka.cafe_management_system.util.CafeUtils;
import com.ranushka.cafe_management_system.wrapper.ProductWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {

        try {
            if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap, false)){
                    productDao.save(getProductFromMap(requestMap, false));
                    return CafeUtils.getResponseEntity("Product Added Successfully !!", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity(CafeConstant.INVALIDATE_DATA, HttpStatus.BAD_REQUEST);

            }else {
                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {

        log.info("inside validateProductMap");
        if(requestMap.containsKey("name")){
            log.info("inside containsKey--> name");
            if(requestMap.containsKey("id") && validateId){
                log.info("inside containsKey--> id");
                return true;
            } else if (!validateId) {
                log.info("inside !validateId");
                return true;
            }
        }
        log.info("validateProductMap false");
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {

        log.info("inside getProductFromMap");
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();

        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            product.setStatus("true");
        }

        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDesciption(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));

        return product;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {

        log.info("inside getAllProduct");
        try{

            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {

        log.info("inside updateProduct");
        try {

            if (jwtFilter.isAdmin()) {
                log.info("update product check isAdmin-------------");
                if (validateProductMap(requestMap, true)) {
                    log.info("update product check validateProductMap-------------");
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));

                    if (!optional.isEmpty()) {

                        Product existingProduct = optional.get();
                        Product updatedProduct = getProductFromMap(requestMap, true);

                        // Set only the fields that can be updated
                        existingProduct.setName(updatedProduct.getName());
                        existingProduct.setDesciption(updatedProduct.getDesciption());
                        existingProduct.setPrice(updatedProduct.getPrice());

                        log.info("before update product-------------");
                        productDao.save(existingProduct);

                        return CafeUtils.getResponseEntity("Product Updated Successfully!!", HttpStatus.OK);
                    } else {
                        return CafeUtils.getResponseEntity("Product Id does not exist", HttpStatus.OK);
                    }
                } else {
                    return CafeUtils.getResponseEntity(CafeConstant.INVALIDATE_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{

            if(jwtFilter.isAdmin()){
                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()){
                    productDao.deleteById(id);
                    return CafeUtils.getResponseEntity("Product deleted successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("product id does not exist", HttpStatus.OK);
            }else{
                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                Optional optional = productDao.findById(Integer.parseInt(requestMap.get("id")));

                if(!optional.isEmpty()){
                    productDao.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return CafeUtils.getResponseEntity("Product status updated successfully !!", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Product is does not exist.", HttpStatus.OK);

            }else {
                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {

        try {

            return new ResponseEntity<>(productDao.getProductByCategory(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {

        try{
            return new ResponseEntity<>(productDao.getProductById(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
