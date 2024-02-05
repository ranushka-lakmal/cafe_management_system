package com.ranushka.cafe_management_system.dao;

import com.ranushka.cafe_management_system.POJO.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDao extends JpaRepository<Bill, Integer> {
}
