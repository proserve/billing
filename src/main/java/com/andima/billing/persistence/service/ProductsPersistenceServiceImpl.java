package com.andima.billing.persistence.service;

import com.andima.billing.core.service.ProductsPersistenceService;
import com.andima.billing.core.request.product.ProductDetail;
import com.andima.billing.persistence.domain.Product;
import com.andima.billing.persistence.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public class ProductsPersistenceServiceImpl implements ProductsPersistenceService {

    @Autowired
    ProductsRepository productsRepository;

    @Override
    public ProductDetail createProduct(ProductDetail detail) {
        Product product = Product.fromProductDetail(detail);
        Product savedProduct = productsRepository.save(product);
        ProductDetail productDetail = savedProduct.toProductDetail();
        return productDetail;
    }

    @Override
    public void deleteAllProduct() {
        productsRepository.deleteAll();
    }
}