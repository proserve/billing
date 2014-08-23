package com.andima.billing.persistence.domain;

import com.andima.billing.core.request.productInvoices.ProductInvoiceDetail;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
@Entity
public class ProductInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int number;
    private String designation;
    private int quantity;
    private String UM;
    private double unitPrice;
    private boolean withTVA;
    @ManyToOne
    private Invoice invoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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
    public ProductInvoiceDetail toProductInvoiceDetail(){
        ProductInvoiceDetail invoicesDetails = new ProductInvoiceDetail();
        BeanUtils.copyProperties(this, invoicesDetails);
        if(invoice != null)
            invoicesDetails.setInvoiceDetail(invoice.toInvoiceDetail());
        return invoicesDetails;
    }

    public static ProductInvoice fromProductInvoiceDetail(ProductInvoiceDetail details){
        ProductInvoice productInvoice = new ProductInvoice();
        BeanUtils.copyProperties(details, productInvoice);
        if(details.getInvoiceDetail() != null)
            productInvoice.setInvoice(Invoice.fromInvoiceDetail(details.getInvoiceDetail()));
        return productInvoice;
    }
}
