package com.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.srrp.SRRPRequest;
import com.srrp.SRRPRequestHandler;
import com.srrp.SRRPResponse;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class StockInOldItem implements SRRPRequestHandler {

    @Override
    public SRRPResponse handle(SRRPRequest request) {
        String dataJson = request.getData();

        // JSON String -> HashMap
        Type type = new TypeToken<HashMap<String, Integer>>() {}.getType();
        HashMap<String, Integer> dataMap = new Gson().fromJson(dataJson, type);
        int id = dataMap.get("id");
        int quantity = dataMap.get("quantity");


        // get the original quantity of the item
        String query = "SELECT quantity FROM supermarket WHERE id = %d";
        query = String.format(query, id);

        Connection connection = Database.newConnection();
        Statement statement = null;
        int originalQuantity = 0;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                originalQuantity = resultSet.getInt("quantity");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        // update the final quantity
        query = "UPDATE supermarket SET quantity = quantity + %d WHERE id = %d";
        query = String.format(query, quantity, id);
        Statement statement1 = null;
        try {
            statement1 = connection.createStatement();
            statement1.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //send back the response
        SRRPResponse response = SRRPResponse.ok("Successfully stock in!");
        return response;
    }
}
