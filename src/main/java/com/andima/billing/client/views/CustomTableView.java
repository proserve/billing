package com.andima.billing.client.views;

import com.panemu.tiwulfx.table.BaseColumn;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableController;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

/**
 * Created by GHIBOUB Khalid  on 24/08/2014.
 */

public abstract class CustomTableView<T> extends AnchorPane {
    TableControl<T> tableControl = new TableControl<T>();

    public CustomTableView(){
        this.getStylesheets().add("css/win7glass.css");
        this.setWidth(800);
        this.setHeight(600);
        createColumns();
        setVisibleComponents();
        createTable();
        this.getChildren().add(tableControl);
        AnchorPane.setTopAnchor(tableControl, 0.0);
        AnchorPane.setLeftAnchor(tableControl, 0.0);
        AnchorPane.setBottomAnchor(tableControl, 0.0);
        AnchorPane.setRightAnchor(tableControl, 0.0);
    }

    public abstract  void createColumns();
    public abstract  void setVisibleComponents();

    private void createTable() {
        tableControl.setController(getTableController());
        tableControl.reload();
        tableControl.setMaxRecord(12);
        tableControl.setAgileEditing(true);
        tableControl.setFillWidth(true);
        tableControl.setSelectionMode(SelectionMode.MULTIPLE);
        tableControl.setAlignment(Pos.CENTER);
        for (TableColumn<T, ?> tTableColumn : tableControl.getLeafColumns()) {
            ((BaseColumn) tTableColumn).setAlignment(Pos.CENTER);
        }
        tableControl.getTableView().getStylesheets().add("css/theme.css");

    }
    public abstract TableController<T> getTableController();


}