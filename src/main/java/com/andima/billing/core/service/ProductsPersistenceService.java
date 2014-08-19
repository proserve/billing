package com.andima.billing.core.service;

import com.andima.billing.core.request.product.ProductDetail;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public interface ProductsPersistenceService {
    public ProductDetail createProduct(ProductDetail detail);
    public void deleteAllProduct();
}
