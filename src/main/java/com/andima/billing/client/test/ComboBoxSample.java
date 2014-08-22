package com.andima.billing.client.test;

/**
 * Created by GHIBOUB Khalid  on 20/08/2014.
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.stage.Stage;

/**
 * A sample that shows both an un-editable and an editable ComboBox.
 *
 * @see javafx.scene.control.ComboBox
 * @see javafx.scene.control.ComboBoxBuilder
 */
public class ComboBoxSample extends Application {

    private final ObservableList strings = FXCollections.observableArrayList(
            "Option 1", "Option 2", "Option 3",
            "Option 4", "Option 5", "Option 6",
            "Longer ComboBox item",
            "Option 7", "Option 8", "Option 9",
            "Option 10", "Option 12");

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));

        HBox hbox = HBoxBuilder.create().alignment(Pos.CENTER).spacing(15).build();

        //Editable combobox. Use the default item display length
        final ComboBox<String> editableComboBox = new ComboBox<String>();
        editableComboBox.setId("second-editable");
        editableComboBox.setPromptText("Edit or Choose...");
        editableComboBox.setItems(strings);
        editableComboBox.setEditable(true);
        Button ok = new Button("ok");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(editableComboBox.getValue());
            }
        });
        hbox.getChildren().add(ok);
        hbox.getChildren().add(editableComboBox);
        root.getChildren().add(hbox);
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}