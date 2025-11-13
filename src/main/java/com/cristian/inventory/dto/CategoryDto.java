package com.cristian.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String name;
    private Long profileId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
