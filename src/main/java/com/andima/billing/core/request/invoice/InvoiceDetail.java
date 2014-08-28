package com.andima.billing.core.request.invoice;

import com.andima.billing.core.domain.ProductInvoiceCore;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 23/08/2014.
 */
public class InvoiceDetail {
    private int number;
    private LocalDate date;
    private List<ProductInvoiceCore> productsLines;
    private double tvaPercentage;
    private String fullName;
    private String address;
    private String NCF;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNCF() {
        return NCF;
    }

    public void setNCF(String NCF) {
        this.NCF = NCF;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ProductInvoiceCore> getProductsLines() {
        return productsLines;
    }

    public void setProductsLines(List<ProductInvoiceCore> productsLines) {
        this.productsLines = productsLines;
    }

    public double getTvaPercentage() {
        return tvaPercentage;
    }

    public void setTvaPercentage(double tvaPercentage) {
        this.tvaPercentage = tvaPercentage;
    }
}
