package com.client;

import com.client.view.SupermarketPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    private int width = 500;
    private int height = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = new Pane();

        Pane supermarketPane = new SupermarketPane().createPane();
        pane.getChildren().add(supermarketPane);

        Scene scene = new Scene(pane,width,height);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Supermarket");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
