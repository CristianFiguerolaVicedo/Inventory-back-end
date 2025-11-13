package com.cristian.inventory.service;

import com.cristian.inventory.dto.CategoryDto;
import com.cristian.inventory.entity.CategoryEntity;
import com.cristian.inventory.entity.ProfileEntity;
import com.cristian.inventory.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<CategoryDto> getCategoriesForCurrentUser() {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        List<CategoryEntity> categories = categoryRepository.findByProfileId(currentProfile.getId());

        return categories.stream().map(this::toDto).toList();
    }

    public List<CategoryDto> getCategoriesByNameForCurrentUser(String name) {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        List<CategoryEntity> categories = categoryRepository.findByNameAndProfileId(name, currentProfile.getId());

        return categories.stream().map(this::toDto).toList();
    }

    public void deleteCategory(Long id) {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getProfile().getId().equals(currentProfile.getId())) {
            throw new RuntimeException("Unauthorized to delete this category");
        }

        categoryRepository.delete(category);
    }

    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findByIdAndProfileId(categoryId, currentProfile.getId())
                .orElseThrow(() -> new RuntimeException("Category not found or not accessible"));

        category.setName(categoryDto.getName());
        category = categoryRepository.save(category);

        return toDto(category);
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
