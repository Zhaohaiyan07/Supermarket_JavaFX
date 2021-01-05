package com.client.view;

import com.client.model.OrderModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import java.util.LinkedList;

public class ShoppingCartPane {
    private int margin = 20;
    private int labelWidth = 100;
    private int btnHeight = 30;
    private int btnWidth = 50;

    public Pane createPane(LinkedList<OrderModel> orders){
        Pane pane = new Pane();
        Label label = new Label("购物车");
        Button btnBack = new Button("返回");

        label.setLayoutX(margin);
        label.setLayoutY(margin);
        label.setPrefHeight(btnHeight);
        label.setPrefWidth(labelWidth);

        btnBack.setLayoutX(margin * 4 + label.getPrefWidth());
        btnBack.setLayoutY(margin);
        btnBack.setPrefWidth(btnWidth);
        btnBack.setPrefHeight(btnHeight);

        Pane shoppingCartPane = new Pane();
        shoppingCartPane.setLayoutY(50);
        shoppingCartPane.setPrefHeight(100);

        ObservableList<OrderModel> items = FXCollections.observableArrayList(orders);
        TableView<OrderModel> shoppingCart = new TableView<>();

        TableColumn<OrderModel,Integer> orderIdCol = new TableColumn<>("Order");
        orderIdCol.setMinWidth(20);
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<OrderModel,String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<OrderModel,Double> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(100);
        PropertyValueFactory<OrderModel,Double> price = new PropertyValueFactory<>("price");
        priceCol.setCellValueFactory(price);

        TableColumn<OrderModel,Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setMinWidth(100);
        PropertyValueFactory<OrderModel,Integer> orderQuantity = new PropertyValueFactory<>("orderQuantity");
        quantityCol.setCellValueFactory(orderQuantity);


        shoppingCart.setItems(items);
        shoppingCart.getColumns().addAll(orderIdCol,nameCol,priceCol,quantityCol);
        shoppingCartPane.getChildren().add(shoppingCart);

        pane.getChildren().add(label);
        pane.getChildren().add(btnBack);
        pane.getChildren().add(shoppingCartPane);

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
