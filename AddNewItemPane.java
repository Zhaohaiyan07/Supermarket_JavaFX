package com.client.view;

import com.client.model.ItemModel;
import com.google.gson.Gson;
import com.srrp.SRRPClient;
import com.srrp.SRRPRequest;
import com.srrp.SRRPResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AddNewItemPane {
    private int margin = 20;
    private int labelWidth = 100;
    private int btnHeight = 30;
    private int width = 500;

    public Pane createPane(){
        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(300);

        ItemModel newItem = new ItemModel();

        Label nameLabel = new Label("名称：");
        TextField nameTextField = new TextField();
        Label categoryLabel = new Label("类别：");
        TextField categoryTextField = new TextField();
        Label priceLabel = new Label("价格：");
        TextField priceTextField = new TextField();
        Label quantityLabel = new Label("数量：");
        TextField quantityTextField = new TextField();
        Button btnSubmit = new Button("入库");

        nameLabel.setLayoutX(margin);
        nameLabel.setLayoutY(margin);
        nameLabel.setPrefWidth(labelWidth);
        nameLabel.setPrefHeight(btnHeight);

        nameTextField.setLayoutX(margin * 2 + nameLabel.getPrefWidth());
        nameTextField.setLayoutY(margin);
        nameTextField.setPrefWidth(labelWidth);
        nameTextField.setPrefHeight(btnHeight);

        categoryLabel.setLayoutX(margin);
        categoryLabel.setLayoutY(margin * 2 + nameLabel.getPrefHeight());
        categoryLabel.setPrefWidth(labelWidth);
        categoryLabel.setPrefHeight(btnHeight);

        categoryTextField.setLayoutX(margin * 2 + categoryLabel.getPrefWidth());
        categoryTextField.setLayoutY(margin * 2 + nameLabel.getPrefHeight());
        categoryTextField.setPrefWidth(labelWidth);
        categoryTextField.setPrefHeight(btnHeight);

        priceLabel.setLayoutX(margin);
        priceLabel.setLayoutY(margin * 3 + nameLabel.getPrefHeight() + categoryLabel.getPrefHeight());
        priceLabel.setPrefWidth(labelWidth);
        priceLabel.setPrefHeight(btnHeight);

        priceTextField.setLayoutX(margin * 2 + priceLabel.getPrefWidth());
        priceTextField.setLayoutY(margin * 3 + nameLabel.getPrefHeight() + categoryLabel.getPrefHeight());
        priceTextField.setPrefWidth(labelWidth);
        priceTextField.setPrefHeight(btnHeight);

        quantityLabel.setLayoutX(margin);
        quantityLabel.setLayoutY(margin * 4 + nameLabel.getPrefHeight() + categoryLabel.getPrefHeight() + priceLabel.getPrefHeight());
        quantityLabel.setPrefWidth(labelWidth);
        quantityLabel.setPrefHeight(btnHeight);

        quantityTextField.setLayoutX(margin * 2 + quantityLabel.getPrefWidth());
        quantityTextField.setLayoutY(margin * 4 + nameLabel.getPrefHeight() + categoryLabel.getPrefHeight() + priceLabel.getPrefHeight());
        quantityTextField.setPrefWidth(labelWidth);
        quantityTextField.setPrefHeight(btnHeight);

        btnSubmit.setLayoutX(margin);
        btnSubmit.setLayoutY(margin * 5 + nameLabel.getPrefHeight() + categoryLabel.getPrefHeight() + priceLabel.getPrefHeight() + quantityLabel.getPrefHeight());
        btnSubmit.setPrefWidth(labelWidth);
        btnSubmit.setPrefHeight(btnHeight);

        pane.getChildren().add(nameLabel);
        pane.getChildren().add(nameTextField);
        pane.getChildren().add(categoryLabel);
        pane.getChildren().add(categoryTextField);
        pane.getChildren().add(priceLabel);
        pane.getChildren().add(priceTextField);
        pane.getChildren().add(quantityLabel);
        pane.getChildren().add(quantityTextField);
        pane.getChildren().add(btnSubmit);

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newItem.setName(nameTextField.getText());
                newItem.setCategory(categoryTextField.getText());
                newItem.setPrice(Double.parseDouble(priceTextField.getText()));
                newItem.setQuantity(Integer.parseInt(quantityTextField.getText()));

                String newItemJson = new Gson().toJson(newItem);

                new Thread(){
                    @Override
                    public void run(){
                        SRRPRequest request = new SRRPRequest();
                        request.setAction("stockin");
                        request.setData(newItemJson);
                        SRRPResponse response = new SRRPClient().send(request);
                        if(response.isOk()){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setHeaderText(null);
                                    alert.setContentText(response.getData());
                                    alert.showAndWait();
                                }
                            });
                        }else{
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText(null);
                                    alert.setContentText(response.getData());
                                    alert.showAndWait();
                                }
                            });
                        }

                    }
                }.start();
            }
        });

        return pane;
    }
}
