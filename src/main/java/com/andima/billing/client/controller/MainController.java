package com.andima.billing.client.controller;

import com.andima.billing.client.App;
import com.andima.billing.client.views.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.dialog.Dialogs;

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

    @FXML
    private AnchorPane tableAnchorPane;
    private InvoicesTableView invoicesTableView;

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
    private NotificationPane notificationPane;

    @FXML
    void initialize() {
        assert toolBar != null : "fx:id=\"toolBar\" was not injected: check your FXML file 'main.fxml'.";
        assert addProductBtn != null : "fx:id=\"addProductBtn\" was not injected: check your FXML file 'main.fxml'.";
        assert mainAnchor != null : "fx:id=\"mainAnchor\" was not injected: check your FXML file 'main.fxml'.";
        invoicesTableView = new InvoicesTableView();
        tableAnchorPane.getChildren().add(invoicesTableView);
        AnchorPane.setTopAnchor(invoicesTableView, 0.0);
        AnchorPane.setRightAnchor(invoicesTableView, 0.0);
        AnchorPane.setBottomAnchor(invoicesTableView, 0.0);
        AnchorPane.setLeftAnchor(invoicesTableView, 0.0);
        mainAnchor.getChildren().add(getPane());
    }


    private Stage showNewStage(Parent parent) {
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    public void deleteFacture(){

      invoicesTableView.delete();
    }

    public void closeApp(){
        App.stage.close();
    }
    public void showInvoiceInterface() {
        try {
            showNewStage((Parent) FXMLLoader.load(getClass().getResource("/fxml/invoice.fxml")));

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

    public Node getPane(){
        notificationPane = new NotificationPane();
        notificationPane.getActions().addAll(new AbstractAction("Non") {
            @Override public void handle(ActionEvent ae) {
                notificationPane.hide();
            }
        });
        notificationPane.getActions().addAll(new AbstractAction("Oui") {
            @Override public void handle(ActionEvent ae) {
                notificationPane.hide();
            }
        });



        VBox root = new VBox(10);
        root.setFillWidth(true);
        root.setPadding(new Insets(50, 0, 0, 10));
        notificationPane.setContent(root);
        notificationPane.setText("Vous voullez vraiement supprimer cette facture");
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
        return notificationPane;
    }

}
