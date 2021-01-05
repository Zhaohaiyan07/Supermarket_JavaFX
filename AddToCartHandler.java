package com.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.srrp.SRRPRequest;
import com.srrp.SRRPRequestHandler;
import com.srrp.SRRPResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;

public class AddToCartHandler implements SRRPRequestHandler {

    public Log log = LogFactory.getLog(AddToCartHandler.class);

    @Override
    public SRRPResponse handle(SRRPRequest request) {

        String data = request.getData();
        Type type = new TypeToken<HashMap<String, Integer>>() {}.getType();
        HashMap<String,Integer> dataMap = new Gson().fromJson(data, type);
        int id = dataMap.get("id");
        int orderQuantity = dataMap.get("order_quantity");

        //1. 查看库存的数量
        String query = "SELECT quantity FROM supermarket WHERE id = %d" ;
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


        //2.确定库存是否足够
        if(inventoryQuantity >= orderQuantity){

            //3.判断购物车中是否已经有这个商品了
            query = "SELECT EXISTS(SELECT * from shoppingcart WHERE item_id = %d) exist";
            query = String.format(query, id);

            int ifRecordExist = 0;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()){
                    ifRecordExist = resultSet.getInt("exist") ;
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            //5.没有这种商品，INSERT
            int i = 0;
            //4.有这种商品，直接修改数量
            if(ifRecordExist > 0){
                query = "UPDATE shoppingcart SET order_quantity = order_quantity + %d WHERE item_id = %d";
                query = String.format(query, orderQuantity, id);

                try {
                    i = statement.executeUpdate(query);
                } catch (SQLException throwables) {
                    log.warn("SQL exception!Can't UPDATE!",throwables);
                    throwables.printStackTrace();
                }

            }else{
                query = "INSERT INTO shoppingcart (item_id, order_quantity) VALUES (%d, %d)";
                query = String.format(query, id, orderQuantity);

                try {
                    i = statement.executeUpdate(query);
                } catch (SQLException throwables) {
                    log.warn("SQL exception!Can't INSERT!",throwables);
                    throwables.printStackTrace();
                }
            }

            if(i>0){

                return SRRPResponse.ok("Succeed to add to cart!");

            }else{
                log.error("Fail to add to cart!");
                return SRRPResponse.error("Fail to add to cart!");
            }

        }else{
            log.error("Not enough quantity to add to cart!");

            return SRRPResponse.error("Not enough quantity to add to cart!");
        }

    }
}
