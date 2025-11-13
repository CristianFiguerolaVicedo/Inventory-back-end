package com.cristian.inventory.repository;

import com.cristian.inventory.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByProfileIdAndCreatedAtBetween(Long profileId, LocalDateTime startDate, LocalDateTime endDate);
    Optional<ProductEntity> findByIdAndProfileId(Long productId, Long profileId);
}
