package com.cristian.inventory.repository;

import com.cristian.inventory.entity.ExpenseEntity;
import com.cristian.inventory.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findByProfileId(Long profileId);
    Optional<ExpenseEntity> findByIdAndProfileId(Long incomeId, Long profileId);
    List<ExpenseEntity> findTop5ByProfileIdOrderByUpdatedAtDesc(Long profileId);
}
