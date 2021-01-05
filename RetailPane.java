package com.client.view;

import com.client.model.ShoppingCartModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class RetailPane {
    private int margin = 20;
    private int btnHeight = 30;
    private int btnWidth = 150;
    private ShoppingCartModel shoppingCart = new ShoppingCartModel();

    public Pane createPane(){
        Pane pane = new Pane();
        Button btnShoppingCart = new Button("查看购物车");
        Button btnAddItemToCart = new Button("添加商品");
        Button btnDeleteItemFromCart = new Button("删除商品");
        Button btnCheckOut = new Button("结账");
        Button btnBack = new Button("返回");

        btnShoppingCart.setLayoutX(margin);
        btnShoppingCart.setLayoutY(margin);
        btnShoppingCart.setPrefWidth(btnWidth);
        btnShoppingCart.setPrefHeight(btnHeight);

        btnAddItemToCart.setLayoutX(margin);
        btnAddItemToCart.setLayoutY(margin * 2 + btnShoppingCart.getPrefHeight());
        btnAddItemToCart.setPrefWidth(btnWidth);
        btnAddItemToCart.setPrefHeight(btnHeight);

        btnDeleteItemFromCart.setLayoutX(margin);
        btnDeleteItemFromCart.setLayoutY(margin * 3 + btnShoppingCart.getPrefHeight() + btnAddItemToCart.getPrefHeight());
        btnDeleteItemFromCart.setPrefWidth(btnWidth);
        btnDeleteItemFromCart.setPrefHeight(btnHeight);

        btnCheckOut.setLayoutX(margin);
        btnCheckOut.setLayoutY(margin * 4 + btnShoppingCart.getPrefHeight() + btnAddItemToCart.getPrefHeight() + btnDeleteItemFromCart.getPrefHeight());
        btnCheckOut.setPrefWidth(btnWidth);
        btnCheckOut.setPrefHeight(btnHeight);

        btnBack.setLayoutX(margin);
        btnBack.setLayoutY(margin * 5 + btnShoppingCart.getPrefHeight() + btnAddItemToCart.getPrefHeight() + btnDeleteItemFromCart.getPrefHeight() + btnCheckOut.getPrefHeight());
        btnBack.setPrefWidth(btnWidth);
        btnBack.setPrefHeight(btnHeight);

        pane.getChildren().add(btnShoppingCart);
        pane.getChildren().add(btnAddItemToCart);
        pane.getChildren().add(btnDeleteItemFromCart);
        pane.getChildren().add(btnCheckOut);
        pane.getChildren().add(btnBack);

        btnShoppingCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane shoppingCartPane = new ShoppingCartPane().createPane(shoppingCart.getOrder());
                pane.getChildren().clear();
                pane.getChildren().add(shoppingCartPane);
            }
        });

        btnAddItemToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane addToCartPane = new AddToCartPane().createPane();
                pane.getChildren().clear();
                pane.getChildren().add(addToCartPane);

            }
        });

        btnDeleteItemFromCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Pane deleteFromCartPane = new DeleteFromCartPane().createPane();
                pane.getChildren().clear();
                pane.getChildren().add(deleteFromCartPane);
            }
        });

        btnCheckOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane checkOutPane = new CheckOutPane().createPane(shoppingCart.getOrder());
                pane.getChildren().clear();
                pane.getChildren().add(checkOutPane);
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
        return pane;
    }
}
