package com.cristian.inventory.service;

import com.cristian.inventory.dto.IncomeDto;
import com.cristian.inventory.entity.IncomeEntity;
import com.cristian.inventory.entity.ProfileEntity;
import com.cristian.inventory.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final ProfileService profileService;

    public IncomeDto addIncome(IncomeDto incomeDto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        IncomeEntity newIncome = toEntity(incomeDto, profile);
        newIncome = incomeRepository.save(newIncome);

        return toDto(newIncome);
    }

    public List<IncomeDto> getCurrentMonthIncomesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<IncomeEntity> list = incomeRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);

        return list.stream().map(this::toDto).toList();
    }

    //HELPER METHODS

    private IncomeEntity toEntity(IncomeDto incomeDto, ProfileEntity profile) {
        return IncomeEntity.builder()
                .name(incomeDto.getName())
                .amount(incomeDto.getAmount())
                .date(incomeDto.getDate())
                .profile(profile)
                .build();
    }

    private IncomeDto toDto(IncomeEntity income) {
        return IncomeDto.builder()
                .id(income.getId())
                .name(income.getName())
                .amount(income.getAmount())
                .date(income.getDate())
                .createdAt(income.getCreatedAt())
                .updatedAt(income.getUpdatedAt())
                .build();
    }
}
