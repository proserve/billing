package com.andima.billing.client.views;

import com.andima.billing.client.App;
import com.andima.billing.client.domain.Product;
import com.andima.billing.client.util.SpringUtil;
import com.andima.billing.core.request.product.ProductDetail;
import com.andima.billing.core.service.ProductsPersistenceService;
import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.dialog.MessageDialog;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.table.*;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public class ProductViews extends AnchorPane{
    private TableControl<Product> productTableControl = new TableControl<Product>(Product.class);
    private ProductsPersistenceService persistenceService = SpringUtil.getBean(ProductsPersistenceService.class);
    public ProductViews() {
        this.getStylesheets().add("css/win7glass.css");
        this.setWidth(800);
        this.setHeight(600);
        createProductTable();
        this.getChildren().add(productTableControl);
        AnchorPane.setTopAnchor(productTableControl, 0.0);
        AnchorPane.setLeftAnchor(productTableControl, 0.0);
        AnchorPane.setBottomAnchor(productTableControl, 0.0);
        AnchorPane.setRightAnchor(productTableControl, 0.0);

    }

    private void createProductTable() {
        NumberColumn<Product, Integer> numberColumn = new NumberColumn<Product, Integer>("number",Integer.class);
        productTableControl.addColumn(numberColumn);
        numberColumn.setText("Numéro");
        numberColumn.setEditable(false);
        numberColumn.setAlignment(Pos.CENTER);

        TextColumn<Product> nameColumn = new TextColumn<Product>("name");
        nameColumn.setText("Nom du Produit");
        productTableControl.addColumn(nameColumn);
        nameColumn.setAlignment(Pos.CENTER);
        nameColumn.setRequired(true);
        nameColumn.setNullLabel("Le nom de produit ne peut pas être vide");

        TextColumn<Product> UmColumn = new TextColumn<Product>("UM");
        UmColumn.setText("Unité metrique");
        productTableControl.addColumn(UmColumn);
        UmColumn.setAlignment(Pos.CENTER);
        UmColumn.setRequired(true);

        NumberColumn<Product, Double> priceColumn = new NumberColumn<Product, Double>("unitPrice", Double.class);
        productTableControl.addColumn(priceColumn);
        priceColumn.setText("Prix d'unité");
        priceColumn.setAlignment(Pos.CENTER);
        priceColumn.setRequired(true);

        productTableControl.setController(new ProductTableController());
        productTableControl.setErrorHandlerCallback(new ErrorHandler());
        productTableControl.setAgileEditing(false);
        productTableControl.reload();
        productTableControl.setVisibleComponents(false, TableControl.Component.FOOTER);
        productTableControl.setReloadOnCriteriaChange(true);
        productTableControl.setFillWidth(true);
        productTableControl.setSelectionMode(SelectionMode.MULTIPLE);
        for (TableColumn<Product, ?> leafColumn : productTableControl.getLeafColumns()) {
            leafColumn.setPrefWidth(200);
            ((BaseColumn) leafColumn).setFilterable(false);
        }
        productTableControl.getStylesheets().add("/css/theme.css");
    }

    private class ProductTableController extends TableController<Product> {
        @Override
        public TableData<Product> loadData(int startIndex, List<TableCriteria> filteredColumns, List<String>
                sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
            List<ProductDetail> allProductDetails = persistenceService.getAllProducts();
            List<Product> allProducts = new ArrayList<Product>();
            for (ProductDetail productDetail : allProductDetails) {
                allProducts.add(Product.fromProductDetail(productDetail));
            }
            TableData<Product> productTableData = new TableData<Product>(allProducts, false, allProducts.size());
            return productTableData;
        }

        @Override
        public List<Product> insert(List<Product> newRecords) {
            List<Product> resultProducts = new ArrayList<Product>();
            for (Product newRecord : newRecords) {
                ProductDetail   product = persistenceService.createProduct(newRecord.toProductDetail());

                resultProducts.add(Product.fromProductDetail(product));
            }
            return resultProducts;
        }

        @Override
        public List<Product> update(List<Product> records) {
            List<Product> productList = new ArrayList<Product>();
            for (Product record : records) {
                ProductDetail detail = persistenceService.update(record.toProductDetail());
                productList.add(Product.fromProductDetail(detail));
            }
            return productList;
        }

        @Override
        public void delete(List<Product> records) {
            for (Product record : records) {
                persistenceService.delete(record.getNumber());
            }
        }

        @Override
        public void exportToExcel(String title, int maxResult, TableControl<Product> tblView, List<TableCriteria> lstCriteria) {
            super.exportToExcel("List des produits", maxResult, tblView, lstCriteria);
        }

        @Override
        public boolean canDelete(TableControl<Product> table) {
            MessageDialog.Answer answer = MessageDialogBuilder.confirmation()
                    .message(TiwulFXUtil.getLiteral("Vous voullez vraiment supprimer ce produit"))
                    .title(TiwulFXUtil.getLiteral("Confirmation"))
                    .defaultAnswer(MessageDialog.Answer.NO)
                    .yesOkButtonText("Oui")
                    .noButtonText("Non")
                    .show(table.getScene().getWindow());
            return answer.equals(MessageDialog.Answer.YES_OK);
        }
    }

    private class ErrorHandler implements Callback<Throwable, Void> {
        @Override
        public Void call(Throwable param) {
            Throwable cause = param.getCause().getCause();
            String causeValidation ="";
            if(cause instanceof ConstraintViolationException) {
                ConstraintViolationException cause1 = (ConstraintViolationException) cause;
                Set<ConstraintViolation<?>> constraintViolations = cause1.getConstraintViolations();
                for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                    causeValidation += constraintViolation.getMessage();
                }
                MessageDialogBuilder.error(null).message(causeValidation).show(App.stage);
            }
            else {
                MessageDialogBuilder.error(param).message(cause.getMessage()).show(App.stage);
            }
            return null;
        }
    }
}
