package com.andima.billing.core.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public class InvoiceTest {

    private Invoice invoice;

    @Before
    public void setUp() throws Exception {
        ArrayList<ProductInvoiceCore> productInvoiceCores = new ArrayList<ProductInvoiceCore>();
        productInvoiceCores.add(new ProductInvoiceCore(4, 240.00).createWithoutTva());
        productInvoiceCores.add(new ProductInvoiceCore(5, 7500.00));
        invoice = new Invoice(productInvoiceCores);
    }

    @Test
    public void getHTAmountTest() throws Exception{
        double amount = invoice.getHTAmountSum();
        assertEquals(38460.00, amount);
    }
    @Test
    public void getAmountWithTvaTest() throws Exception{
        double amount = invoice.getTvaAmountSum();
        assertEquals(6375.00, amount);
    }
    
    @Test
    public void getAmountTTCTest() throws Exception{
        double amount = invoice.getAmountTTCSum();
        assertEquals(44835.00, amount);
    }
}
