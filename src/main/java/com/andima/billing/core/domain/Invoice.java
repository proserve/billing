package com.andima.billing.core.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public class Invoice {
    private int number;
    private Date date;
    private List<ProductInvoiceCore> productsLines;
    private double tvaPercentage;
    private String fullName;
    private String address;
    private String NCF;

    public Invoice(double tvaPercentage) {
        this.tvaPercentage = tvaPercentage;
    }

    public Invoice(List<ProductInvoiceCore> productsLines, double tvaPercentage) {
        this.productsLines = productsLines;
        this.tvaPercentage = tvaPercentage;
        this.date = new Date();
    }

    public Invoice(ArrayList<ProductInvoiceCore> productInvoiceCores) {
        this.productsLines = productInvoiceCores;
        this.tvaPercentage = 17;
        this.date = new Date();
    }

    public double getHTAmountSum() {
        double sum = 0;
        for (ProductInvoiceCore productsLine : productsLines) {
            sum += productsLine.getHTAmount();
        }
        return sum;
    }

    public double getTvaAmountSum() {

        double sum = 0;
        for (ProductInvoiceCore productsLine : productsLines) {
            sum += productsLine.getTvaAmount(tvaPercentage);
        }
        return sum;

    }


    public double getAmountTTCSum() {
        return getTvaAmountSum()+getHTAmountSum();
    }
}
