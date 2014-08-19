package com.andima.billing.core.request.product;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public class ProductDetail {
    private int number;
    private String name;
    private String UM;
    private double unitPrice;

    public ProductDetail(String name, String UM, double unitPrice) {
        this.name = name;
        this.UM = UM;
        this.unitPrice = unitPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ProductDetail() {
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
}
