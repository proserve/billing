package com.andima.billing.client.util;

import com.andima.billing.client.domain.ProductInvoice;
import com.andima.billing.core.request.productInvoices.ProductInvoiceDetail;
import com.andima.billing.core.service.ProductInvoicesPersistenceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 22/08/2014.
 */
public class GetDailySalesTask extends Task {
    ProductInvoicesPersistenceService persistenceService = SpringUtil.getBean(ProductInvoicesPersistenceService.class);
    @Override
    protected Object call() throws Exception {
        for (int i = 0; i < 500; i++) {
            updateProgress(i, 500);
            Thread.sleep(5);
        }
        List<ProductInvoiceDetail> allProductInvoiceDetails = persistenceService.getAllProductInvoices();
        List<ProductInvoice> allProductInvoices = new ArrayList<ProductInvoice>();
        for (ProductInvoiceDetail addressDetail : allProductInvoiceDetails) {
            allProductInvoices.add(ProductInvoice.fromProductInvoiceDetail(addressDetail));
        }

        ObservableList<ProductInvoice> observableList = FXCollections.observableArrayList(allProductInvoices);
        return observableList;
    }
}
