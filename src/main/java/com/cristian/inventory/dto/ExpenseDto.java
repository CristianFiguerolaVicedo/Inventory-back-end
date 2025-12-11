package com.cristian.inventory.dto;

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
public class ExpenseDto {

    private Long id;
    private String name;
    private Float amount;
    private LocalDate date;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
