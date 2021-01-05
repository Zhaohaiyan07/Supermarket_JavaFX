package com.server;


import com.client.model.ItemModel;
import com.google.gson.Gson;
import com.srrp.SRRPRequest;
import com.srrp.SRRPRequestHandler;
import com.srrp.SRRPResponse;
import java.sql.Connection;
import java.sql.Statement;

public class StockInHandler implements SRRPRequestHandler {

    @Override
    public SRRPResponse handle(SRRPRequest request) {
        //Request -> JSON
        String dataJson = request.getData();

        //JSON -> com.client.model.ItemModel Object
        ItemModel newItem = new Gson().fromJson(dataJson, ItemModel.class);

        //2.
        String query = "INSERT INTO supermarket (name, category, price, quantity) VALUES (\"%s\",\"%s\",%f,%d)";
        query = String.format(query,newItem.getName(),newItem.getCategory(),newItem.getPrice(),newItem.getQuantity());

        // SQL -> send
        Connection connection = Database.newConnection();
        int i = 0;
        try {
            Statement statement = connection.createStatement();
            i = statement.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(i > 0){
            return SRRPResponse.ok("Successfully add " + newItem.getName());
        }else{
            return SRRPResponse.error("Fail to add " + newItem.getName());
        }

    }
}
