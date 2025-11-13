package com.cristian.inventory.repository;

import com.cristian.inventory.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Boolean existsByNameAndProfileId(String name, Long id);
}
