package com.cristian.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDetailDto {

    private Long productId;
    private Integer quantity;
    private Float unitPrice;
}
