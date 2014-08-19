package com.andima.billing.core.request.product.response;

import com.andima.billing.core.request.product.ProductDetail;
import com.andima.billing.core.request.responses.CreateResponse;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public class CreateProductResponse extends CreateResponse {
    private ProductDetail detail;
    private int key;

    public CreateProductResponse(ProductDetail detail, int key) {
        this.detail = detail;
        this.key = key;
    }

    public CreateProductResponse() {
    }

    public ProductDetail getDetail() {
        return detail;
    }

    public int getKey() {
        return key;
    }

}