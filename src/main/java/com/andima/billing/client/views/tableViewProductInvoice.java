package com.andima.billing.client.views;

import com.andima.billing.client.App;
import com.andima.billing.client.domain.ProductInvoice;
import com.andima.billing.core.request.product.ProductDetail;
import com.andima.billing.core.service.ProductsPersistenceService;
import com.andima.billing.core.useCases.FrenchNumberToWords;
import com.panemu.tiwulfx.control.NumberField;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import java.util.List;
import java.util.Set;

import static com.andima.billing.client.util.SpringUtil.getBean;

/**
 * Created by GHIBOUB Khalid  on 27/08/2014.
 */
public class tableViewProductInvoice extends AnchorPane {
    private final VBox controlHbox;
    private SimpleObjectProperty<String> montantTTCSum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> montantTVASum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> montantTHSum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> letterSum = new SimpleObjectProperty<String>("");

    public VBox getControlHbox() {
        return controlHbox;
    }

    public String getMontantTTCSum() {
        return montantTTCSum.get();
    }

    public SimpleObjectProperty<String> montantTTCSumProperty() {
        return montantTTCSum;
    }

    public String getMontantTVASum() {
        return montantTVASum.get();
    }

    public SimpleObjectProperty<String> montantTVASumProperty() {
        return montantTVASum;
    }

    public String getMontantTHSum() {
        return montantTHSum.get();
    }

    public SimpleObjectProperty<String> montantTHSumProperty() {
        return montantTHSum;
    }

    public String getLetterSum() {
        return letterSum.get();
    }

    public SimpleObjectProperty<String> letterSumProperty() {
        return letterSum;
    }

    ProductsPersistenceService productsPersistenceService =
            getBean(ProductsPersistenceService.class);
    private final TableView<ProductInvoice> tableView = new TableView<ProductInvoice>();

    List<ProductDetail> allProducts = productsPersistenceService.getAllProducts();

    public TableView<ProductInvoice> getTableView() {
        return tableView;
    }

    private void setTableFooterValues() {

        double ttc = 0;
        double tva = 0;
        double th = 0;

        if(tableView.getItems() != null)
        for (ProductInvoice productInvoice : tableView.getItems()) {
            ttc += getTTCAmount(productInvoice);
            tva += getTVAAmount(productInvoice);
            th += getHtAmount(productInvoice);
        }
        montantTTCSum.set(String.valueOf(ttc));
        montantTHSum.set(String.valueOf(th));
        montantTVASum.set(String.valueOf(tva));
        letterSum.set(new FrenchNumberToWords().convert((long) ttc));
    }

    public tableViewProductInvoice() {
        SimpleObjectProperty<ObservableList<ProductInvoice>> observableListSimpleObjectProperty =
                new SimpleObjectProperty<ObservableList<ProductInvoice>>();
        final TableColumn<ProductInvoice, String> designationColumn = createDesignationTableColumn();
        final TableColumn<ProductInvoice, Integer> quantityColumn = createQuantityTableColumn();
        final TableColumn<ProductInvoice, String> uMColumn = createUMTableColumn();
        final TableColumn<ProductInvoice, Double> priceColumn = createPriceTableColumn();
        final TableColumn<ProductInvoice, Boolean> isWithTvaColumn = createIsWithTvaTableColumn();
        final TableColumn<ProductInvoice, Double> hTAmountColumn = createHTAmountTableColumn();
        final TableColumn<ProductInvoice, Double> TVAAmountColumn = createTVAAmountTableColumn();
        final TableColumn<ProductInvoice, Double> TCCAmountColumn = createTTCAmountTableColumn();

       /* tableView.itemsProperty().bindBidirectional(observableListSimpleObjectProperty);*/
        tableView.getColumns().addAll(designationColumn, quantityColumn, uMColumn, priceColumn,  hTAmountColumn,
                TVAAmountColumn,TCCAmountColumn, isWithTvaColumn);
        /*tableView.setItems(createTestData());*/
        tableView.setEditable(true);

        updateObservableListProperties(designationColumn, quantityColumn, uMColumn, priceColumn, isWithTvaColumn);

        this.getChildren().addAll(tableView);

        controlHbox = createAddLineHbox();
        getChildren().add(controlHbox);
        AnchorPane.setRightAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);
        AnchorPane.setBottomAnchor(tableView, 0.0);
        AnchorPane.setTopAnchor(tableView, 90.0);
        this.getStylesheets().add("css/theme.css");
        setTableFooterValues();
    }

    private VBox createAddLineHbox() {
        VBox toolBar = new VBox();
        HBox form = new HBox(7.0);
        HBox commandes = new HBox(10.0);
        toolBar.getStylesheets().add("css/JMetroLightTheme.css");
        toolBar.setPadding(new Insets(10.0));

        final NumberField<Integer> quantityField = createQuantityField();

        final ComboBox<String> designationField = createDesignationField();
        final ComboBox<String> uMField = createUMField();
        final NumberField<Double> priceField = createPriceField();
        final CheckBox isWithTVA = createIsWithTvaField();
        isWithTVA.setPadding(new Insets(30.0, 0.0, 0.0 , 0.0));
        quantityField.getStyleClass().addAll(new TextField().getStyleClass());
        designationField.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ProductDetail productDetail = productsPersistenceService.getByName(newValue);
                uMField.getEditor().setText(productDetail.getUM());
                priceField.setValue(productDetail.getUnitPrice());
            }
        });
        Button ajouter = new Button("Ajouter");
        //ajouter.getStylesheets().add("css/DarkTheme.css");
        ajouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final ProductInvoice e = new ProductInvoice();
                e.setDesignation(designationField.getEditor().getText());
                designationField.getEditor().setText("");
                e.setQuantity(quantityField.getValue());
                quantityField.setText("");
                e.setUM(uMField.getEditor().getText());
                uMField.getEditor().setText("");
                e.setUnitPrice(priceField.getValue());
                priceField.setText("");
                e.setWithTVA(isWithTVA.isSelected());
                isWithTVA.setSelected(false);
                tableView.getItems().add(e);
            }
        });
        Button supprimer = new Button("Supprimer");
        supprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProductInvoice selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    Action response = Dialogs.create()
                            .title("Confirmation")
                            .message(
                                    "Vous voullez vraiment supprimer cette commandes ").
                                    style(DialogStyle.NATIVE).
                                    owner(App.stage).
                                    showConfirm();

                    if(response == Dialog.Actions.YES){
                        tableView.getItems().remove(selectedItem);
                    }
                }

            }
        });

        Button refresh = new Button("refresh");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refresh();
            }
        });
        form.getChildren().addAll(designationField, quantityField, uMField, priceField, isWithTVA);
        commandes.getChildren().addAll(ajouter, supprimer, refresh);
        toolBar.getChildren().addAll(form, commandes);
        return toolBar;
    }

    private CheckBox createIsWithTvaField(){
        final CheckBox checkBox = new CheckBox("Avec TVA");
        return checkBox;
    }

    private NumberField<Integer> createQuantityField() {
        final NumberField<Integer> quantityField = new NumberField<Integer>(Integer.class);
        quantityField.setPromptText("Qté");
        return quantityField;
    }

    private NumberField<Double> createPriceField() {
        final NumberField<Double> priceField = new NumberField<Double>(Double.class);
        priceField.setPromptText("Prix U (HT)");
        return priceField;
    }

    private ComboBox<String> createDesignationField() {
        return createEditableComboBoxField(getProductNames(), "la Désignation");
    }

    private ComboBox<String> createUMField() {
        ObservableList<String> items = FXCollections.observableArrayList("K", "U");
        return createEditableComboBoxField(items, "Unité métrique");
    }

    private ComboBox<String> createEditableComboBoxField(ObservableList<String> items, String promptText) {
        final ComboBox<String> stringComboBox = new ComboBox<String>(items);
        stringComboBox.setEditable(true);
        stringComboBox.setPromptText(promptText);
        return stringComboBox;
    }

    private ObservableList<ProductInvoice> createTestData() {
        ProductInvoice productInvoice = new ProductInvoice();
        productInvoice.setDesignation("ghiboub khalid");
        productInvoice.setQuantity(15);
        return FXCollections.observableArrayList(
                productInvoice
        );
    }

    private TableColumn<ProductInvoice, String> createDesignationTableColumn() {
        final TableColumn<ProductInvoice, String> designationColumn = createStringTableCol("Désignation", "designation");
        designationColumn.setCellFactory(new Callback<TableColumn<ProductInvoice, String>, TableCell<ProductInvoice, String>>() {
            @Override
            public TableCell<ProductInvoice, String> call(TableColumn<ProductInvoice, String> param) {
                return new EditingCell();
            }
        });
        designationColumn.setPrefWidth(200);
        return designationColumn;
    }

    private TableColumn<ProductInvoice, Integer> createQuantityTableColumn() {
        final TableColumn<ProductInvoice, Integer> quantityColumn =
                new TableColumn<ProductInvoice, Integer>("Qté");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<ProductInvoice, Integer>("quantity"));
        quantityColumn.setPrefWidth(70);
        quantityColumn.setCellFactory(TextFieldTableCell.<ProductInvoice, Integer>forTableColumn(new IntegerStringConverter()));
        return quantityColumn;
    }

    private TableColumn<ProductInvoice, Double> createPriceTableColumn() {
        final TableColumn<ProductInvoice, Double> priceColumn =
                new TableColumn<ProductInvoice, Double>("Prix U (HT)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<ProductInvoice, Double>("unitPrice"));
        priceColumn.setCellFactory(TextFieldTableCell.<ProductInvoice, Double>forTableColumn(new DoubleStringConverter()));
        priceColumn.setPrefWidth(100);
        return priceColumn;
    }

    private TableColumn<ProductInvoice, String> createUMTableColumn() {
        final TableColumn<ProductInvoice, String> UMTableColumn = createStringTableCol("U/M", "UM");
        UMTableColumn.setCellFactory(TextFieldTableCell.<ProductInvoice, String>forTableColumn(new DefaultStringConverter()));
        UMTableColumn.setPrefWidth(50);
        return UMTableColumn;
    }

    private TableColumn<ProductInvoice, String> createStringTableCol(String text, String property) {
        final TableColumn<ProductInvoice, String> stringTableColumn = new TableColumn<ProductInvoice, String>(text);
        stringTableColumn.setCellValueFactory(new PropertyValueFactory<ProductInvoice, String>(property));
        stringTableColumn.setCellFactory(new Callback<TableColumn<ProductInvoice, String>, TableCell<ProductInvoice, String>>() {
            @Override
            public TableCell<ProductInvoice, String> call(TableColumn<ProductInvoice, String> param) {
                return new EditingCell();
            }
        });
        return stringTableColumn;
    }

    private TableColumn<ProductInvoice, Boolean> createIsWithTvaTableColumn() {
        final TableColumn<ProductInvoice, Boolean> booleanTableColumn =
                new TableColumn<ProductInvoice, Boolean>("TVA");
        booleanTableColumn.setCellValueFactory(new PropertyValueFactory<ProductInvoice, Boolean>("withTVA"));
        booleanTableColumn.setCellFactory(new Callback<TableColumn<ProductInvoice, Boolean>, TableCell<ProductInvoice, Boolean>>() {
            @Override
            public TableCell<ProductInvoice, Boolean> call(TableColumn<ProductInvoice, Boolean> param) {
                return new CheckBoxTableCell();
            }
        });
        booleanTableColumn.setPrefWidth(40);
        return booleanTableColumn;
    }

    private TableColumn<ProductInvoice, Double> createHTAmountTableColumn() {
        TableColumn<ProductInvoice, Double> HTAmountTableColumn = createAmountTableColumn("Montant HT");
        HTAmountTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductInvoice, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<ProductInvoice, Double> param) {
                ProductInvoice value = param.getValue();
                return new SimpleObjectProperty<Double>(getHtAmount(value));
            }
        });
        return HTAmountTableColumn;
    }

    private double getHtAmount(ProductInvoice value) {
        return value.getUnitPrice() * value.getQuantity();
    }

    private TableColumn<ProductInvoice, Double> createTVAAmountTableColumn() {
        TableColumn<ProductInvoice, Double> HTAmountTableColumn = createAmountTableColumn("Montant TVA");
        HTAmountTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductInvoice, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<ProductInvoice, Double> param) {
                ProductInvoice value = param.getValue();
                return new SimpleObjectProperty<Double>((value.isWithTVA()) ? getTVAAmount(value) : 0);
            }
        });
        return HTAmountTableColumn;
    }

    private double getTVAAmount(ProductInvoice value) {
        return getHtAmount(value)*17/100;
    }

    private TableColumn<ProductInvoice, Double> createTTCAmountTableColumn() {
        TableColumn<ProductInvoice, Double> HTAmountTableColumn = createAmountTableColumn("Montant TTC");
        HTAmountTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductInvoice, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<ProductInvoice, Double> param) {
                ProductInvoice value = param.getValue();
                return new SimpleObjectProperty<Double>(getTTCAmount(value));
            }
        });
        return HTAmountTableColumn;
    }

    private double getTTCAmount(ProductInvoice value) {
        return (value.isWithTVA()) ? getTVAAmount(value)+getHtAmount(value) : getHtAmount(value);
    }

    private TableColumn<ProductInvoice, Double> createAmountTableColumn(String text) {
        final TableColumn<ProductInvoice, Double> HtAmountTableColumn =
                new TableColumn<ProductInvoice, Double>(text);
        HtAmountTableColumn.setEditable(false);
        HtAmountTableColumn.setPrefWidth(150);
        return HtAmountTableColumn;
    }
    private void updateObservableListProperties(TableColumn designationCol, TableColumn quantityCol,
                                                TableColumn<ProductInvoice, String> uMColumn,
                                                TableColumn<ProductInvoice, Double> priceColumn, TableColumn<ProductInvoice, Boolean> isWithTvaColumn) {
        designationCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductInvoice, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductInvoice, String> t) {
                ProductInvoice productInvoice = t.getTableView().getItems().get(
                        t.getTablePosition().getRow());
                productInvoice.setDesignation(t.getNewValue());
                refresh();
            }
        });
        quantityCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductInvoice, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductInvoice, Integer> t) {
                (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setQuantity(t.getNewValue());
                refresh();
            }
        });

        uMColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductInvoice, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductInvoice, String> t) {
                ( t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setUM(t.getNewValue());
                refresh();
            }
        });

        priceColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductInvoice, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductInvoice, Double> t) {
                ( t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setUnitPrice(t.getNewValue());
                refresh();
            }
        });

        isWithTvaColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductInvoice, Boolean>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductInvoice, Boolean> t) {
                ( t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setWithTVA(t.getNewValue());
                refresh();
            }
        });
    }

    public class EditingCell extends TableCell<ProductInvoice, String> {
        private ComboBox<String> comboBox;

        public EditingCell() {

        }

        @Override
        public void startEdit() {
            super.startEdit();

            if (comboBox == null) {
                createTextField();
            }
            setText(null);
            setGraphic(comboBox);
            comboBox.getEditor().selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            endEditing();
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (comboBox != null) {
                        comboBox.getEditor().setText(getString());
                    }
                    setText(null);
                    setGraphic(comboBox);
                } else {
                    endEditing();
                }
            }
        }

        private void endEditing() {

            setText(getString());
            Text graphic = new Text(getString());
            graphic.wrappingWidthProperty().bind(widthProperty().subtract(10));
            graphic.setTextAlignment(TextAlignment.CENTER);
            setGraphic(graphic);
        }

        private void createTextField() {
            ObservableList<String> data = getProductNames();

            comboBox = new ComboBox<String>(data);
            comboBox.getStylesheets().add("css/JMetroLightTheme.css");
            comboBox.getEditor().requestFocus();
            comboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ProductDetail byName = productsPersistenceService.getByName(newValue);
                    ProductInvoice selectedItem = tableView.getSelectionModel().getSelectedItem();
                    selectedItem.setUnitPrice(byName.getUnitPrice());
                    selectedItem.setUM(byName.getUM());

                }
            });
            comboBox.getEditor().setText(getString());
            comboBox.setEditable(true);
            comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            comboBox.getEditor().setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.SHIFT) {
                        commitEdit(comboBox.getEditor().getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    private ObservableList<String> getProductNames() {
        ObservableList<String> data = FXCollections.observableArrayList();
        for (ProductDetail product : allProducts) {
            data.add(product.getName());
        }
        return data;
    }
    public void refresh() {
        setTableFooterValues();
        Set<Node> nodes = tableView.lookupAll(".table-row-cell");
        for (Node node : nodes) {
            if (node instanceof TableRow) {
                TableRow<ProductInvoice> row = (TableRow) node;
                ProductInvoice item = row.getItem();
                row.setItem(null);
                row.setItem(item);
            }
        }
    }
    public class CheckBoxTableCell extends TableCell<ProductInvoice, Boolean> {
        private CheckBox checkBox;


        public CheckBoxTableCell() {
        }

        @Override
        public void startEdit() {
            super.startEdit();

            if (checkBox == null) {
                createTextField();
            }
            setText(null);
            setGraphic(checkBox);
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            endEditing();
        }

        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (checkBox != null) {
                        checkBox.setSelected(item);
                    }
                    setText(null);
                    setGraphic(checkBox);
                } else {
                    endEditing();
                }
            }
        }

        private void endEditing() {
            setText(getString());
            setGraphic(null);
        }

        private void createTextField() {
            checkBox = new CheckBox();
            checkBox.requestFocus();
            checkBox.getStylesheets().add("css/JMetroLightTheme.css");
            checkBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            checkBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.SHIFT) {
                        commitEdit(checkBox.isSelected());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : (getItem()? "Oui" : "Non");
        }
    }
}
