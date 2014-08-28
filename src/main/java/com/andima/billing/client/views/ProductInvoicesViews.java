package com.andima.billing.client.views;

import com.andima.billing.client.domain.Invoice;
import com.andima.billing.client.domain.Product;
import com.andima.billing.client.domain.ProductInvoice;
import com.andima.billing.client.util.SpringUtil;
import com.andima.billing.core.domain.ProductInvoiceCore;
import com.andima.billing.core.request.product.ProductDetail;
import com.andima.billing.core.service.ProductsPersistenceService;
import com.andima.billing.core.useCases.FrenchNumberToWords;
import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.control.TypeAheadField;
import com.panemu.tiwulfx.table.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public class ProductInvoicesViews extends AnchorPane {
    private SimpleObjectProperty<String> montantTTCSum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> montantTVASum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> montantTHSum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> letterSum = new SimpleObjectProperty<String>("");
    private SimpleDoubleProperty tableHeightProperty = new SimpleDoubleProperty(0);
    private List<ProductInvoice> productInvoices = new ArrayList<ProductInvoice>();

    public String getLetterSum() {
        return letterSum.get();
    }

    public SimpleObjectProperty<String> letterSumProperty() {
        return letterSum;
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

    protected TableControl<ProductInvoice> productInvoiceTableControl = new TableControl<ProductInvoice>(ProductInvoice.class);

    public static final int SMALL_PREF_WIDTH = 67;
    private double TvaPercent = 17;

    public List<ProductInvoice> getProductInvoice() {
        return productInvoiceTableControl.getTableView().getItems();
    }

    public double getTvaPercent() {
        return TvaPercent;
    }

    public void setTvaPercent(double tvaPercent) {
        TvaPercent = tvaPercent;
    }

    /*private ProductInvoicesPersistenceService persistenceService = SpringUtil.getBean(ProductInvoicesPersistenceService.class);*/
    /*private InvoicesPersistenceService invoicesPersistenceService = SpringUtil.getBean(InvoicesPersistenceService.class);*/
    private int key;
    private Invoice invoice;

    /* public ProductInvoicesViews(int invoiceKey){
         this.key = invoiceKey;
         invoice = Invoice.fromInvoiceDetail(invoicesPersistenceService.getOneInvoice(key));
         new ProductInvoicesViews();
     }*/
    public ProductInvoicesViews() {
        if (invoice == null) invoice = new Invoice();

        this.setWidth(800);
        this.setHeight(600);
        createProductInvoiceTable();
        this.getChildren().add(productInvoiceTableControl);
        AnchorPane.setTopAnchor(productInvoiceTableControl, 0.0);
        AnchorPane.setLeftAnchor(productInvoiceTableControl, 0.0);
        AnchorPane.setBottomAnchor(productInvoiceTableControl, 0.0);
        AnchorPane.setRightAnchor(productInvoiceTableControl, 0.0);
     ;

    }

    private void createProductInvoiceTable() {
        productInvoiceTableControl.setAgileEditing(false);
        NumberColumn<ProductInvoice, Integer> numberColumn = new NumberColumn<ProductInvoice, Integer>("number", Integer.class);
        productInvoiceTableControl.addColumn(numberColumn);
        numberColumn.setText("N° ");
        numberColumn.setEditable(false);
        numberColumn.setAlignment(Pos.CENTER);

        final BaseColumn<ProductInvoice, String> designationColumn = new TextColumn<ProductInvoice>("designation");
        designationColumn.setCellFactory(new Callback<TableColumn<ProductInvoice, String>, TableCell<ProductInvoice, String>>() {
            @Override
            public TableCell<ProductInvoice, String> call(TableColumn<ProductInvoice, String> param) {
                return new ComboBoxCustomCell(designationColumn);
            }
        });
        /*designationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductInvoice, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProductInvoice, String> param) {
                return new SimpleStringProperty(param.getValue().getDesignation());
            }
        });*/


        productInvoiceTableControl.addColumn(designationColumn);
        designationColumn.setAlignment(Pos.CENTER);
        NumberColumn<ProductInvoice, Integer> quantityColumn = new NumberColumn<ProductInvoice, Integer>("quantity", Integer.class);
        quantityColumn.addCellEditorListener(new CellEditorListener<ProductInvoice, Integer>() {
            @Override
            public void valueChanged(int rowIndex, String propertyName, ProductInvoice record, Integer value) {
                if (value == null) {
                    value = 0;
                }
                if (record != null)
                    record.setQuantity(value);
                refreshTableView();
            }
        });
        productInvoiceTableControl.addColumn(quantityColumn);
        quantityColumn.setText("Qté");
        quantityColumn.setAlignment(Pos.CENTER);

        TextColumn<ProductInvoice> UmColumn = new TextColumn<ProductInvoice>("UM");
        UmColumn.setText("U/M");
        productInvoiceTableControl.addColumn(UmColumn);
        UmColumn.setAlignment(Pos.CENTER);
        UmColumn.setRequired(true);

        NumberColumn<ProductInvoice, Double> priceColumn = new NumberColumn<ProductInvoice, Double>("unitPrice", Double.class);
        productInvoiceTableControl.addColumn(priceColumn);
        priceColumn.setText("Prix U (HT)");
        priceColumn.setAlignment(Pos.CENTER);
        priceColumn.setRequired(true);
        priceColumn.addCellEditorListener(new CellEditorListener<ProductInvoice, Double>() {
            @Override
            public void valueChanged(int rowIndex, String propertyName, ProductInvoice record, Double value) {
                if (value == null) {
                    value = 0.0;
                }
                if (record != null) record.setUnitPrice(value);
                refreshTableView();
            }
        });

        final NumberColumn<ProductInvoice, Double> mountantHT = new NumberColumn<ProductInvoice, Double>("HTAmount", Double.class);
        productInvoiceTableControl.addColumn(mountantHT);
        mountantHT.setNumberType(Double.class);
        mountantHT.setText("Montant (HT)");
        mountantHT.setAlignment(Pos.CENTER);
        mountantHT.setEditable(false);

        final NumberColumn<ProductInvoice, Double> mountantTVa = new NumberColumn<ProductInvoice, Double>("TVAAmount", Double.class);
        productInvoiceTableControl.addColumn(mountantTVa);
        mountantTVa.setNumberType(Double.class);
        mountantTVa.setText("Montant (TVA)");
        mountantTVa.setAlignment(Pos.CENTER);
        mountantTVa.setEditable(false);

        final NumberColumn<ProductInvoice, Double> mountantTTC = new NumberColumn<ProductInvoice, Double>("HTAmount", Double.class);
        productInvoiceTableControl.addColumn(mountantTTC);
        mountantTTC.setNumberType(Double.class);
        mountantTTC.setText("Montant (TTC)");
        mountantTTC.setAlignment(Pos.CENTER);
        mountantTTC.setEditable(false);

        CheckBoxColumn<ProductInvoice> withTVAColumn = new CheckBoxColumn<ProductInvoice>("withTVA");
        withTVAColumn.setText("TVA");
        withTVAColumn.setRequired(true);
        withTVAColumn.setFalseLabel("Non");
        withTVAColumn.setTrueLabel("Oui");
        withTVAColumn.setAlignment(Pos.CENTER);
        withTVAColumn.addCellEditorListener(new CellEditorListener<ProductInvoice, Boolean>() {
            @Override
            public void valueChanged(int rowIndex, String propertyName, ProductInvoice record, Boolean value) {
                record.setWithTVA(value);
                refreshTableView();
            }
        });
        designationColumn.addCellEditorListener(new CellEditorListener<ProductInvoice, String>() {
            @Override
            public void valueChanged(int rowIndex, String propertyName, ProductInvoice record, String value) {
                record.setDesignation(value);
                System.out.println("edting");
                ProductDetail productDetail = getProductDetailByName(value);
                if (productDetail != null)
                    if (productDetail.getName() != null) {
                        Product product = Product.fromProductDetail(productDetail);
                        if (record != null && product != null) {
                            if (product.getUM() != null || !product.getUM().isEmpty()) {
                                record.setUM(product.getUM());
                            }
                            record.setUnitPrice(product.getUnitPrice());
                        }
                    }
                refreshTableView();
            }
        });
        productInvoiceTableControl.addColumn(withTVAColumn);

        mountantTVa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductInvoice, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<ProductInvoice, Double> param) {
                ProductInvoice value = param.getValue();
                double tvaAmount = new ProductInvoiceCore(value.getQuantity(), value.getUnitPrice()).getTvaAmount(
                        (value.isWithTVA()) ? TvaPercent : 0);
                param.getValue().setTVAAmount(tvaAmount);
                return new SimpleObjectProperty<Double>(
                        tvaAmount);
            }
        });
        mountantHT.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductInvoice, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<ProductInvoice, Double> param) {
                ProductInvoice value = param.getValue();
                double htAmount = new ProductInvoiceCore(value.getQuantity(), value.getUnitPrice()).getHTAmount();
                param.getValue().setHTAmount(htAmount);
                return new SimpleObjectProperty<Double>(
                        htAmount);
            }
        });
        mountantTTC.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductInvoice, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<ProductInvoice, Double> param) {

                ProductInvoice value = param.getValue();

                double amountTTC = new ProductInvoiceCore(value.getQuantity(), value.getUnitPrice()).getAmountTTC((value.isWithTVA()) ? TvaPercent : 0);
                param.getValue().setTTCAmount(amountTTC);
                return new SimpleObjectProperty<Double>(
                        amountTTC
                );
            }
        });


        for (TableColumn<ProductInvoice, ?> leafColumn : productInvoiceTableControl.getLeafColumns()) {
            leafColumn.setPrefWidth(130);
            ((BaseColumn) leafColumn).setFilterable(false);
        }
        productInvoiceTableControl.setController(new ProductInvoiceTableController());
        //    productInvoiceTableControl.setErrorHandlerCallback(new ErrorHandler());
        productInvoiceTableControl.reload();
        productInvoiceTableControl.setVisibleComponents(false, TableControl.Component.FOOTER);
        productInvoiceTableControl.setReloadOnCriteriaChange(true);
        productInvoiceTableControl.setFillWidth(true);
        productInvoiceTableControl.setSelectionMode(SelectionMode.MULTIPLE);
        productInvoiceTableControl.setMaxRecord(12);
        //  productInvoiceTableControl.setVisibleComponents(false, TableControl.Component.TOOLBAR);
        numberColumn.setPrefWidth(SMALL_PREF_WIDTH);
        quantityColumn.setPrefWidth(SMALL_PREF_WIDTH);
        withTVAColumn.setPrefWidth(SMALL_PREF_WIDTH);
        UmColumn.setPrefWidth(SMALL_PREF_WIDTH);
        designationColumn.setPrefWidth(201);
        productInvoiceTableControl.getTableView().getStylesheets().add("css/theme.css");
        productInvoiceTableControl.prefWidth(productInvoiceTableControl.getTableView().getItems().size() * 60);
        productInvoiceTableControl.prefHeightProperty().bind(tableHeightProperty);

    }

    private class ProductInvoiceTableController extends TableController<ProductInvoice> {
        @Override
        public TableData<ProductInvoice> loadData
                (int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns,
                 List<TableColumn.SortType> sortingOrders, int maxResult) {
            tableHeightProperty.set(400);
            TableData<ProductInvoice> addressTableData =
                    new TableData<ProductInvoice>(productInvoiceTableControl.getRecords(), false,
                            productInvoiceTableControl.getRecords().size());
            return addressTableData;
        }

        @Override
        public List<ProductInvoice> insert(List<ProductInvoice> newRecords) {
            productInvoiceTableControl.getRecords().addAll(newRecords);
            return newRecords;
        }
    }

    ProductsPersistenceService persistenceService = SpringUtil.getBean(ProductsPersistenceService.class);
    private List<ProductDetail> allProducts = persistenceService.getAllProducts();

    public void updateRow(String newValue) {
        ProductDetail productDetail = getProductDetailByName(newValue);
        if (productDetail != null)
            if (productDetail.getName() != null) {
                Product product = Product.fromProductDetail(productDetail);
                ProductInvoice selectedItem = productInvoiceTableControl.getTableView().getSelectionModel().getSelectedItem();
                if (selectedItem != null && product != null) {
                    if (product.getUM() != null || !product.getUM().isEmpty()) {
                        selectedItem.setUM(product.getUM());
                    }

                    selectedItem.setUnitPrice(product.getUnitPrice());
                }
                refreshTableView();
            }

    }

    private ProductDetail getProductDetailByName(String newValue) {
        for (ProductDetail product : allProducts) {
            if (product.getName().equals(newValue)) return product;
        }
        return null;
    }

    public class ComboBoxCustomCell extends BaseCell<ProductInvoice, String> {

        private ComboBox<String> comboBox = new ComboBox<String>();

        public ComboBoxCustomCell(BaseColumn<ProductInvoice, String> column) {
            super(column);
            addChoiceListTo(comboBox);
        }


        private void addChoiceListTo(ComboBox comboBox) {
            List<String> allProductsNames = new ArrayList<String>();
            for (ProductDetail product : allProducts) {
                allProductsNames.add(product.getName());
            }
            comboBox.setItems(FXCollections.observableList(allProductsNames));
        }

        @Override
        protected void setValueToEditor(String value) {
            comboBox.getEditor().setText(value);
        }

        @Override
        protected String getValueFromEditor() {
            return comboBox.getEditor().getText();

        }

        @Override
        protected Control getEditor() {
            comboBox.setEditable(true);
            new TypeAheadField<ProductInvoice>().getText();
            return comboBox;
        }

    }

    private void refreshTableView() {
        productInvoiceTableControl.refresh();
        setTableFooterValues();
    }

    private void setTableFooterValues() {

        double ttc = 0;
        double tva = 0;
        double th = 0;
        for (ProductInvoice productInvoice : productInvoiceTableControl.getTableView().getItems()) {

            ttc += productInvoice.getTTCAmount();
            tva += productInvoice.getTVAAmount();
            th += productInvoice.getHTAmount();
        }
        montantTTCSum.set(String.valueOf(ttc));
        montantTHSum.set(String.valueOf(th));
        montantTVASum.set(String.valueOf(tva));
        letterSum.set(new FrenchNumberToWords().convert((long) ttc));
    }
}
