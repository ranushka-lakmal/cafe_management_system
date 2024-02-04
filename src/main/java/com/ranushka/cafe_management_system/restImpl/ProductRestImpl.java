    package com.ranushka.cafe_management_system.restImpl;

    import com.ranushka.cafe_management_system.JWT.JwtFilter;
    import com.ranushka.cafe_management_system.constents.CafeConstant;
    import com.ranushka.cafe_management_system.rest.ProductRest;
    import com.ranushka.cafe_management_system.service.ProductService;
    import com.ranushka.cafe_management_system.util.CafeUtils;
    import com.ranushka.cafe_management_system.wrapper.ProductWrapper;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;

    @RestController
    public class ProductRestImpl implements ProductRest {

        @Autowired
        ProductService productService;

        @Autowired
        JwtFilter jwtFilter;

        @Override
        public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {

            try{
                return productService.addNewProduct(requestMap);

            }catch (Exception e){
                e.printStackTrace();
            }
            return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @Override
        public ResponseEntity<List<ProductWrapper>> getAllProduct() {
            try{

                return productService.getAllProduct();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @Override
        public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {

            try{
                return productService.updateProduct(requestMap);
            }catch (Exception e){
                e.printStackTrace();
            }

            return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @Override
        public ResponseEntity<String> deleteProduct(Integer id) {

            try {
                return productService.deleteProduct(id);
            }catch (Exception e){
                e.printStackTrace( );
            }

            return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        @Override
        public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {

            try{
                return productService.updateStatus(requestMap);
            }catch (Exception e){
                e.printStackTrace();
            }
            return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @Override
        public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {

            try{
                return productService.getByCategory(id);
            }catch (Exception e){
                e.printStackTrace();
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
