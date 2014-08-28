package com.andima.billing.persistence.domain;

import com.andima.billing.core.request.invoice.InvoiceDetail;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int number;
    @Temporal(value = TemporalType.DATE)
    private Date date;
    @OneToMany(mappedBy = "invoice")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "invoice_id")
    private List<ProductInvoice> productsLines = new ArrayList<ProductInvoice>();
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
        invoicesDetails.setDate(LocalDate.of(date.getYear(), date.getMonth(), date.getDay()));
        return invoicesDetails;
    }

    public static Invoice fromInvoiceDetail(InvoiceDetail details){
        Invoice productInvoice = new Invoice();
        BeanUtils.copyProperties(details, productInvoice);
        LocalDate detailsDate = details.getDate();
        productInvoice.setDate(new Date(detailsDate.getYear(), detailsDate.getMonth().getValue(),
                detailsDate.getDayOfMonth()));
        return productInvoice;
    }
}
