package com.andima.billing.client.views;

import com.andima.billing.client.App;
import com.andima.billing.client.domain.Address;
import com.andima.billing.client.domain.Client;
import com.andima.billing.client.util.SpringUtil;
import com.andima.billing.core.request.address.AddressDetail;
import com.andima.billing.core.request.client.ClientDetail;
import com.andima.billing.core.service.AddressesPersistenceService;
import com.andima.billing.core.service.ClientsPersistenceService;
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
public class ClientViews extends AnchorPane{
    private TableControl<Client> clientTableControl = new TableControl<Client>(Client.class);
    private ClientsPersistenceService persistenceService = SpringUtil.getBean(ClientsPersistenceService.class);
    private AddressesPersistenceService addressesPersistenceService = SpringUtil.getBean(AddressesPersistenceService.class);

    public ClientViews() {
        this.getStylesheets().add("css/win7glass.css");
        this.setWidth(800);
        this.setHeight(600);
        createClientTable();
        this.getChildren().add(clientTableControl);
        AnchorPane.setTopAnchor(clientTableControl, 0.0);
        AnchorPane.setLeftAnchor(clientTableControl, 0.0);
        AnchorPane.setBottomAnchor(clientTableControl,0.0);
        AnchorPane.setRightAnchor(clientTableControl, 0.0);
    }

    private void createClientTable() {


        TextColumn<Client> firstNameColumn = new TextColumn<Client>("firstName");
        firstNameColumn.setText("Prénom");
        clientTableControl.addColumn(firstNameColumn);
        firstNameColumn.setAlignment(Pos.CENTER);
        firstNameColumn.setRequired(true);
        firstNameColumn.setNullLabel("Le prénom de client ne peut pas être vide");

        TextColumn<Client> lastNameColumn = new TextColumn<Client>("lastName");
        lastNameColumn.setText("Nom");
        clientTableControl.addColumn(lastNameColumn);
        lastNameColumn.setAlignment(Pos.CENTER);
        lastNameColumn.setRequired(true);
        lastNameColumn.setNullLabel("Le nom de client ne peut pas être vide");

        TypeAheadColumn<Client, Address> addressColumn = new TypeAheadColumn<Client, Address>("address");
        List<AddressDetail> allAddresses = addressesPersistenceService.getAllAddresses();
        for (AddressDetail detail : allAddresses) {
            Address address = Address.fromAddressDetail(detail);
            addressColumn.addItem(address.toString(), address);
        }

        addressColumn.setText("Adresse");
        clientTableControl.addColumn(addressColumn);
        addressColumn.setAlignment(Pos.CENTER);
        addressColumn.setRequired(true);

        TextColumn<Client> NCFColumn = new TextColumn<Client>("NCF");
        clientTableControl.addColumn(NCFColumn);
        NCFColumn.setText("N°FC");
        NCFColumn.setAlignment(Pos.CENTER);
        NCFColumn.setRequired(true);
        clientTableControl.setController(new ClientTableController());
        clientTableControl.setErrorHandlerCallback(new ErrorHandler());
        clientTableControl.setAgileEditing(true);
        clientTableControl.reload();
        clientTableControl.setVisibleComponents(false, TableControl.Component.FOOTER);
        clientTableControl.setReloadOnCriteriaChange(true);
        clientTableControl.setFillWidth(true);
        clientTableControl.setSelectionMode(SelectionMode.MULTIPLE);
        for (TableColumn<Client, ?> leafColumn : clientTableControl.getLeafColumns()) {
            leafColumn.setPrefWidth(200);
            ((BaseColumn) leafColumn).setFilterable(false);
        }
        clientTableControl.getTableView().getStylesheets().add("css/theme.css");


    }

    private class ClientTableController extends TableController<Client> {
        @Override
        public TableData<Client> loadData(int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {

            List<ClientDetail> allClientDetails = persistenceService.getAllClients();
            List<Client> allClients = new ArrayList<Client>();
            for (ClientDetail clientDetail : allClientDetails) {
                allClients.add(Client.fromClientDetail(clientDetail));
            }
            TableData<Client> clientTableData = new TableData<Client>(allClients, false, allClients.size());
            return clientTableData;
        }

        @Override
        public List<Client> insert(List<Client> newRecords) {
            List<Client> resultClients = new ArrayList<Client>();
            for (Client newRecord : newRecords) {
                ClientDetail   client = persistenceService.createClient(newRecord.toClientDetail());

                resultClients.add(Client.fromClientDetail(client));
            }
            return resultClients;
        }

        @Override
        public List<Client> update(List<Client> records) {
            List<Client> clientList = new ArrayList<Client>();
            for (Client record : records) {
                ClientDetail detail = persistenceService.update(record.toClientDetail());
                clientList.add(Client.fromClientDetail(detail));
            }
            return clientList;
        }

        @Override
        public void delete(List<Client> records) {
            for (Client record : records) {
                persistenceService.delete(record.getId());
            }
        }

        @Override
        public void exportToExcel(String title, int maxResult, TableControl<Client> tblView, List<TableCriteria> lstCriteria) {
            super.exportToExcel("List des clients", maxResult, tblView, lstCriteria);
        }

        @Override
        public boolean canDelete(TableControl<Client> table) {
            MessageDialog.Answer answer = MessageDialogBuilder.confirmation()
                    .message(TiwulFXUtil.getLiteral("Vous voullez vraiment supprimer ce client"))
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
            try {
                Throwable cause = param.getCause().getCause();
                String causeValidation = "";
                if (cause instanceof ConstraintViolationException) {
                    ConstraintViolationException cause1 = (ConstraintViolationException) cause;
                    Set<ConstraintViolation<?>> constraintViolations = cause1.getConstraintViolations();
                    for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                        causeValidation += constraintViolation.getMessage();
                    }
                    MessageDialogBuilder.error(null).message(causeValidation).show(App.stage);
                } else {
                    MessageDialogBuilder.error(param).message(cause.getMessage()).show(App.stage);
                }
            }catch (NullPointerException e){
                MessageDialogBuilder.error(param).message(param.getMessage()).show(App.stage);
            }
            return null;
        }
    }


}
