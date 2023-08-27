package com.example.androidapp.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class OrderObject {
    private Date orderDate;
    private Date deliveryDate;
    private Date createdAt;
    private long id;
    private long customerId;
    private String orderStatus;
    private int totalAmount;
    private double totalPrice;

    public OrderObject(long id, Date orderDate, Date deliveryDate, String orderStatus, int totalAmount, double totalPrice, long customerId, Date createdAt) {
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.createdAt = createdAt;
        this.id = id;
        this.customerId = customerId;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.totalPrice);
    }
}
