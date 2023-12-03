package com.ranushka.cafe_management_system.POJO;

import jdk.jfr.Name;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@NamedQuery(name="User.findByEmailId", query = "select u from User u where u.email=:email")

@NamedQuery(name="User.getAllUser", query = "select new com.ranushka.cafe_management_system.wrapper.UserWrapper(u.id, u.name,u.email,u.contractNumber,u.status) from User u where u.role = 'user'")

@NamedQuery(name = "User.updateStatus", query = "UPDATE User u set u.status=:status where u.id=:id")

@NamedQuery(name="User.getAllAdmin", query = "select u.email from User u where u.role = 'admin'")


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contractNumber")
    private String contractNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
}
