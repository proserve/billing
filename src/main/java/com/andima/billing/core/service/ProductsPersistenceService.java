package com.andima.billing.core.service;

import com.andima.billing.core.request.product.ProductDetail;

import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public interface ProductsPersistenceService {
    public ProductDetail createProduct(ProductDetail detail);
    public List<ProductDetail> getAllProducts();
    public void deleteAllProduct();
    public void delete(int key);
    public ProductDetail update(ProductDetail detail);
    public ProductDetail getByName(String designation);
}
