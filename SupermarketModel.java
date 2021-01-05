package com.client.model;

import com.srrp.SRRPClient;
import com.srrp.SRRPRequest;
import com.srrp.SRRPResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class SupermarketModel {
    public LinkedList<ItemModel> inventory = new LinkedList<>();
    public static int id = 000000;

    public SupermarketModel(){

        SRRPRequest request = new SRRPRequest();
        request.setAction("get");
        SRRPResponse response = new SRRPClient().send(request);
        String inventoryJson = response.getData();

        Type type = new TypeToken<LinkedList<ItemModel>>() {}.getType();
        inventory = new Gson().fromJson(inventoryJson, type);
    }

    public void stockIn(ItemModel item){
        inventory.add(item);
        String ItemModelJson = new Gson().toJson(item);

        new Thread(){
            @Override
            public void run(){
                SRRPRequest request = new SRRPRequest();
                request.setAction("add");
//                request.setData(ItemModelJson);
            }
        }.start();
    }

    public void stockOut(ItemModel item){
        int i = inventory.indexOf(item);
        inventory.get(i).setQuantity(0);
    }

    public ItemModel getItem(int id) {
        ItemModel item = null;
        for (int i = 0; i < inventory.size(); i++) {
            item = inventory.get(i);
            if (item.getId() == id) {
                return item;
            }else{
                item = null;
            }
        }
        return item;
    }

}
