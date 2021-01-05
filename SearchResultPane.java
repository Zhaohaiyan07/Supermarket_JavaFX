package com.client.view;

import com.client.model.ItemModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import java.util.LinkedList;

public class SearchResultPane {

    public Pane createPane(LinkedList<ItemModel> searchResult){
        Pane searchResutlPane = new Pane();

        ObservableList<ItemModel> searchResultList = FXCollections.observableArrayList(searchResult);

        TableView<ItemModel> resultTable = new TableView<>();

        TableColumn<ItemModel,Integer> idCol = new TableColumn("ID");
        idCol.setMinWidth(20);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ItemModel,String> nameCol = new TableColumn("Name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ItemModel,String> categoryCol = new TableColumn("Category");
        categoryCol.setMinWidth(100);
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<ItemModel,Double> priceCol = new TableColumn("Price");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        TableColumn<ItemModel,Integer> quantityCol = new TableColumn("Quantity");
        quantityCol.setMinWidth(100);
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        resultTable.setItems(searchResultList);
        resultTable.getColumns().addAll(idCol,nameCol,categoryCol,priceCol,quantityCol);

        searchResutlPane.getChildren().add(resultTable);
        return searchResutlPane;
    }
}
