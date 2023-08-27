package com.example.androidapp.model;

import androidx.annotation.NonNull;

public class OrderDetail {
    private long id;
    private long orderId;
    private long flowerId;
    private int quantity;
    private double subTotal;

    public OrderDetail(long id, int quantity, double subTotal, long orderId, long flowerId) {
        this.id = id;
        this.orderId = orderId;
        this.flowerId = flowerId;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(long flowerId) {
        this.flowerId = flowerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.subTotal);
    }
}
