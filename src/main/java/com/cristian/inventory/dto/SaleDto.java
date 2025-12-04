package com.cristian.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDto {

    private Long id;
    private LocalDate date;
    private Boolean iva;
    private Boolean sent;
    private Boolean contract;
    private Float packaging;
    private Float sendingFees;
    private Float productionFees;
    private List<SaleDetailDto> details;
}
