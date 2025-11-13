package com.cristian.inventory.repository;

import com.cristian.inventory.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


}
