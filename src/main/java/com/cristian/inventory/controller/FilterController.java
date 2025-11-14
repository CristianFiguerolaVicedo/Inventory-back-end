package com.cristian.inventory.controller;

import com.cristian.inventory.dto.FilterDto;
import com.cristian.inventory.dto.ProductDto;
import com.cristian.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> filterProducts(@RequestBody FilterDto filterDto) {
        LocalDateTime startDate = filterDto.getStartDate() != null ? filterDto.getStartDate() : LocalDateTime.MIN;
        LocalDateTime endDate = filterDto.getEndDate() != null ? filterDto.getEndDate() : LocalDateTime.now();
        String keyword = filterDto.getKeyword() != null ? filterDto.getKeyword() : "";
        String sortField = filterDto.getSortField() != null ? filterDto.getSortField() : "createdAt";
        Sort.Direction direction = "desc".equalsIgnoreCase(filterDto.getSortOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        List<ProductDto> filteredProducts = productService.filterProducts(filterDto.getCategoryId(), startDate, endDate, keyword, sort);
        return ResponseEntity.ok(filteredProducts);
    }
}
