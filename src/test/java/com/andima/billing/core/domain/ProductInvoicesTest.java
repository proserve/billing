package com.andima.billing.core.domain;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public class ProductInvoicesTest {


    private ProductInvoices productInvoicesNoTva;
    private ProductInvoices productInvoicesWithTva;

    @Before
    public void setUp() throws Exception {
        productInvoicesWithTva = new ProductInvoices(4, 240.00);
        productInvoicesNoTva = new ProductInvoices(4, 240.00).createWithoutTva();
    }

    @Test
    public void getHTAmountTest() throws Exception{
        double amount = productInvoicesWithTva.getHTAmount();
        assertEquals(960.00, amount);
    }
    @Test
    public void getTvaAmountTest() throws Exception{
        double amount = productInvoicesWithTva.getTvaAmount(17);
        assertEquals(163.20, amount);
    }
    
    @Test
    public void getAmountTTCTest() throws Exception{
        double amount = productInvoicesWithTva.getAmountTTC(17);
        assertEquals(1123.20, amount);
    }

    @Test
    public void getTvaAmountNoTvaTest() throws Exception{
        double amount = productInvoicesNoTva.getTvaAmount(17);
        assertEquals(0.0, amount);
    }

    @Test
    public void getAmountTTCNoTvaTest() throws Exception{
        double amount = productInvoicesNoTva.getAmountTTC(17);
        assertEquals(960.00, amount);
    }
}
