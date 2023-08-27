package com.quangtoi.flowerstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowerResponse {
    private Long id;
    private String name;
    private String description;
    private double unitPrice;
    private String urlImage;
    private int stockQuantity;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int totalPreviews;
    private double avgScore;
    private int saleVolume;
}
