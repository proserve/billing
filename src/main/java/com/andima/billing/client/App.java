 package com.andima.billing.client;

 import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

 /**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public class App extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(
                getClass().getClassLoader().getResource("fxml/main.fxml"));
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image("images/billing-1.0-SNAPSHOT.png"));
        stage = primaryStage;
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
