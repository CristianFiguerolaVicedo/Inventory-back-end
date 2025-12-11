package com.cristian.inventory.service;

import com.cristian.inventory.dto.ExpenseDto;
import com.cristian.inventory.dto.IncomeDto;
import com.cristian.inventory.entity.ExpenseEntity;
import com.cristian.inventory.entity.IncomeEntity;
import com.cristian.inventory.entity.ProfileEntity;
import com.cristian.inventory.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ProfileService profileService;

    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        ExpenseEntity newExpense = toEntity(expenseDto, profile);
        newExpense = expenseRepository.save(newExpense);

        return toDto(newExpense);
    }

    public List<ExpenseDto> getCurrentMonthExpensesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);

        return list.stream().map(this::toDto).toList();
    }

    //HELPER METHODS

    private ExpenseEntity toEntity(ExpenseDto expenseDto, ProfileEntity profile) {
        return ExpenseEntity.builder()
                .name(expenseDto.getName())
                .amount(expenseDto.getAmount())
                .date(expenseDto.getDate())
                .profile(profile)
                .build();
    }

    private ExpenseDto toDto(ExpenseEntity expense) {
        return ExpenseDto.builder()
                .id(expense.getId())
                .name(expense.getName())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }
}
