package com.client.view;

import com.client.model.OrderModel;
import com.client.model.ShoppingCartModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.srrp.SRRPClient;
import com.srrp.SRRPRequest;
import com.srrp.SRRPResponse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;

public class DeleteFromCartPane {

    private int margin = 20;
    private int labelWidth = 100;
    private int btnHeight = 30;
    private int width = 500;
    private ShoppingCartModel shoppingCart = new ShoppingCartModel();

    public Pane createPane(){
        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(300);

        ObservableList<String> inventoryList = FXCollections.observableArrayList();
        for (int i = 0; i < shoppingCart.getOrder().size(); i++) {
            String id = Integer.toString(shoppingCart.getOrder().get(i).getOrderId());
            String name = shoppingCart.getOrder().get(i).getName();
            inventoryList.add(id + "-" + name);
        }
        ComboBox<String> inventoryComboBox = new ComboBox<String>(inventoryList);

        Label inventoryLabel = new Label("选择移除的商品：");
        Label quantityLabel = new Label("请输入移除数量：");
        TextField quantityTextField = new TextField();
        Button btnSubmit = new Button("移除");
        Button btnBack = new Button("返回");


        inventoryLabel.setLayoutX(margin);
        inventoryLabel.setLayoutY(margin);
        inventoryLabel.setPrefWidth(labelWidth);
        inventoryLabel.setPrefHeight(btnHeight);

        inventoryComboBox.setLayoutX(margin * 2 + inventoryLabel.getPrefWidth());
        inventoryComboBox.setLayoutY(margin);
        inventoryComboBox.setPrefWidth(200);
        inventoryComboBox.setPrefHeight(btnHeight);

        quantityLabel.setLayoutX(margin);
        quantityLabel.setLayoutY(margin * 2 + inventoryLabel.getPrefHeight());
        quantityLabel.setPrefWidth(labelWidth);
        quantityLabel.setPrefHeight(btnHeight);

        quantityTextField.setLayoutX(margin * 2 + quantityLabel.getPrefWidth());
        quantityTextField.setLayoutY(margin * 2 + inventoryLabel.getPrefHeight());
        quantityTextField.setPrefWidth(labelWidth);
        quantityTextField.setPrefHeight(btnHeight);

        btnSubmit.setLayoutX(margin);
        btnSubmit.setLayoutY(margin * 3 + inventoryLabel.getPrefHeight() + quantityLabel.getPrefHeight());
        btnSubmit.setPrefWidth(labelWidth);
        btnSubmit.setPrefHeight(btnHeight);

        btnBack.setLayoutX(margin);
        btnBack.setLayoutY(margin * 4 + inventoryLabel.getPrefHeight() + quantityLabel.getPrefHeight() + btnSubmit.getPrefHeight());
        btnBack.setPrefWidth(labelWidth);
        btnBack.setPrefHeight(btnHeight);

        pane.getChildren().add(inventoryLabel);
        pane.getChildren().add(inventoryComboBox);
        pane.getChildren().add(quantityLabel);
        pane.getChildren().add(quantityTextField);
        pane.getChildren().add(btnSubmit);
        pane.getChildren().add(btnBack);

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String inventory = inventoryComboBox.getValue();
                int id = Integer.parseInt(inventory.substring(0,inventory.indexOf('-')));

                int quantity = Integer.parseInt(quantityTextField.getText());
                HashMap<String, Integer> dataMap = new HashMap<>();
                dataMap.put("id",id);
                dataMap.put("quantity",quantity);
                String dataJson = new Gson().toJson(dataMap);


                new Thread(){
                    public void run(){
                        SRRPRequest request = new SRRPRequest();
                        request.setAction("deleteFromCart");
                        request.setData(dataJson);
                        SRRPResponse response= new SRRPClient().send(request);


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

                            request.setAction("getOrder");
                            SRRPResponse responseGetOrder = new SRRPClient().send(request);
                            String orderJson = responseGetOrder.getData();

                            Type type = new TypeToken<LinkedList<OrderModel>>() {}.getType();
                            shoppingCart.setOrder(new Gson().fromJson(orderJson, type));

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Pane deleteFromCartPane = new DeleteFromCartPane().createPane();
                                    pane.getChildren().clear();
                                    pane.getChildren().add(deleteFromCartPane);
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

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane retailPane = new RetailPane().createPane();
                pane.getChildren().clear();
                pane.getChildren().add(retailPane);
            }
        });

        return pane;
    }
}
