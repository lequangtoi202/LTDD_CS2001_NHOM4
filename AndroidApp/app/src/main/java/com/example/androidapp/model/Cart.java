package com.example.androidapp.model;

import androidx.annotation.NonNull;

public class Cart {
    private int totalAmount;
    private double totalPrice;
    private long id;
    private long accountId;

    public Cart() {}

    public Cart(long id, int totalAmount, double totalPrice, long accountId) {
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
        this.id = id;
        this.accountId = accountId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.totalAmount);
    }
}
