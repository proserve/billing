package com.andima.billing.core.service;


import com.andima.billing.core.request.invoice.InvoiceDetail;

import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public interface InvoicesPersistenceService {
    public InvoiceDetail createInvoice(InvoiceDetail detail);
    public List<InvoiceDetail> getAllInvoices();
    public void deleteAllInvoices();
    public void delete(int key);
    public InvoiceDetail update(InvoiceDetail detail);
    public InvoiceDetail getOneInvoice(int key);
}
