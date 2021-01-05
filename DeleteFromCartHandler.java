package com.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.srrp.SRRPRequest;
import com.srrp.SRRPRequestHandler;
import com.srrp.SRRPResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DeleteFromCartHandler implements SRRPRequestHandler {

    Log log = LogFactory.getLog(DeleteFromCartHandler.class);

    @Override
    public SRRPResponse handle(SRRPRequest request) {
        String data = request.getData();
        Type type = new TypeToken<HashMap<String, Integer>>() {}.getType();
        HashMap<String, Integer> dataMap = new Gson().fromJson(data, type);

        int id = dataMap.get("id");
        int deleteQuantity = dataMap.get("quantity");

        String query = "SELECT order_quantity FROM shoppingcart WHERE id = %d";
        query = String.format(query, id);

        Connection connection = Database.newConnection();
        Statement statement = null;
        int orderQuantity = 0;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                orderQuantity = resultSet.getInt("order_quantity");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (orderQuantity > deleteQuantity) {
            query = "UPDATE shoppingcart SET order_quantity = %d WHERE id = %d";
            query = String.format(query, orderQuantity - deleteQuantity, id);
            int i = 0;
            try {
                i = statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (i > 0) {
                return SRRPResponse.ok("Successfully delete from cart!");
            } else {
                return SRRPResponse.error("Fail to delete from cart!");
            }
        } else if (orderQuantity == deleteQuantity) {
            query = "DELETE FROM shoppingcart WHERE id = %d";
            query = String.format(query, id);

            int i = 0;
            try {
                i = statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (i > 0) {
                return SRRPResponse.ok("Successfully clear from cart!");
            } else {
                return SRRPResponse.error("Fail to clear from cart!");
            }

        }else {
            log.error("Exceed the order quantity!");
            return SRRPResponse.error("Exceed the order quantity!");
        }
    }
}
