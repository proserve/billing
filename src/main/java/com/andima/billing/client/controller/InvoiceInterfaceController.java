package com.andima.billing.client.controller;

import com.andima.billing.client.views.ProductInvoicesViews;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by GHIBOUB Khalid  on 21/08/2014.
 */
public class InvoiceInterfaceController implements Initializable{
    @FXML
    private GridPane resume;

    @FXML
    private Label Tva17;

    @FXML
    private AnchorPane root;

    @FXML
    private Label montantTTC;

    @FXML
    private Pane pane;

    @FXML
    private Label mountantHt;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductInvoicesViews productInvoicesViews = new ProductInvoicesViews();
        pane.getChildren().add(productInvoicesViews);
        resume.setLayoutY(productInvoicesViews.getHeight() + pane.getLayoutY() + 50);
        montantTTC.textProperty().bind(productInvoicesViews.montantTTCSumProperty());
    }

    public void print() {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT,
                Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / root.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / root.getBoundsInParent().getHeight();
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
}
