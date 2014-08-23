package com.andima.billing.client.domain;

import com.andima.billing.core.request.invoice.InvoiceDetail;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public class Invoice {
    private int number;
    private Date date;
    private List<ProductInvoice> productsLines = new ArrayList<ProductInvoice>();
    private double tvaPercentage;
    private String fullName;
    private String address;
    private String NCF;
    private double ttc;

    public double getTtc() {
        return ttc;
    }

    public void setTtc(double ttc) {
        this.ttc = ttc;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ProductInvoice> getProductsLines() {
        return productsLines;
    }

    public void setProductsLines(List<ProductInvoice> productsLines) {
        this.productsLines = productsLines;
    }

    public double getTvaPercentage() {
        return tvaPercentage;
    }

    public void setTvaPercentage(double tvaPercentage) {
        this.tvaPercentage = tvaPercentage;
    }
    public InvoiceDetail toInvoiceDetail(){
        InvoiceDetail invoicesDetails = new InvoiceDetail();
        BeanUtils.copyProperties(this, invoicesDetails);
        return invoicesDetails;
    }

    public static Invoice fromInvoiceDetail(InvoiceDetail details){
        Invoice productInvoice = new Invoice();
        BeanUtils.copyProperties(details, productInvoice);
        return productInvoice;
    }
}
