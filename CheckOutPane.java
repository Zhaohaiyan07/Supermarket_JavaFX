package com.client.view;

import com.client.model.OrderModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class CheckOutPane {
    private int margin = 10;
    private int rowMargin = 5;
    private int width = 500;
    private int labelWidth = 100;
    private int btnHeight = 30;
    private double totalAmount = 0;
    private Log log = LogFactory.getLog(getClass());

    public Pane createPane(LinkedList<OrderModel> orders) {

        Pane pane = new Pane();

        Label paidLabel = new Label("请付款：");
        TextField paidTextField = new TextField();
        Button button = new Button("结账");


        Pane receiptPane = new Pane();
        receiptPane.setLayoutX(margin);
        receiptPane.setLayoutY(margin);
        receiptPane.setPrefHeight(300);

        Label titleLabel = new Label("ZZAX Supermarket");
        titleLabel.setLayoutX(margin);
        titleLabel.setLayoutY(margin);
        titleLabel.setPrefWidth(width - margin * 3 - labelWidth);
        titleLabel.setPrefHeight(30);

        Button btnBack = new Button("返回");
        btnBack.setLayoutX(margin + titleLabel.getPrefWidth());
        btnBack.setLayoutY(margin);
        btnBack.setPrefWidth(labelWidth);
        btnBack.setPrefHeight(btnHeight);

        receiptPane.getChildren().add(titleLabel);
        receiptPane.getChildren().add(btnBack);

        int row = 0;
        for (int i = 0; i < orders.size(); i++) {
            row ++;
            OrderModel order = orders.get(i);
            Label idLabel = new Label(Integer.toString(order.getOrderId()));
            Label nameLabel = new Label(order.getName());
            Label priceLabel = new Label(Double.toString(order.getPrice()));

            idLabel.setLayoutX(margin);
            idLabel.setLayoutY(rowMargin * row + btnHeight * (row -1) + titleLabel.getPrefHeight());
            idLabel.setPrefWidth(30);
            idLabel.setPrefHeight(btnHeight);

            nameLabel.setLayoutX(margin * 2 + idLabel.getPrefWidth());
            nameLabel.setLayoutY(rowMargin * row + btnHeight * (row -1)+ titleLabel.getPrefHeight());
            nameLabel.setPrefWidth(160);
            nameLabel.setPrefHeight(btnHeight);

            priceLabel.setLayoutX(margin * 3 + idLabel.getPrefWidth() + nameLabel.getPrefWidth());
            priceLabel.setLayoutY(rowMargin * row + btnHeight * (row -1) + titleLabel.getPrefHeight());
            priceLabel.setPrefWidth(100);
            priceLabel.setPrefHeight(btnHeight);

            receiptPane.getChildren().add(idLabel);
            receiptPane.getChildren().add(nameLabel);
            receiptPane.getChildren().add(priceLabel);

            if(order.getOrderQuantity() > 1){
                row ++;
                Label quantityLabel = new Label("X " + Integer.toString(order.getOrderQuantity()));
                Label totalPriceForSingleItemLabel = new Label(Double.toString(order.getPrice() * order.getOrderQuantity()));

                quantityLabel.setLayoutX(margin * 2 + idLabel.getPrefWidth());
                quantityLabel.setLayoutY(rowMargin * row + btnHeight * (row -1) + titleLabel.getPrefHeight());
                quantityLabel.setPrefWidth(160);
                quantityLabel.setPrefHeight(btnHeight);

                totalPriceForSingleItemLabel.setLayoutX(margin * 3 + idLabel.getPrefWidth() + nameLabel.getPrefWidth());
                totalPriceForSingleItemLabel.setLayoutY(rowMargin * row + btnHeight * (row -1) + titleLabel.getPrefHeight());
                totalPriceForSingleItemLabel.setPrefWidth(100);
                totalPriceForSingleItemLabel.setPrefHeight(btnHeight);

                receiptPane.getChildren().add(quantityLabel);
                receiptPane.getChildren().add(totalPriceForSingleItemLabel);
            }
        }

        row++;
        Label totalLabel = new Label("Total:");
        Label totalAmountLabel = new Label();

        totalLabel.setLayoutX(margin);
        totalLabel.setLayoutY(rowMargin * row + btnHeight * (row -1) + titleLabel.getPrefHeight());
        totalLabel.setPrefWidth(labelWidth);
        totalLabel.setPrefHeight(btnHeight);


        for (int i = 0; i < orders.size(); i++) {
            totalAmount += orders.get(i).getPrice() * orders.get(i).getOrderQuantity();
        }
        totalAmountLabel.setText(Double.toString(totalAmount));
        totalAmountLabel.setLayoutX(margin * 3 + 200);
        totalAmountLabel.setLayoutY(rowMargin * row + btnHeight * (row -1) + titleLabel.getPrefHeight());
        totalAmountLabel.setPrefWidth(labelWidth);
        totalAmountLabel.setPrefHeight(btnHeight);

        receiptPane.getChildren().add(totalLabel);
        receiptPane.getChildren().add(totalAmountLabel);

        Pane paymentPane = new Pane();
        paidLabel.setLayoutX(margin);
        paidLabel.setLayoutY(margin + receiptPane.getPrefHeight());
        paidLabel.setPrefWidth(labelWidth);
        paidLabel.setPrefHeight(btnHeight);

        paidTextField.setLayoutX(margin * 2 + paidLabel.getPrefWidth());
        paidTextField.setLayoutY(margin + receiptPane.getPrefHeight());
        paidTextField.setPrefWidth(100);
        paidTextField.setPrefHeight(btnHeight);

        button.setLayoutX(margin * 3 + paidLabel.getPrefWidth() + paidTextField.getPrefWidth());
        button.setLayoutY(margin + receiptPane.getPrefHeight());
        button.setPrefWidth(labelWidth);
        button.setPrefHeight(btnHeight);

        paymentPane.getChildren().add(paidLabel);
        paymentPane.getChildren().add(paidTextField);
        paymentPane.getChildren().add(button);

        pane.getChildren().add(receiptPane);

        if(orders.size() > 0){
            pane.getChildren().add(paymentPane);
        }


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String orderJson = new Gson().toJson(orders);

                double paidAmount = 0;
                try{
                    paidAmount = Double.parseDouble(paidTextField.getText());
                }catch(NumberFormatException e){
                    log.error("Empty String.There is nothing in the paidTextField", e);
                }

                if(totalAmount > paidAmount){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("Not enough money to pay!");
                    alert.setContentText("Please re-enter a valid number.");
                    alert.showAndWait();

                }else if (totalAmount == paidAmount){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Succeed!");
                    alert.setHeaderText("You have paid the bill!");
                    alert.setContentText("Thank you for shopping in ZZAX Supermarket.");
                    alert.showAndWait();
                    new Thread(){
                        @Override
                        public void run(){
                            //拿到购买的数量传过去
                            SRRPRequest request = new SRRPRequest();
                            request.setAction("checkout");
                            request.setData(orderJson);
                            SRRPResponse response = new SRRPClient().send(request);
                            System.out.println(response.getData());
                        }
                    }.start();

                }else{
                    String changeAmount = Double.toString(paidAmount - totalAmount);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Succeed!");
                    alert.setHeaderText("You have paid the bill!");
                    alert.setContentText("The change is " + changeAmount);
                    alert.showAndWait();

                    new Thread(){
                        @Override
                        public void run(){
                            SRRPRequest request = new SRRPRequest();
                            request.setAction("checkout");
                            request.setData(orderJson);
                            SRRPResponse response = new SRRPClient().send(request);

                            if(response.isOk()){
                                request.setAction("getOrder");
                                SRRPResponse responseGetOrder = new SRRPClient().send(request);
                                String orderJson = responseGetOrder.getData();

                                Type type = new TypeToken<LinkedList<OrderModel>>() {}.getType();
                                LinkedList<OrderModel> orderModels = new Gson().fromJson(orderJson, type);

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Pane checkOutPane = new CheckOutPane().createPane(orderModels);
                                        pane.getChildren().clear();
                                        pane.getChildren().add(checkOutPane);
                                    }
                                });
                            }
                        }
                    }.start();

                }

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
