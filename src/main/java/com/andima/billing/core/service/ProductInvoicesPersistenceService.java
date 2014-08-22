package com.andima.billing.core.service;


import com.andima.billing.core.request.productInvoices.ProductInvoiceDetail;

import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public interface ProductInvoicesPersistenceService {
    public ProductInvoiceDetail createProductInvoice(ProductInvoiceDetail detail);
    public List<ProductInvoiceDetail> getAllProductInvoices();
    public void deleteAllProductInvoices();
    public void delete(int key);
    public ProductInvoiceDetail update(ProductInvoiceDetail detail);
}
