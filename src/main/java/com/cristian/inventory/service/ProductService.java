package com.cristian.inventory.service;

import com.cristian.inventory.dto.CategoryDto;
import com.cristian.inventory.dto.ProductDto;
import com.cristian.inventory.entity.CategoryEntity;
import com.cristian.inventory.entity.ProductEntity;
import com.cristian.inventory.entity.ProfileEntity;
import com.cristian.inventory.repository.CategoryRepository;
import com.cristian.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProfileService profileService;
    private final CategoryRepository categoryRepository;

    public ProductDto addProduct(ProductDto productDto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        ProductEntity newProduct = toEntity(productDto, profile, category);
        newProduct = productRepository.save(newProduct);

        return toDto(newProduct);
    }

    public List<ProductDto> getCurrentMonthProductsForCurrentUser() {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDateTime startDate = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDate = now.withDayOfMonth(now.lengthOfMonth()).atTime(23, 59, 59);
        List<ProductEntity> list = productRepository.findByProfileIdAndCreatedAtBetween(currentProfile.getId(), startDate, endDate);

        return list.stream().map(this::toDto).toList();
    }

    //HELPER METHODS

    private ProductEntity toEntity(ProductDto productDto, ProfileEntity profile, CategoryEntity category) {
        return ProductEntity.builder()
                .name(productDto.getName())
                .category(category)
                .productionPrice(productDto.getProductionPrice())
                .pvp(productDto.getPvp())
                .stock(productDto.getStock())
                .packaging(productDto.getPackaging())
                .status(productDto.getStatus())
                .notes(productDto.getNotes())
                .profile(profile)
                .build();
    }

    private ProductDto toDto(ProductEntity product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .productionPrice(product.getProductionPrice())
                .pvp(product.getPvp())
                .stock(product.getStock())
                .packaging(product.getPackaging())
                .status(product.getStatus())
                .notes(product.getNotes())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
