package com.client.view;

import com.client.model.ItemModel;
import com.client.model.SupermarketModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.util.LinkedList;

public class SearchPane {
    private int margin = 20;
    private int labelWidth = 100;
    private int btnHeight = 30;
    private int btnWidth = 50;
    private int width = 500;
    private int paneHeight = 100;
    SupermarketModel supermarket = new SupermarketModel();

    public Pane createPane(){
        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(300);

        Label label = new Label("请输入搜索关键字：");
        TextField keywordTextField = new TextField();
        Button btnSearch = new Button("搜索");
        Button btnBack = new Button("返回");

        label.setLayoutX(margin);
        label.setLayoutY(margin);
        label.setPrefHeight(btnHeight);
        label.setPrefWidth(150);

        keywordTextField.setLayoutX(margin * 2 + label.getPrefWidth());
        keywordTextField.setLayoutY(margin);
        keywordTextField.setPrefHeight(btnHeight);
        keywordTextField.setPrefWidth(100);

        btnSearch.setLayoutX(margin * 3 + label.getPrefWidth() + keywordTextField.getPrefWidth());
        btnSearch.setLayoutY(margin);
        btnSearch.setPrefHeight(btnHeight);
        btnSearch.setPrefWidth(btnWidth);

        btnBack.setLayoutX(margin * 4 + label.getPrefWidth() + keywordTextField.getPrefWidth() + btnSearch.getPrefWidth());
        btnBack.setLayoutY(margin);
        btnBack.setPrefHeight(btnHeight);
        btnBack.setPrefWidth(btnWidth);

        pane.getChildren().add(label);
        pane.getChildren().add(keywordTextField);
        pane.getChildren().add(btnSearch);
        pane.getChildren().add(btnBack);

        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LinkedList<ItemModel> searchedResult = new LinkedList<>();
                String searchKeyword = keywordTextField.getText();
                for (int i = 0; i < supermarket.inventory.size(); i++) {
                    ItemModel item = supermarket.inventory.get(i);
                    String itemString = item.toString();
                    if(itemString.contains(searchKeyword)){
                        searchedResult.add(item);
                    }
                }
                Pane searchResultPane = new SearchResultPane().createPane(searchedResult);
                searchResultPane.setLayoutY(paneHeight);
                pane.getChildren().add(searchResultPane);
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
