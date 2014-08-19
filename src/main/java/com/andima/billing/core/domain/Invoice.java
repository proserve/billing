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
    private List<ProductInvoices> productsLines;
    private double tvaPercentage;
    public static int num = 1;
    public Invoice(List<ProductInvoices> productsLines, double tvaPercentage) {
        this.productsLines = productsLines;
        this.tvaPercentage = tvaPercentage;
        this.date = new Date();
        this.number = num++;
    }

    public Invoice(ArrayList<ProductInvoices> productInvoices) {
        this.productsLines = productInvoices;
        this.tvaPercentage = 17;
        this.date = new Date();
        this.number = num++;
    }

    public double getHTAmountSum() {
        double sum = 0;
        for (ProductInvoices productsLine : productsLines) {
            sum += productsLine.getHTAmount();
        }
        return sum;
    }

    public double getTvaAmountSum() {

        double sum = 0;
        for (ProductInvoices productsLine : productsLines) {
            sum += productsLine.getTvaAmount(tvaPercentage);
        }
        return sum;

    }


    public double getAmountTTCSum() {
        return getTvaAmountSum()+getHTAmountSum();
    }
}
