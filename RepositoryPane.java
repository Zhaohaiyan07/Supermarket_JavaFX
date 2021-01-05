package com.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class RepositoryPane {
    private int margin = 20;
    private int btnHeight = 30;
    private int btnWidth = 50;

    public Pane createPane(){
        Pane pane = new Pane();
        Button btnSearch = new Button("搜索");
        Button btnStockIn = new Button("入库");
        Button btnStockOut = new Button("出库");
        Button btnBack = new Button("返回");

        btnSearch.setLayoutX(margin);
        btnSearch.setLayoutY(margin);
        btnSearch.setPrefWidth(btnWidth);
        btnSearch.setPrefHeight(btnHeight);

        btnStockIn.setLayoutX(margin);
        btnStockIn.setLayoutY(margin * 2 + btnSearch.getPrefHeight());
        btnStockIn.setPrefWidth(btnWidth);
        btnStockIn.setPrefHeight(btnHeight);

        btnStockOut.setLayoutX(margin);
        btnStockOut.setLayoutY(margin * 3 + btnSearch.getPrefHeight() + btnStockIn.getPrefHeight());
        btnStockOut.setPrefWidth(btnWidth);
        btnStockOut.setPrefHeight(btnHeight);

        btnBack.setLayoutX(margin);
        btnBack.setLayoutY(margin * 4 + btnSearch.getPrefHeight() + btnStockIn.getPrefHeight() + btnStockOut.getPrefHeight());
        btnBack.setPrefWidth(btnWidth);
        btnBack.setPrefHeight(btnHeight);

        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SearchPane search = new SearchPane();
                Pane searchPane = search.createPane();
                pane.getChildren().clear();
                pane.getChildren().add(searchPane);
            }
        });

        btnStockIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane stockInPane = new StockInPane().createPane();
                pane.getChildren().clear();
                pane.getChildren().add(stockInPane);
            }
        });

        btnStockOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane stockOutPane = new StockOutPane().createPane();
                pane.getChildren().clear();
                pane.getChildren().add(stockOutPane);
            }
        });

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane supermarketPane = new SupermarketPane().createPane();
                pane.getChildren().clear();
                pane.getChildren().add(supermarketPane);
            }
        });

        pane.getChildren().add(btnSearch);
        pane.getChildren().add(btnStockIn);
        pane.getChildren().add(btnStockOut);
        pane.getChildren().add(btnBack);

        return pane;
    }
}
