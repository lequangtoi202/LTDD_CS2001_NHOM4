package com.example.androidapp.model;

import java.io.Serializable;

public class CartDetail implements Serializable {

    private int quantity;
    private long cartId;
    private long id;
    private long flowerId;
    private double totalPrice;

    public CartDetail(long id, int quantity, double totalPrice, long cartId, long flowerId) {
        this.quantity = quantity;
        this.cartId = cartId;
        this.id = id;
        this.flowerId = flowerId;
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(long flowerId) {
        this.flowerId = flowerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        String str = "ok";
        return str;
    }
}
