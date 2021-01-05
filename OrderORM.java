package com.client.common;

import com.client.model.OrderModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderORM {

    public static OrderModel toObject(ResultSet resultSet){
        OrderModel order = new OrderModel();

        try {
            int id = resultSet.getInt("id");
            order.setOrderId(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            String name = resultSet.getString("name");
            order.setName(name);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            double price = resultSet.getDouble("price");
            order.setPrice(price);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            int orderQuantity = resultSet.getInt("order_quantity");
            order.setOrderQuantity(orderQuantity);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return order;
    }
}
