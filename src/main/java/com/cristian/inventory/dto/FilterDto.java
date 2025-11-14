package com.cristian.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterDto {

    private Long categoryId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String keyword;
    private String sortField;
    private String sortOrder;
}
