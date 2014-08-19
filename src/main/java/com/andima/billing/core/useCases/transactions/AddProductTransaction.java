package com.andima.billing.core.useCases.transactions;

import com.andima.billing.core.request.product.ProductDetail;
import com.andima.billing.core.request.product.response.CreateProductResponse;
import com.andima.billing.core.service.ProductsPersistenceService;

import java.sql.SQLException;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public class AddProductTransaction implements Transaction {
    ProductsPersistenceService productsPersistenceService;
    ProductDetail detail;

    public AddProductTransaction(ProductDetail detail, ProductsPersistenceService service) {
        this.detail = detail;
        this.productsPersistenceService = service;
    }

    @Override
    public CreateProductResponse execute() throws SQLException {
        ProductDetail product = productsPersistenceService.createProduct(detail);
        return new CreateProductResponse(product, product.getNumber());
    }
}
