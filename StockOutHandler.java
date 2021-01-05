package com.server;

import com.client.model.ItemModel;
import com.google.gson.Gson;
import com.srrp.SRRPRequest;
import com.srrp.SRRPRequestHandler;
import com.srrp.SRRPResponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockOutHandler implements SRRPRequestHandler {
    @Override
    public SRRPResponse handle(SRRPRequest request) {
        // JSON String -> ItemModel Object
        String dataJson = request.getData();
        ItemModel item = new Gson().fromJson(dataJson, ItemModel.class);

        int deleteQuantity = item.getQuantity();
        int id = item.getId();
        String query = "SELECT quantity FROM supermarket WHERE id = %d";
        query = String.format(query,id);

        Connection connection = Database.newConnection();
        Statement statement = null;
        int inventoryQuantity = 0;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                inventoryQuantity = resultSet.getInt("quantity");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(inventoryQuantity > deleteQuantity){
            query = "UPDATE supermarket SET quantity = quantity - %d WHERE id = %d ";
            query = String.format(query,deleteQuantity,id);

            int i = 0;
            try {
                i = statement.executeUpdate(query);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(i > 0){
                return SRRPResponse.ok("Successfully stock out!");
            }else{
                return SRRPResponse.error("Something went wrong!");
            }
        }else{
            return SRRPResponse.error("Not enough quantity to stock out!");
        }
    }
}
