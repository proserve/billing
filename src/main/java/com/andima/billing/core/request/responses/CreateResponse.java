package com.andima.billing.core.request.responses;

import com.andima.billing.core.request.Response;
import com.andima.billing.core.request.product.response.CreateProductResponse;

public class CreateResponse implements Response {
    protected boolean noError = true;
    protected String errorMessage = "";

    public static CreateResponse errorResponse(String message){
        CreateResponse response = new CreateResponse();
        response.noError = false;
        response.errorMessage = message;
        return response;
    }

    public boolean isNoError() {
        return noError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
