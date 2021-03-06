package com.andima.billing.core.domain;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public class ProductInvoiceCore {
    private int number;
    private String designation;
    private int quantity;
    private String UM;
    private double unitPrice;
    private boolean withTVA;
    private Invoice invoice;


    public ProductInvoiceCore(int quantity, double unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.withTVA = true;
    }

    public ProductInvoiceCore createWithoutTva(){
        this.withTVA = false;
        return this;
    }

    public double getHTAmount() {
        return quantity*unitPrice;
    }

    public double getTvaAmount(double Tva) {

        return withTVA ? getHTAmount()*Tva/100: 0;
    }

    public double getAmountTTC(double TVA) {
        return  withTVA ?getHTAmount()+ getTvaAmount(TVA): getHTAmount();
    }
}
