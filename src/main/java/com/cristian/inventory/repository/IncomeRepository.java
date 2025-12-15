package com.cristian.inventory.repository;

import com.cristian.inventory.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    List<IncomeEntity> findByProfileId(Long profileId);
    Optional<IncomeEntity> findByIdAndProfileId(Long incomeId, Long profileId);
    List<IncomeEntity> findTop5ByProfileIdOrderByUpdatedAtDesc(Long profileId);
}
