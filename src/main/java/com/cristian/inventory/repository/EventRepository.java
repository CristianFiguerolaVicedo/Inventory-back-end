package com.cristian.inventory.repository;

import com.cristian.inventory.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByIdAndProfileId(Long eventId, Long profileId);
}
