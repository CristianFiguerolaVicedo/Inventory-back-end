package com.cristian.inventory.repository;

import com.cristian.inventory.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Boolean existsByNameAndProfileId(String name, Long id);
    List<CategoryEntity> findByProfileId(Long profileId);
    List<CategoryEntity> findByNameAndProfileId(String name, Long profileId);
}
