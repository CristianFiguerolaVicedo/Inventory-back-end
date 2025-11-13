package com.cristian.inventory.dto;

import com.cristian.inventory.util.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private Float productionPrice;
    private Float pvp;
    private Integer stock;
    private Integer packaging;
    private ProductStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
