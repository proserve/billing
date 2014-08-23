package com.andima.billing.client.controller;

import com.andima.billing.client.views.ProductInvoicesViews;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by GHIBOUB Khalid  on 21/08/2014.
 */
public class InvoiceInterfaceController implements Initializable{
    @FXML private TextArea letterSum;
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
    private Pane signaturePane;

    @FXML
    private VBox sumVbox;

    @FXML
    private Label montantHt;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ProductInvoicesViews productInvoicesViews = new ProductInvoicesViews();
        pane.getChildren().add(productInvoicesViews);
       // resume.setLayoutY(productInvoicesViews.getHeight() + pane.getLayoutY() + 50);
;
        resume.setMaxHeight(150);
        sumVbox.setLayoutY(resume.getLayoutY());
        montantTTC.textProperty().bind(productInvoicesViews.montantTTCSumProperty());
        montantHt.textProperty().bind(productInvoicesViews.montantTHSumProperty());
        Tva17.textProperty().bind(productInvoicesViews.montantTVASumProperty());
        letterSum.textProperty().bind(productInvoicesViews.letterSumProperty());
       signaturePane.setLayoutY(resume.getLayoutY()+resume.getMaxHeight()+20);

    }

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
}
