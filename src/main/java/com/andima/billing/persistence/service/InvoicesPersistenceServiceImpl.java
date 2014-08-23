package com.andima.billing.persistence.service;

import com.andima.billing.core.request.invoice.InvoiceDetail;
import com.andima.billing.core.service.InvoicesPersistenceService;
import com.andima.billing.persistence.domain.Invoice;
import com.andima.billing.persistence.repository.InvoicesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 20/08/2014.
 */
public class InvoicesPersistenceServiceImpl implements InvoicesPersistenceService{
    @Autowired
    InvoicesRepository invoicesRepository;

    @Override
    public InvoiceDetail createInvoice(InvoiceDetail detail) {
        Invoice invoice = Invoice.fromInvoiceDetail(detail);
        Invoice savedInvoice = invoicesRepository.save(invoice);
        InvoiceDetail invoiceDetail = savedInvoice.toInvoiceDetail();
        return invoiceDetail;
    }

    @Override
    public List<InvoiceDetail> getAllInvoices() {
        List<Invoice> invoices = invoicesRepository.findAll();
        List<InvoiceDetail> invoiceDetails = new ArrayList<InvoiceDetail>();
        for (Invoice invoice : invoices) {
            invoiceDetails.add(invoice.toInvoiceDetail());
        }
        return invoiceDetails;
    }

    @Override
    public void deleteAllInvoices() {
        invoicesRepository.deleteAll();
    }

    @Override
    public void delete(int key) {
        invoicesRepository.delete(key);
    }

    @Override
    public InvoiceDetail update(InvoiceDetail detail) {

        Invoice invoice = invoicesRepository.findOne(detail.getNumber());
        if(invoice != null){
            BeanUtils.copyProperties(detail, invoice);
            Invoice save = invoicesRepository.save(invoice);
            return save.toInvoiceDetail();
        }else return createInvoice(detail);
    }

    @Override
    public InvoiceDetail getOneInvoice(int key) {
        Invoice invoice = invoicesRepository.findOne(key);
        return invoice.toInvoiceDetail();
    }

}
