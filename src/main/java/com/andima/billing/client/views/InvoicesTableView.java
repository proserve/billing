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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import java.util.ArrayList;
import java.util.Arrays;
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

        DateColumn<Invoice> invoiceDateColumn = new DateColumn<Invoice>("date");
        this.addColumn(invoiceDateColumn);
        invoiceDateColumn.setText("Date");
        invoiceDateColumn.setPrefWidth(120);

        TextColumn<Invoice> invoiceClientColumn = new TextColumn<>("fullName");
        invoiceClientColumn.setText("Client");
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
            TableData<Invoice> tableData = new TableData<>(invoices, false, invoices.size());
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
           /* Action response = Dialogs.create()
                    .title("Confirmation")
                    .message(
                            "Attention tous les commandes correspondentes " +
                                    "vont être supprimer").
                            style(DialogStyle.NATIVE).
                            owner(App.stage).
                            showConfirm();*/
            List<Dialogs.CommandLink> links = Arrays.asList(
                    new Dialogs.CommandLink("Add a network that is in the range of this computer",
                            "This shows you a list of networks that are currently available and lets you connect to one."),
                    new Dialogs.CommandLink("Manually create a network profile",
                            "This creates a new network profile or locates an existing one and saves it on your computer"),
                    new Dialogs.CommandLink("Create an ad hoc network",
                            "This creates a temporary network for sharing files or and Internet connection"));

            Action response = Dialogs.create()
                    .owner(App.stage)
                    .title("Manually connect to wireless network")
                    .masthead( "Manually connect to wireless network")
                    .message("How do you want to add a network?").
                            style(DialogStyle.NATIVE)
                    .showCommandLinks(links.get(1), links);
            if(response== Dialog.Actions.OK){
                return true;
            }
            return false;
        }
    }
}
