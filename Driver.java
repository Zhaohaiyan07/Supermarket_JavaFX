package com.server;


import com.srrp.SRRPServer;

public class Driver {
    public static void main(String[] args) {
        SRRPServer srrpServer = new SRRPServer();
        srrpServer.addHandler("get",new GetHandler());
        srrpServer.addHandler("stockin",new StockInHandler());
        srrpServer.addHandler("stockout",new StockOutHandler());
        srrpServer.addHandler("increaseQuantity",new StockInOldItem());

        srrpServer.addHandler("getOrder",new GetOrderHandler());
        srrpServer.addHandler("addToCart",new AddToCartHandler());
        srrpServer.addHandler("deleteFromCart",new DeleteFromCartHandler());
        srrpServer.addHandler("checkout",new CheckOutHandler());
        srrpServer.start();
    }
}
