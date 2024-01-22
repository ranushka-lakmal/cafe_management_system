package com.ranushka.cafe_management_system.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name = "Product.getAllProduct", query = "select new com.ranushka.cafe_management_system.wrapper.ProductWrapper(p.id, p.name, p.desciption, p.price, p.status, p.category.id, p.category.name) from Product p")
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
public class Product implements Serializable {

    public static final long serialVersionUid = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @Column(name = "description")
    private String desciption;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private String status;
}
