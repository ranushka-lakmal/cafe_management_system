package com.ranushka.cafe_management_system.dao;

import com.ranushka.cafe_management_system.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {

    List<Category> getAllCategory();


}
