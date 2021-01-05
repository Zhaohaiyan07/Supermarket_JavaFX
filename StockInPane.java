package com.client.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class StockInPane {
    private int margin = 20;
    private int labelWidth = 100;
    private int btnHeight = 30;
    private int btnWidth = 50;
    private int width = 500;
    private int paneHeight = 100;

    public Pane createPane(){
        Label label = new Label(" 是否是新商品？");
        ObservableList<String> options = FXCollections.observableArrayList("是","否");
        ComboBox<String> optionComboBox = new ComboBox<String>(options);
        Button btnSure = new Button("确定");
        Button btnBack = new Button("返回");

        label.setLayoutX(margin);
        label.setLayoutY(margin);
        label.setPrefHeight(btnHeight);
        label.setPrefWidth(labelWidth);

        optionComboBox.setLayoutX(margin * 2 + label.getPrefWidth());
        optionComboBox.setLayoutY(margin);
        optionComboBox.setPrefHeight(btnHeight);
        optionComboBox.setPrefWidth(60);

        btnSure.setLayoutX(margin * 3 + label.getPrefWidth() + optionComboBox.getPrefWidth());
        btnSure.setLayoutY(margin);
        btnSure.setPrefWidth(btnWidth);
        btnSure.setPrefHeight(btnHeight);

        btnBack.setLayoutX(margin * 4 + label.getPrefWidth() + optionComboBox.getPrefWidth() + btnSure.getPrefWidth());
        btnBack.setLayoutY(margin);
        btnBack.setPrefWidth(btnWidth);
        btnBack.setPrefHeight(btnHeight);

        Pane view = new Pane();
        view.setPrefWidth(width);
        view.setPrefHeight(paneHeight);
        view.getChildren().add(label);
        view.getChildren().add(optionComboBox);
        view.getChildren().add(btnSure);
        view.getChildren().add(btnBack);

        Pane itemPane = new Pane();
        itemPane.setPrefWidth(width);
        itemPane.setLayoutY(paneHeight);
        itemPane.setPrefHeight(300);

        Pane pane = new Pane();
        pane.getChildren().add(view);
        pane.getChildren().add(itemPane);


        btnSure.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(optionComboBox.getValue().equals("是")){
                    Pane addNewItemPane = new AddNewItemPane().createPane();
                    itemPane.getChildren().clear();
                    itemPane.getChildren().add(addNewItemPane);

                }else if(optionComboBox.getValue().equals("否")){
                    Pane addOldItemPane = new AddOldItemPane().createPane();
                    itemPane.getChildren().clear();
                    itemPane.getChildren().add(addOldItemPane);

                }else if(optionComboBox.getValue().equals(null)){
                    System.out.println("没有选呢");
                }
            }
        });

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane repositoryPane = new RepositoryPane().createPane();
                pane.getChildren().clear();
                pane.getChildren().add(repositoryPane);
            }
        });

        return pane;
    }
}
