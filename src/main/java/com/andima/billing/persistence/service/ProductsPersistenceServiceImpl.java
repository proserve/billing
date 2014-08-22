package com.andima.billing.persistence.service;

import com.andima.billing.core.request.product.ProductDetail;
import com.andima.billing.core.service.ProductsPersistenceService;
import com.andima.billing.persistence.domain.Product;
import com.andima.billing.persistence.repository.ProductsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
    public List<ProductDetail> getAllProducts() {
        List<Product> products = productsRepository.findAll();
        List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
        for (Product product : products) {
            productDetails.add(product.toProductDetail());
        }
        return productDetails;
    }

    @Override
    public void deleteAllProduct() {
        productsRepository.deleteAll();
    }

    @Override
    public void delete(int key) {
        productsRepository.delete(key);
    }

    @Override
    public ProductDetail update(ProductDetail detail) {
        Product product = productsRepository.findOne(detail.getNumber());
        BeanUtils.copyProperties(detail, product);
        Product save = productsRepository.save(product);
        return save.toProductDetail();
    }

    @Override
    public ProductDetail getByName(String designation) {
        Product product = productsRepository.findByName(designation);
        if (product != null) {
            return product.toProductDetail();
        }
        return new ProductDetail();
    }
}