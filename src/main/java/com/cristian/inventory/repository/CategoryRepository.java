package com.cristian.inventory.repository;

import com.cristian.inventory.entity.CategoryEntity;
import com.cristian.inventory.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Boolean existsByNameAndProfileId(String name, Long id);
    List<CategoryEntity> findByProfileId(Long profileId);
    List<CategoryEntity> findByNameAndProfileId(String name, Long profileId);
    Optional<CategoryEntity> findByIdAndProfileId(Long categoryId, Long profileId);
}
