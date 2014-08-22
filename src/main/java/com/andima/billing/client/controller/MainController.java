package com.andima.billing.client.controller;

import com.andima.billing.client.views.ProductInvoicesViews;
import com.andima.billing.client.views.addressViews;
import com.andima.billing.client.views.clientViews;
import com.andima.billing.client.views.productViews;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public class MainController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToolBar toolBar;

    @FXML
    private Button addProductBtn;

    @FXML
    private AnchorPane mainAnchor;

    public void showProductInterface() {
        showNewStage(new productViews());
    }

    public void showAddressInterface() {
        showNewStage(new addressViews());
    }

    public void showClientInterface() {
        showNewStage(new clientViews());
    }

    public void showInvoiceProductInterface() {
        showNewStage(new ProductInvoicesViews());
    }

    @FXML
    void initialize() {
        assert toolBar != null : "fx:id=\"toolBar\" was not injected: check your FXML file 'main.fxml'.";
        assert addProductBtn != null : "fx:id=\"addProductBtn\" was not injected: check your FXML file 'main.fxml'.";
        assert mainAnchor != null : "fx:id=\"mainAnchor\" was not injected: check your FXML file 'main.fxml'.";

    }


    private Stage showNewStage(Parent parent) {
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    public void showInvoiceInterface() {
        try {
            showNewStage((Parent) FXMLLoader.load(getClass().getResource("/fxml/invoice_inteface.fxml")));

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void print(ActionEvent actionEvent) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT,
                Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / mainAnchor.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / mainAnchor.getBoundsInParent().getHeight();
        mainAnchor.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(mainAnchor);
            if (success) {
                job.endJob();
                }
            }
    }
}
