package com.andima.billing.client.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.spreadsheet.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by GHIBOUB Khalid  on 26/08/2014.
 */
public class InvoiceProductSpreadSheetView extends AnchorPane{

    public static final int ROW_COUNT = 12;
    public static final int COLUMN_COUNT = 4;
    GridBase grid = new GridBase(ROW_COUNT, COLUMN_COUNT);
    SpreadsheetView spreadsheetView;
    ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
    public InvoiceProductSpreadSheetView(){


        List<String> headersList = Arrays.asList("Désignation", "Qté", "U/M", "Prix U");
        grid.getColumnHeaders().addAll(headersList);

        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        final SpreadsheetCellEditor editor = new MySpreadsheetCellEditor();
        SpreadsheetCellType<String> cellEditor = new StringSpreadsheetCellType(editor);
        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            for (int column = 0; column < grid.getColumnCount(); ++column) {
                SpreadsheetCellType.ListType list1 = SpreadsheetCellType.LIST(Arrays.asList("test", "kidek"));

/*
                SpreadsheetCell cell = list1.createCell(row, column, 1, 1, "kidxe");
*/
                SpreadsheetCell cell = new SpreadsheetCellBase(row, column, 1, 1, cellEditor);

                list.add(cell);
            }
            rows.add(list);

        }
        grid.setRows(rows);
        spreadsheetView = new SpreadsheetView(grid);
        spreadsheetView.setShowRowHeader(false);

        this.getChildren().add(spreadsheetView);
        setTopAnchor(spreadsheetView, 0.0);
        setBottomAnchor(spreadsheetView, 0.0);
        setLeftAnchor(spreadsheetView, 0.0);
        setRightAnchor(spreadsheetView, 0.0);
    }

    private  class StringSpreadsheetCellType extends SpreadsheetCellType<String> {

        private final SpreadsheetCellEditor editor;

        public StringSpreadsheetCellType(SpreadsheetCellEditor editor) {
            this.editor = editor;

        }

        @Override
        public SpreadsheetCellEditor createEditor(SpreadsheetView view) {
            return editor;
        }

        @Override
        public String toString(String object) {
            return object;
        }

        @Override
        public boolean match(Object value) {
            return true;
        }

        @Override
        public String convertValue(Object value) {
            return value.toString();
        }
    }

    private class MySpreadsheetCellEditor extends SpreadsheetCellEditor {

        private ComboBox checkBox = new ComboBox();

        public MySpreadsheetCellEditor() {
            super(InvoiceProductSpreadSheetView.this.spreadsheetView);
            checkBox.getItems().addAll(FXCollections.observableList(Arrays.asList("khalid", "ghiboub")));
        }

        @Override
        public void startEdit(Object item) {

        }

        @Override
        public Control getEditor() {
            checkBox.setEditable(true);
            return checkBox;
        }

        @Override
        public String getControlValue() {
            return checkBox.getEditor().getText();
        }

        @Override
        public void end() {

        }
    }
}
