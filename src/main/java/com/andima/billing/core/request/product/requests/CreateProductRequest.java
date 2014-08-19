package com.andima.billing.core.request.product.requests;

import com.andima.billing.core.request.product.ProductDetail;
import com.andima.billing.core.request.requests.CreateRequest;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public class CreateProductRequest extends CreateRequest {
    private ProductDetail detail;

    public CreateProductRequest(ProductDetail detail) {
        this.detail = detail;
    }

    public ProductDetail getDetail() {
        return detail;
    }
}
