package com.andima.billing.client.domain;

import com.andima.billing.core.request.product.ProductDetail;
import org.springframework.beans.BeanUtils;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public class Product {
    private int number;
    private String name;
    private String UM;
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
