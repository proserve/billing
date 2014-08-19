package com.andima.billing.persistence.domain;

import com.andima.billing.core.request.product.ProductDetail;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int number;
    @Column(unique = true)
    @NotBlank(message = "Le nom de produit  ne peut pas être vide")
    private String name;

    @Column(name = "metric_unit")
    @NotBlank(message = "L'unité métrique ne peut pas être vide")
    private String UM;
    @Column(name = "unite_price")
    private double unitPrice;

    public Product(String name, String UM, double unitPrice) {
        this.name = name;
        this.UM = UM;
        this.unitPrice = unitPrice;
    }

    public Product() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUM() {
        return UM;
    }

    public void setUM(String UM) {
        this.UM = UM;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ProductDetail toProductDetail(){
        ProductDetail productDetail = new ProductDetail();
        BeanUtils.copyProperties(this, productDetail);
        return productDetail;
    }

    public static Product fromProductDetail(ProductDetail productDetail){
        Product product = new Product();
        BeanUtils.copyProperties(productDetail, product);
        return product;
    }
}
