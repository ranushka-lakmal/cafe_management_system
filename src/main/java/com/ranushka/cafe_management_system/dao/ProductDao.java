package com.ranushka.cafe_management_system.dao;

import com.ranushka.cafe_management_system.POJO.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}
