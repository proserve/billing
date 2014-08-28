package com.andima.billing.client.controller;

import com.andima.billing.client.domain.Address;
import com.andima.billing.client.domain.Invoice;
import com.andima.billing.client.domain.ProductInvoice;
import com.andima.billing.client.views.tableViewProductInvoice;
import com.andima.billing.core.request.client.ClientDetail;
import com.andima.billing.core.request.invoice.InvoiceDetail;
import com.andima.billing.core.request.productInvoices.ProductInvoiceDetail;
import com.andima.billing.core.service.ClientsPersistenceService;
import com.andima.billing.core.service.InvoicesPersistenceService;
import com.andima.billing.core.service.ProductInvoicesPersistenceService;
import com.panemu.tiwulfx.control.NumberField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.andima.billing.client.util.SpringUtil.getBean;

/**
 * Created by GHIBOUB Khalid  on 22/08/2014.
 */


public class InvoiceController implements Initializable {
    public ComboBox<String> fullNameComboBox;
    public TextField NCF;

    public TextArea addressTextField;

    public DatePicker datePiker;

    public NumberField factureField;
    public Label factureLabel;
    public Label dateLabel;
    public Label fullName;
    public Label addressLabel;
    public Label NCFLabel;
    public Label reglerLabel;
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
            getBean(ProductInvoicesPersistenceService.class);
    private InvoicesPersistenceService invoicesPersistenceService =
            getBean(InvoicesPersistenceService.class);
    private tableViewProductInvoice productInvoicesViews;
    private ClientsPersistenceService clientsPersistenceService =
            getBean(ClientsPersistenceService.class);
    private List<ClientDetail> allClients = clientsPersistenceService.getAllClients();;

    public void print() {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT,
                Printer.MarginType.DEFAULT);
       productInvoicesViews.getControlHbox().setVisible(false);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productInvoicesViews = new tableViewProductInvoice();
        pane.getChildren().add(productInvoicesViews);
        montantTTC.textProperty().bind(productInvoicesViews.montantTTCSumProperty());
        montantHt.textProperty().bind(productInvoicesViews.montantTHSumProperty());
        Tva17.textProperty().bind(productInvoicesViews.montantTVASumProperty());
        //letterSum.textProperty().bind(productInvoicesViews.letterSumProperty());
        productInvoicesViews.letterSumProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                letterSum.setText(newValue);
            }
        });
        letterSum.setEditable(true);

        ObservableList<String> clients = FXCollections.observableArrayList();
        for (ClientDetail clientDetail : allClients) {
            clients.add(getFullName(clientDetail));
        }
        fullNameComboBox.setItems(clients);
        fullNameComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ClientDetail client = findClientByFullName(newValue);
                NCF.setText(client.getNCF());
                addressTextField.setText(Address.fromAddressDetail(client.getAddress()).toString());
            }
        });
        factureField.setNumberType(Integer.class);
        makeLabelEditable(factureLabel,  FXCollections.observableArrayList("FACTURE N° :", "FACTURE D'ENGRAIS N° :"));
        makeLabelEditable(dateLabel, null);
        makeLabelEditable(fullName, null);
        makeLabelEditable(addressLabel, null);
        makeLabelEditable(NCFLabel, null);
        ObservableList<String> reglerParList = FXCollections.observableArrayList("A term", "Par chèque");
        makeLabelEditable(reglerLabel, reglerParList);
    }

    private void makeLabelEditable(final Label label, final ObservableList<String> items) {
        label.setOnMouseClicked(new LabelEditableConverter(items, label));
    }

    private void endEditing(ComboBox<String> graphic, Label label) {
        label.setText(graphic.getEditor().getText());
        label.setGraphic(null);
    }
    private String getFullName(ClientDetail clientDetail) {
        return clientDetail.getLastName() + " " + clientDetail.getLastName();
    }

    private ClientDetail findClientByFullName(String fullName) {
        for (ClientDetail client : allClients) {
            if(fullName.equals(getFullName(client)))
                return client;
        }
        return null;
    }

    public void saveInvoice() {
        Invoice invoice = new Invoice();
        invoice.setAddress(addressTextField.getText());
        invoice.setDate(datePiker.getValue());
        invoice.setFullName(fullNameComboBox.getEditor().getText());
        invoice.setNCF(NCF.getText());
        InvoiceDetail detail = invoicesPersistenceService.createInvoice(invoice.toInvoiceDetail());
        for (ProductInvoice productInvoice : productInvoicesViews.getTableView().getItems()) {
            ProductInvoiceDetail productInvoiceDetail = productInvoice.toProductInvoiceDetail();
            productInvoiceDetail.setInvoiceDetail(detail);
            persistenceService.update(productInvoiceDetail);
        }
        factureField.setValue(detail.getNumber());
    }

    private class LabelEditableConverter implements EventHandler<MouseEvent> {
        private  ObservableList<String> items;
        private  Label label;
        ComboBox<String> graphic;
        public LabelEditableConverter(ObservableList<String> items, Label label) {
            this.items = items;
            this.label = label;
            graphic = new ComboBox<String>(items);
        }

        @Override
        public void handle(MouseEvent event) {
          //  if (event.getClickCount() == 2) {
                graphic.getEditor().setText(label.getText());
                graphic.setEditable(true);
                label.setGraphic(graphic);
                graphic.getEditor().setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SHIFT) {
                            endEditing(graphic, label);
                        }
                    }
                });
                graphic.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SHIFT) {
                            endEditing(graphic , label);
                        }
                    }
                });
           // }
        }
    }
}
