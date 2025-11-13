package com.cristian.inventory.service;

import com.cristian.inventory.dto.CategoryDto;
import com.cristian.inventory.entity.CategoryEntity;
import com.cristian.inventory.entity.ProfileEntity;
import com.cristian.inventory.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        if (categoryRepository.existsByNameAndProfileId(categoryDto.getName(), profile.getId())) {
            throw new RuntimeException("Category with this name already exists");
        }

        CategoryEntity newCategory = toEntity(categoryDto, profile);
        newCategory = categoryRepository.save(newCategory);

        return toDto(newCategory);
    }

    //HELPER METHODS

    private CategoryEntity toEntity(CategoryDto categoryDto, ProfileEntity profile) {
        return CategoryEntity.builder()
                .name(categoryDto.getName())
                .profile(profile)
                .build();
    }

    private CategoryDto toDto(CategoryEntity category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .profileId(category.getProfile() != null ? category.getProfile().getId() : null)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
