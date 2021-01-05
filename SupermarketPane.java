package com.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class SupermarketPane {
    private int margin = 20;
    private int btnHeight = 30;
    private int btnWidth = 50;

    public Pane createPane(){
        Pane pane = new Pane();
        Button btnRepository = new Button("仓库");
        Button btnRetail = new Button("销售");
        Button btnExit = new Button("退出");

        btnRepository.setLayoutX(margin);
        btnRepository.setLayoutY(margin);
        btnRepository.setPrefWidth(btnWidth);
        btnRepository.setPrefHeight(btnHeight);

        btnRetail.setLayoutX(margin);
        btnRetail.setLayoutY(margin * 2 + btnRepository.getPrefHeight());
        btnRetail.setPrefWidth(btnWidth);
        btnRetail.setPrefHeight(btnHeight);

        btnExit.setLayoutX(margin);
        btnExit.setLayoutY(margin * 3 + btnRepository.getPrefHeight() + btnRetail.getPrefHeight());
        btnExit.setPrefWidth(btnWidth);
        btnExit.setPrefHeight(btnHeight);

        pane.getChildren().add(btnRepository);
        pane.getChildren().add(btnRetail);
        pane.getChildren().add(btnExit);

        btnRepository.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane repositoryPane = new RepositoryPane().createPane();
                pane.getChildren().clear();
                pane.getChildren().add(repositoryPane);
            }
        });

        btnRetail.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane retailPane = new RetailPane().createPane();
                pane.getChildren().clear();
                pane.getChildren().add(retailPane);
            }
        });

        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        return pane;
    }
}
