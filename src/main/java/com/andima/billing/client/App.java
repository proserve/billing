 package com.andima.billing.client;

import com.andima.billing.client.util.SpringUtil;
import com.andima.billing.core.service.ProductsPersistenceService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by GHIBOUB Khalid  on 19/08/2014.
 */
public class App extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        SpringUtil.getBean(ProductsPersistenceService.class);
        AnchorPane anchorPane = FXMLLoader.load(
                getClass().getClassLoader().getResource("fxml/main.fxml"));
        Scene scene = new Scene(anchorPane);
        scene.getStylesheets().add("css/DarkTheme.css");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("images/billing-1.0-SNAPSHOT.png"));
        stage = primaryStage;
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
