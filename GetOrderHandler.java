package com.server;

import com.client.model.OrderModel;
import com.client.common.OrderORM;
import com.google.gson.Gson;
import com.srrp.SRRPRequest;
import com.srrp.SRRPRequestHandler;
import com.srrp.SRRPResponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class GetOrderHandler implements SRRPRequestHandler {
    @Override
    public SRRPResponse handle(SRRPRequest request) {
        // prepare the SQL query
        String query = "SELECT c.id, s.name, s.price, c.order_quantity FROM shoppingcart c INNER JOIN supermarket s ON c.item_id = s.id";

        // establish the connection
        Connection connection = Database.newConnection();
        SRRPResponse response = new SRRPResponse();
        try {
            //execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            LinkedList<OrderModel> shoppingCart = new LinkedList<>();

            while(resultSet.next()){
                OrderModel order = OrderORM.toObject(resultSet);
                shoppingCart.add(order);
            }

            String shoppingCartJson = new Gson().toJson(shoppingCart);

            response.setData(shoppingCartJson);
            response.setStatus("ok");

        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
