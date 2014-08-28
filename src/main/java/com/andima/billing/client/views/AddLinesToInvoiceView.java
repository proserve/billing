package com.andima.billing.client.views;

import com.andima.billing.client.domain.ProductInvoice;
import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.table.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 23/08/2014.
 */
public class AddLinesToInvoiceView  extends AnchorPane{
    private SimpleObjectProperty<String> montantTTCSum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> montantTVASum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> montantTHSum = new SimpleObjectProperty<String>("");
    private SimpleObjectProperty<String> letterSum = new SimpleObjectProperty<String>("");
    private TableControl<ProductInvoice> tableControl = new TableControl<ProductInvoice>(ProductInvoice.class);
    public AddLinesToInvoiceView(){
        this.getChildren().add(tableControl);
        tableControl.setFillWidth(true);
        final TextColumn<ProductInvoice> designationColumn = new TextColumn<ProductInvoice>("designation");
        tableControl.addColumn(designationColumn);

        NumberColumn<ProductInvoice, Integer> quantityColumn = new NumberColumn<ProductInvoice, Integer>("quantity", Integer.class);
        tableControl.addColumn(quantityColumn);
        quantityColumn.setText("Qté");

        TextColumn<ProductInvoice> UmColumn = new TextColumn<ProductInvoice>("UM");
        UmColumn.setText("U/M");
        tableControl.addColumn(UmColumn);

        NumberColumn<ProductInvoice, Double> priceColumn = new NumberColumn<ProductInvoice, Double>("unitPrice", Double.class);
        tableControl.addColumn(priceColumn);
        priceColumn.setText("Prix U (HT)");
        priceColumn.setRequired(true);

        final NumberColumn<ProductInvoice, Double> mountantHT = new NumberColumn<ProductInvoice, Double>("HTAmount", Double.class);
        tableControl.addColumn(mountantHT);
        mountantHT.setNumberType(Double.class);
        mountantHT.setText("Montant (HT)");
        mountantHT.setEditable(false);

        final NumberColumn<ProductInvoice, Double> mountantTVa = new NumberColumn<ProductInvoice, Double>("TVAAmount", Double.class);
        tableControl.addColumn(mountantTVa);
        mountantTVa.setNumberType(Double.class);
        mountantTVa.setText("Montant (TVA)");
        mountantTVa.setEditable(false);

        final NumberColumn<ProductInvoice, Double> mountantTTC = new NumberColumn<ProductInvoice, Double>("HTAmount", Double.class);
        tableControl.addColumn(mountantTTC);
        mountantTTC.setNumberType(Double.class);
        mountantTTC.setText("Montant (TTC)");
        mountantTTC.setAlignment(Pos.CENTER);
        mountantTTC.setEditable(false);

        CheckBoxColumn<ProductInvoice> withTVAColumn = new CheckBoxColumn<ProductInvoice>("withTVA");
        withTVAColumn.setText("TVA");
        withTVAColumn.setRequired(true);
        withTVAColumn.setFalseLabel("Non");
        withTVAColumn.setTrueLabel("Oui");;
        tableControl.addColumn(withTVAColumn);
        for (TableColumn<ProductInvoice, ?> productInvoiceTableColumn : tableControl.getLeafColumns()) {
            ((BaseColumn) productInvoiceTableColumn).setAlignment(Pos.CENTER);
        }

        tableControl.setController(new ProductInvoiceTableController());
        tableControl.setMaxRecord(2);
    }

    private class ProductInvoiceTableController extends TableController<ProductInvoice> {
        @Override
        public TableData<ProductInvoice> loadData(int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
            return new TableData<ProductInvoice>(tableControl.getRecords(), false, tableControl.getRecords().size());
        }

        @Override
        public List<ProductInvoice> insert(List<ProductInvoice> newRecords) {
            return newRecords;
        }

        @Override
        public List<ProductInvoice> update(List<ProductInvoice> records) {
            return records;
        }
    }
}
