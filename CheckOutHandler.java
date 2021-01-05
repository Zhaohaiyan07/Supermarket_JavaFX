package com.server;

import com.client.model.OrderModel;
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
import java.util.LinkedList;

public class CheckOutHandler implements SRRPRequestHandler {
    private Log log = LogFactory.getLog(getClass());

    @Override
    public SRRPResponse handle(SRRPRequest request) {
        String orderJson = request.getData();
        Type type = new TypeToken<LinkedList<OrderModel>>() {}.getType();
        LinkedList<OrderModel> shoppingcart = new Gson().fromJson(orderJson, type);
        int ifOperationOnSupermarketSucceed = 0; // greater than 0 means succeed
        int ifOperationOnShoppingCartSucceed = 0;

        for (int i = 0; i < shoppingcart.size(); i++) {
            OrderModel order = shoppingcart.get(i);
            int id = order.getOrderId();
            int orderQuantity = order.getOrderQuantity();

            //1.先获取每种商品还有多少库存
            String query = "SELECT s.quantity FROM shoppingcart c INNER JOIN supermarket s ON c.item_id = s.id WHERE c.id = %d" ;
            query = String.format(query, id);

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

            //2.修改库存数量
            query = "UPDATE supermarket s INNER JOIN shoppingcart c ON c.item_id = s.id SET s.quantity = %d WHERE c.id = %d";
            query = String.format(query, inventoryQuantity - orderQuantity, id);

            try {
                statement = connection.createStatement();
                ifOperationOnSupermarketSucceed = statement.executeUpdate(query);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            //3.删除购物车中的订单
            query = "DELETE FROM shoppingcart WHERE id = %d";
            query = String.format(query, id);

            try {
                statement = connection.createStatement();
                ifOperationOnShoppingCartSucceed = statement.executeUpdate(query);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        if(ifOperationOnSupermarketSucceed > 0 && ifOperationOnShoppingCartSucceed > 0){
            log.info("Successfully update the supermarket and shoppingcart table!");
            return SRRPResponse.ok("Successfully Check out!");

        }else if (ifOperationOnSupermarketSucceed > 0){
            log.error("Fail to update the shoppingcart table!");
            return SRRPResponse.error("Fail to update the shoppingcart table!");

        }else if (ifOperationOnShoppingCartSucceed > 0){
            log.error("Fail to update the supermarket table!");
            return SRRPResponse.error("Fail to update the supermarket table!");

        }else{
            log.error("Fail to update supermarket and shoppingcart table!");
            return SRRPResponse.error("Fail to update supermarket and shoppingcart table! ");
        }
    }
}
