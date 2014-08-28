package com.andima.billing.client.views;

import com.andima.billing.client.App;
import com.andima.billing.client.domain.Address;
import com.andima.billing.client.util.SpringUtil;
import com.andima.billing.core.request.address.AddressDetail;
import com.andima.billing.core.service.AddressesPersistenceService;
import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.table.BaseColumn;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableController;
import com.panemu.tiwulfx.table.TextColumn;
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
public class AddressViews extends AnchorPane{
    private TableControl<Address> addressTableControl = new TableControl<Address>(Address.class);
    private AddressesPersistenceService persistenceService = SpringUtil.getBean(AddressesPersistenceService.class);
    public AddressViews() {
        this.getStylesheets().add("css/win7glass.css");
        this.setWidth(800);
        this.setHeight(600);
        createAddressTable();
        this.getChildren().add(addressTableControl);
        AnchorPane.setTopAnchor(addressTableControl, 0.0);
        AnchorPane.setLeftAnchor(addressTableControl, 0.0);
        AnchorPane.setBottomAnchor(addressTableControl, 0.0);
        AnchorPane.setRightAnchor(addressTableControl, 0.0);
    }

    private void createAddressTable() {

        TextColumn<Address> streetAddress = new TextColumn<Address>("streetAddress");
        streetAddress.setText("L'adresse");
        addressTableControl.addColumn(streetAddress);
        streetAddress.setAlignment(Pos.CENTER);
        streetAddress.setRequired(true);

        TextColumn<Address> UmColumn = new TextColumn<Address>("city");
        UmColumn.setText("La commune");
        addressTableControl.addColumn(UmColumn);
        UmColumn.setAlignment(Pos.CENTER);
        UmColumn.setRequired(true);

        TextColumn<Address> priceColumn = new TextColumn<Address>("state");
        addressTableControl.addColumn(priceColumn);
        priceColumn.setText("Wilaya");
        priceColumn.setAlignment(Pos.CENTER);
        priceColumn.setRequired(true);

        addressTableControl.setController(new AddressTableController());
        addressTableControl.setErrorHandlerCallback(new ErrorHandler());
        addressTableControl.setAgileEditing(true);
        addressTableControl.reload();
        addressTableControl.setVisibleComponents(false, TableControl.Component.FOOTER);
        addressTableControl.setReloadOnCriteriaChange(true);
        addressTableControl.setFillWidth(true);
        addressTableControl.setSelectionMode(SelectionMode.MULTIPLE);
        for (TableColumn<Address, ?> leafColumn : addressTableControl.getLeafColumns()) {
            leafColumn.setPrefWidth(200);
            ((BaseColumn) leafColumn).setFilterable(false);

        }
        addressTableControl.getTableView().getStylesheets().add("css/theme.css");

    }

    private class AddressTableController extends TableController<Address> {
        @Override
        public TableData<Address> loadData
                (int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns,
                 List<TableColumn.SortType> sortingOrders, int maxResult) {
            for (TableCriteria filteredColumn : filteredColumns) {
                System.out.println("Operator ==> " + filteredColumn.getAttributeName());
            }
            List<AddressDetail> allAddressDetails = persistenceService.getAllAddresses();
            List<Address> allAddresses = new ArrayList<Address>();
            for (AddressDetail addressDetail : allAddressDetails) {
                allAddresses.add(Address.fromAddressDetail(addressDetail));
            }

            TableData<Address> addressTableData = new TableData<Address>(allAddresses, false, allAddresses.size());
            return addressTableData;
        }

        @Override
        public List<Address> insert(List<Address> newRecords) {
            List<Address> resultAddresses = new ArrayList<Address>();
            for (Address newRecord : newRecords) {
                System.out.println(newRecord.getStreetAddress());
                AddressDetail   address = persistenceService.createAddress(newRecord.toAddressDetail());
                resultAddresses.add(Address.fromAddressDetail(address));
            }
            return resultAddresses;
        }

        @Override
        public List<Address> update(List<Address> records) {
            List<Address> addressList = new ArrayList<Address>();
            for (Address record : records) {
                AddressDetail detail = persistenceService.update(record.toAddressDetail());
                addressList.add(Address.fromAddressDetail(detail));
            }
            return addressList;
        }

        @Override
        public void delete(List<Address> records) {
            for (Address record : records) {
                persistenceService.delete(record.getId());
            }
        }

        @Override
        public void exportToExcel(String title, int maxResult, TableControl<Address> tblView, List<TableCriteria> lstCriteria) {
            super.exportToExcel("List des adresses", maxResult, tblView, lstCriteria);
        }

       /* @Override
        public boolean canDelete(TableControl<Address> table) {
            MessageDialog.Answer answer = MessageDialogBuilder.confirmation()
                    .message(TiwulFXUtil.getLiteral("Vous voullez vraiment supprimer cet adresse"))
                    .title(TiwulFXUtil.getLiteral("Confirmation"))
                    .defaultAnswer(MessageDialog.Answer.NO)
                    .yesOkButtonText("Oui")
                    .noButtonText("Non")
                    .show(table.getScene().getWindow());
            return answer.equals(MessageDialog.Answer.YES_OK);
        }*/

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
