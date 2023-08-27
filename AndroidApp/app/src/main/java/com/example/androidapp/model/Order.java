package com.example.androidapp.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SuppressLint("ParcelCreator")
public class Order {
    private Date dateCreateOrder;
    private Date dateArrivalOrder;
    private double totalPriceOrder;
    private int totalAmountOrder;
    private String status;
    private long customerId;
    private Date createAt;
    private ArrayList<CartDetail> cartDetailList;

    public Order(Date dateCreateOrder, Date dateArrivalOrder, double totalPriceOrder, int totalAmountOrder, String status, long customerId, Date createAt, ArrayList<CartDetail> cartDetailList) {
        this.dateCreateOrder = dateCreateOrder;
        this.dateArrivalOrder = dateArrivalOrder;
        this.totalPriceOrder = totalPriceOrder;
        this.totalAmountOrder = totalAmountOrder;
        this.status = status;
        this.customerId = customerId;
        this.createAt = createAt;
        this.cartDetailList = cartDetailList;
    }

    public Date getDateCreateOrder() {
        return dateCreateOrder;
    }

    public void setDateCreateOrder(Date dateCreateOrder) {
        this.dateCreateOrder = dateCreateOrder;
    }

    public Date getDateArrivalOrder() {
        return dateArrivalOrder;
    }

    public void setDateArrivalOrder(Date dateArrivalOrder) {
        this.dateArrivalOrder = dateArrivalOrder;
    }

    public double getTotalPriceOrder() {
        return totalPriceOrder;
    }

    public void setTotalPriceOrder(double totalPriceOrder) {
        this.totalPriceOrder = totalPriceOrder;
    }

    public int getTotalAmountOrder() {
        return totalAmountOrder;
    }

    public void setTotalAmountOrder(int totalAmountOrder) {
        this.totalAmountOrder = totalAmountOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public ArrayList<CartDetail> getProductList() {
        return cartDetailList;
    }

    public void setProductList(ArrayList<CartDetail> cartDetailList) {
        this.cartDetailList = cartDetailList;
    }

    @NonNull
    @Override
    public String toString() {
        // Ép chuỗi từ kiểu dữ liệu Date sang String
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
        String dateString = dateFormat.format(this.dateCreateOrder);

        // Xuất ra thông tin tóm tắt của đơn hàng
        String str = Integer.toString(this.totalAmountOrder) + "\n" + Double.toString(this.totalPriceOrder) + "\n" + dateString;
        return str;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeLong(dateCreateOrder.getTime());
//        dest.writeLong(dateArrivalOrder.getTime());
//        dest.writeDouble(subTotal);
//        dest.writeDouble(serviceFee);
//        dest.writeDouble(deliveryFee);
//        dest.writeDouble(totalPriceOrder);
//        dest.writeInt(totalAmountOrder);
//        dest.writeList((ArrayList<? extends Parcelable>) productList);
//    }
}
