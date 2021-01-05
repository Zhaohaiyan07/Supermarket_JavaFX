package com.client.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.srrp.SRRPClient;
import com.srrp.SRRPRequest;
import com.srrp.SRRPResponse;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class ShoppingCartModel {
    static private LinkedList<OrderModel> order;

    public ShoppingCartModel(){
        SRRPRequest request = new SRRPRequest();
        request.setAction("getOrder");
        SRRPResponse response = new SRRPClient().send(request);
        String orderJson = response.getData();

        Type type = new TypeToken<LinkedList<OrderModel>>() {}.getType();
        order = new Gson().fromJson(orderJson, type);
    }

    public LinkedList<OrderModel> getOrder() {
        return order;
    }

    public void setOrder(LinkedList<OrderModel> order) {
        this.order = order;
    }
}
