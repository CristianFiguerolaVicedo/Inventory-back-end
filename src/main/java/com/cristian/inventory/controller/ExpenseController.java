package com.cristian.inventory.controller;

import com.cristian.inventory.dto.ExpenseDto;
import com.cristian.inventory.dto.IncomeDto;
import com.cristian.inventory.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto) {
        ExpenseDto saved = expenseService.addExpense(expenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getIncome() {
        List<ExpenseDto> incomes = expenseService.getCurrentMonthExpensesForCurrentUser();
        return ResponseEntity.ok(incomes);
    }
}
