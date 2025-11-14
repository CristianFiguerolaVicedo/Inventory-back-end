package com.cristian.inventory.repository;

import com.cristian.inventory.entity.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByProfileIdAndCreatedAtBetween(Long profileId, LocalDateTime startDate, LocalDateTime endDate);
    Optional<ProductEntity> findByIdAndProfileId(Long productId, Long profileId);
    List<ProductEntity> findTop5ByProfileIdOrderByUpdatedAtDesc(Long profileId);
    @Query("""
    SELECT p FROM ProductEntity p
    WHERE p.profile.id = :profileId
      AND p.updatedAt BETWEEN :startDate AND :endDate
      AND (
            LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(p.category.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(p.status) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(p.notes) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
""")
    List<ProductEntity> filterProducts(
            Long profileId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String keyword,
            Sort sort
    );

}
