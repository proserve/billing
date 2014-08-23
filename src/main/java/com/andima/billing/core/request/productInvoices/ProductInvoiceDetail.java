package com.andima.billing.core.request.productInvoices;

import com.andima.billing.core.request.invoice.InvoiceDetail;

/**
 * Created by GHIBOUB Khalid  on 20/08/2014.
 */
public class ProductInvoiceDetail {
    private int number;
    private String designation;
    private int quantity;
    private String UM;
    private double unitPrice;
    private boolean withTVA;
    private InvoiceDetail invoiceDetail;

    public InvoiceDetail getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public boolean isWithTVA() {
        return withTVA;
    }

    public void setWithTVA(boolean withTVA) {
        this.withTVA = withTVA;
    }
}
