package com.ranushka.cafe_management_system.dao;

import com.ranushka.cafe_management_system.POJO.User;
import com.ranushka.cafe_management_system.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,Integer > {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();

    @Transactional
    @Modifying
    Integer  updateStatus(@Param("status") String status, @Param("id") Integer id );
}

