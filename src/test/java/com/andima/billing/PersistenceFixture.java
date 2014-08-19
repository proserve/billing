package com.andima.billing;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */

import com.andima.billing.core.request.product.ProductDetail;
import com.andima.billing.persistence.domain.Product;

/**
 * Created by proserve on 03/08/14.
 */
public class PersistenceFixture {
    public static Product createProduct(String name) {
        Product product = new Product(name, "U", 3560.00);
        return product;
    }

    public static ProductDetail createProductDetail(String name) {
        ProductDetail detail = new ProductDetail(name, "K", 250.00);
        return detail;
    }
}