package com.andima.billing.client.views;

import com.andima.billing.client.util.SpringUtil;
import com.andima.billing.core.service.ProductInvoicesPersistenceService;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by GHIBOUB Khalid  on 23/08/2014.
 */
public class AddLinesToInvoiceView {
    private SimpleObjectProperty<String> montantTTCSum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> montantTVASum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> montantTHSum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> letterSum = new SimpleObjectProperty<String>("");
    private ProductInvoicesPersistenceService persistenceService =
            SpringUtil.getBean(ProductInvoicesPersistenceService.class);
}
