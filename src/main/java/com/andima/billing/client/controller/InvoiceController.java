package com.andima.billing.client.controller;

import com.andima.billing.client.domain.Invoice;
import com.andima.billing.client.domain.ProductInvoice;
import com.andima.billing.client.util.SpringUtil;
import com.andima.billing.client.views.ProductInvoicesViews;
import com.andima.billing.core.request.invoice.InvoiceDetail;
import com.andima.billing.core.service.InvoicesPersistenceService;
import com.andima.billing.core.service.ProductInvoicesPersistenceService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by GHIBOUB Khalid  on 22/08/2014.
 */


public class InvoiceController implements Initializable {
    @FXML
    private VBox root;
    @FXML
    private Label montantTTC;

    @FXML
    private Pane pane;

    @FXML
    private Pane signaturePane;

    @FXML
    private VBox sumVbox;

    @FXML
    private Label montantHt;
    @FXML
    private TextArea letterSum;
    @FXML
    private GridPane resume;

    @FXML
    private Label Tva17;
    private ProductInvoicesPersistenceService persistenceService =
            SpringUtil.getBean(ProductInvoicesPersistenceService.class);
    private InvoicesPersistenceService invoicesPersistenceService =
            SpringUtil.getBean(InvoicesPersistenceService.class);
    private ProductInvoicesViews productInvoicesViews;

    public void print() {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT,
                Printer.MarginType.DEFAULT);

        double scaleX = pageLayout.getPrintableWidth() / root.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / root.getBoundsInParent().getHeight();
        System.out.println("scale Y ==> " + scaleY);
        System.out.println("scale Y ==> " + scaleX);
        Scale scale = new Scale(scaleX, scaleY);
        root.getTransforms().add(scale);

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(root);
            if (success) {
                job.endJob();
            }
        }
        root.getTransforms().remove(scale);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productInvoicesViews = new ProductInvoicesViews();
        pane.getChildren().add(productInvoicesViews);
        montantTTC.textProperty().bind(productInvoicesViews.montantTTCSumProperty());
        montantHt.textProperty().bind(productInvoicesViews.montantTHSumProperty());
        Tva17.textProperty().bind(productInvoicesViews.montantTVASumProperty());
        letterSum.textProperty().bind(productInvoicesViews.letterSumProperty());
    }

    public void saveInvoice() {
        InvoiceDetail invoiceDetail = invoicesPersistenceService.createInvoice(new InvoiceDetail());
        System.out.println(invoiceDetail.getNumber() + "ghiboub");
        for (ProductInvoice productInvoice : productInvoicesViews.getProductInvoice()) {
            productInvoice.setInvoice(Invoice.fromInvoiceDetail(invoiceDetail));
            persistenceService.update(productInvoice.toProductInvoiceDetail());
        }
    }
}
