package com.example.androidapp.model;

import java.util.Date;

public class Flower {

    private int id;
    private String name;
    private String description;
    private double unitPrice = 0.0;
    private String urlImage;
    private int stockQuantity;
    private Long categoryId;
    private Date createdAt;
    private Date updatedAt;
    private int totalPreviews;
    private double avgScore;

    public Flower(){}

    public Flower(int id, String name, String description, double unitPrice, String urlImage, int stockQuantity, Long categoryId, Date createdAt, Date updatedAt, int totalPreviews, double avgScore) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.urlImage = urlImage;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.totalPreviews = totalPreviews;
        this.avgScore = avgScore;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getTotalPreviews() {
        return totalPreviews;
    }

    public void setTotalPreviews(int totalPreviews) {
        this.totalPreviews = totalPreviews;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
