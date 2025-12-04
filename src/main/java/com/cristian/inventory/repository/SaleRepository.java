package com.cristian.inventory.repository;

import com.cristian.inventory.entity.SaleEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<SaleEntity, Long> {

    List<SaleEntity> findByProfileIdAndCreatedAtBetween(Long profileId, LocalDateTime startDate, LocalDateTime endDate);
    Optional<SaleEntity> findByIdAndProfileId(Long saleId, Long profileId);
    List<SaleEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);

    @Query("""
    SELECT DISTINCT s FROM SaleEntity s
    JOIN s.details d
    JOIN d.product p
    WHERE s.profile.id = :profileId
      AND s.date BETWEEN :startDate AND :endDate
      AND ( :sent IS NULL OR s.sent = :sent )
      AND ( :iva IS NULL OR s.iva = :iva )
      AND ( :contract IS NULL OR s.contract = :contract )
      AND (
            :keyword = '' OR
            LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
    """)
    List<SaleEntity> filterSales(Long profileId, LocalDateTime startDate, LocalDateTime endDate, Boolean iva, Boolean contract, Boolean sent, String keyword, Sort sort);
}
