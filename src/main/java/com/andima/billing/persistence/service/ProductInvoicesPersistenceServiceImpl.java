package com.andima.billing.persistence.service;

import com.andima.billing.core.request.productInvoices.ProductInvoiceDetail;
import com.andima.billing.core.service.ProductInvoicesPersistenceService;
import com.andima.billing.persistence.domain.ProductInvoice;
import com.andima.billing.persistence.repository.ProductInvoicesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 20/08/2014.
 */
public class ProductInvoicesPersistenceServiceImpl implements ProductInvoicesPersistenceService{
    @Autowired
    ProductInvoicesRepository productInvoicesRepository;

    @Override
    public ProductInvoiceDetail createProductInvoice(ProductInvoiceDetail detail) {
        ProductInvoice product = ProductInvoice.fromProductInvoiceDetail(detail);
        ProductInvoice savedProductInvoice = productInvoicesRepository.save(product);
        ProductInvoiceDetail productDetail = savedProductInvoice.toProductInvoiceDetail();
        return productDetail;
    }

    @Override
    public List<ProductInvoiceDetail> getAllProductInvoices() {
        List<ProductInvoice> products = productInvoicesRepository.findAll();
        List<ProductInvoiceDetail> productDetails = new ArrayList<ProductInvoiceDetail>();
        for (ProductInvoice product : products) {
            productDetails.add(product.toProductInvoiceDetail());
        }
        return productDetails;
    }

    @Override
    public void deleteAllProductInvoices() {
        productInvoicesRepository.deleteAll();
    }

    @Override
    public void delete(int key) {
        productInvoicesRepository.delete(key);
    }

    @Override
    public ProductInvoiceDetail update(ProductInvoiceDetail detail) {
        ProductInvoice product = productInvoicesRepository.findOne(detail.getNumber());
        BeanUtils.copyProperties(detail, product);
        ProductInvoice save = productInvoicesRepository.save(product);
        return save.toProductInvoiceDetail();
    }

}
