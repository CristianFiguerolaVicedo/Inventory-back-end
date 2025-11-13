package com.cristian.inventory.service;

import com.cristian.inventory.dto.ProductDto;
import com.cristian.inventory.entity.ProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProductService productService;
    private final ProfileService profileService;

    public List<ProductDto> getDashboardData() {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        return productService.getLatest5ProductsForCurrentUser();
    }
}
