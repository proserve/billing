package com.andima.billing.client.views;

import com.andima.billing.client.App;
import com.andima.billing.client.domain.Invoice;
import com.andima.billing.client.util.SpringUtil;
import com.andima.billing.core.request.invoice.InvoiceDetail;
import com.andima.billing.core.service.InvoicesPersistenceService;
import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.table.*;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 23/08/2014.
 */
public class InvoicesTableView extends TableControl<Invoice> {
    InvoicesPersistenceService persistenceService = SpringUtil.getBean(InvoicesPersistenceService.class);
    public InvoicesTableView() {
        this.setRecordClass(Invoice.class);
        this.setVisibleComponents(false, Component.TOOLBAR);
        this.setVisibleComponents(false, Component.FOOTER);


        NumberColumn<Invoice, Integer> numberColumn = new NumberColumn<Invoice, Integer>("number", Integer.class);
        numberColumn.setText("N° ");
        numberColumn.setPrefWidth(60);
        this.addColumn(numberColumn);

        LocalDateColumn<Invoice> invoiceDateColumn = new LocalDateColumn<Invoice>("date");
        this.addColumn(invoiceDateColumn);
        invoiceDateColumn.setText("Date");
        invoiceDateColumn.setPrefWidth(120);

        TextColumn<Invoice> invoiceClientColumn = new TextColumn<Invoice>("fullName");
        invoiceClientColumn.setText("Client");
        invoiceClientColumn.setCellFactory(new Callback<TableColumn<Invoice, String>, TableCell<Invoice, String>>() {
            @Override
            public TableCell<Invoice, String> call(TableColumn<Invoice, String> param) {
                TableCell<Invoice, String> cell = new TableCell<Invoice, String>();
                Text text = new Text();
                text.setStyle("-fx-text-fill: white; -fx-color:white");
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(cell.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                cell.setStyle("-fx-text-fill: white; -fx-color:white");
                return cell ;
            }
        });
        this.addColumn(invoiceClientColumn);
        invoiceClientColumn.setPrefWidth(250);

        NumberColumn<Invoice, Integer> TTCNumberColumn = new NumberColumn<Invoice, Integer>("ttc", Integer.class);
        TTCNumberColumn.setText("Montant TTC");
        this.addColumn(TTCNumberColumn);
        TTCNumberColumn.setPrefWidth(200);


        this.setFillWidth(true);
        this.setController(new InvoiceTableController());
        this.reload();
        this.setSelectionMode(SelectionMode.MULTIPLE);
        for (TableColumn<Invoice, ?> invoiceTableColumn : this.getLeafColumns()) {
            if(invoiceTableColumn instanceof BaseColumn)
            ((BaseColumn)invoiceTableColumn).setAlignment(Pos.CENTER);
            ((BaseColumn)invoiceTableColumn).setFilterable(false);
        }

    }

    private class InvoiceTableController extends TableController<Invoice> {
        @Override
        public TableData<Invoice> loadData(int startIndex, List<TableCriteria> filteredColumns, List<String>
                sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
            List<InvoiceDetail> allInvoices = persistenceService.getAllInvoices();
            List<Invoice> invoices = new ArrayList<Invoice>();
            for (InvoiceDetail invoiceDetail : allInvoices) {
                invoices.add(Invoice.fromInvoiceDetail(invoiceDetail));
            }
            TableData<Invoice> tableData = new TableData<Invoice>(invoices, false, invoices.size());
            return tableData;
        }

        @Override
        public void delete(List<Invoice> records) {
            for (Invoice record : records) {
                persistenceService.delete(record.getNumber());
            }
        }

        @Override
        public boolean canDelete(TableControl<Invoice> table) {
            Action response = Dialogs.create()
                    .title("Confirmation")
                    .message(
                            "Attention tous les commandes correspondentes " +
                                    "vont être supprimer").
                            style(DialogStyle.NATIVE).
                            owner(App.stage).
                            showConfirm();
            if(response == Dialog.Actions.YES){
                return true;
            }
            return false;
        }
    }
}
