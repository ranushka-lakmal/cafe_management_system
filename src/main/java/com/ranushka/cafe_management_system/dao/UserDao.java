package com.ranushka.cafe_management_system.dao;

import com.ranushka.cafe_management_system.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer > {

    User findByEmailId(@Param("email") String email);
}
