package com.client.common;

import com.client.model.ItemModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemORM {
    public static ItemModel toObject(ResultSet resultSet){
        ItemModel item = new ItemModel();

        try {
            item.setId(resultSet.getInt("id"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            item.setName(resultSet.getString("name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            item.setCategory(resultSet.getString("category"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            item.setPrice(resultSet.getDouble("price"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            item.setQuantity(resultSet.getInt("quantity"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return item;
    }
}
