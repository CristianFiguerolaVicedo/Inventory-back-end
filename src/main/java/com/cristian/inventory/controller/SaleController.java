package com.cristian.inventory.controller;

import com.cristian.inventory.dto.SaleDto;
import com.cristian.inventory.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<SaleDto>> getSales() {
        List<SaleDto> sales = saleService.getCurrentMonthSalesForCurrentUser();
        return ResponseEntity.ok(sales);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDto> updateSale(@PathVariable Long id, @RequestBody SaleDto saleDto) {
        SaleDto updatedProduct = saleService.updateSale(id, saleDto);
        return ResponseEntity.ok(updatedProduct);
    }
}
