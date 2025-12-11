package com.cristian.inventory.controller;

import com.cristian.inventory.dto.IncomeDto;
import com.cristian.inventory.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeDto> addIncome(@RequestBody IncomeDto incomeDto) {
        IncomeDto saved = incomeService.addIncome(incomeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<IncomeDto>> getIncome() {
        List<IncomeDto> incomes = incomeService.getCurrentMonthIncomesForCurrentUser();
        return ResponseEntity.ok(incomes);
    }
}
