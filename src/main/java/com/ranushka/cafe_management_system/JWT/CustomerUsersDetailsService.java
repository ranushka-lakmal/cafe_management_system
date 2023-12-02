package com.ranushka.cafe_management_system.JWT;

import com.ranushka.cafe_management_system.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {

/*    @Autowired*/
     private final UserDao userDao;
    @Autowired
    public CustomerUsersDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }
     private com.ranushka.cafe_management_system.POJO.User userDetails; //com.ranushka.cafe_management_system.POJO.User return like this, because this User class also available in Spring secure.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside loadUserByUsername {}", username);
        userDetails = userDao.findByEmailId(username);

        if(!Objects.isNull(userDetails))
            return new User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>());
        else
            throw new UsernameNotFoundException("user not found");

    }

    public com.ranushka.cafe_management_system.POJO.User getUserDetails(){

        return userDetails;
    }

    
}
