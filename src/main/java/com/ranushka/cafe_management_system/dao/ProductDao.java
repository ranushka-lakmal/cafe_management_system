package com.ranushka.cafe_management_system.dao;

import com.ranushka.cafe_management_system.POJO.Product;
import com.ranushka.cafe_management_system.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<ProductWrapper> getAllProduct();

}
