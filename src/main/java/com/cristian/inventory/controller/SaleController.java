package com.cristian.inventory.controller;

import com.cristian.inventory.dto.SaleDto;
import com.cristian.inventory.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleDto> addSale(@RequestBody SaleDto saleDto) {
        SaleDto saved = saleService.addSale(saleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
