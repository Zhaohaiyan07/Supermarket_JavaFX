package com.server;


import com.client.model.ItemModel;
import com.client.common.ItemORM;
import com.google.gson.Gson;
import com.srrp.SRRPRequest;
import com.srrp.SRRPRequestHandler;
import com.srrp.SRRPResponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class GetHandler implements SRRPRequestHandler {
    @Override
    public SRRPResponse handle(SRRPRequest request) {
        // prepare the SQL query
        String query = "SELECT * FROM supermarket";

        // establish the connection
        Connection connection = Database.newConnection();
        SRRPResponse response = new SRRPResponse();
        try {
            //execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            LinkedList<ItemModel> inventory = new LinkedList<>();

            while(resultSet.next()){
                ItemModel item = ItemORM.toObject(resultSet);
                inventory.add(item);
            }

            String inventoryJson = new Gson().toJson(inventory);

            response.setData(inventoryJson);
            response.setStatus("ok");

        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
