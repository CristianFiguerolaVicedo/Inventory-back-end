package com.cristian.inventory.service;

import com.cristian.inventory.dto.SaleDetailDto;
import com.cristian.inventory.dto.SaleDto;
import com.cristian.inventory.entity.ProductEntity;
import com.cristian.inventory.entity.ProfileEntity;
import com.cristian.inventory.entity.SaleDetailEntity;
import com.cristian.inventory.entity.SaleEntity;
import com.cristian.inventory.repository.ProductRepository;
import com.cristian.inventory.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProfileService profileService;
    private final ProductRepository productRepository;

    public SaleDto addSale(SaleDto saleDto) {
        ProfileEntity profile = profileService.getCurrentProfile();

        SaleEntity sale = toEntity(saleDto, profile);

        if (sale.getDetails() == null || sale.getDetails().isEmpty()) {
            throw new RuntimeException("La venta debe tener al menos un producto");
        }

        float subtotal = 0f;

        for (SaleDetailEntity detail : sale.getDetails()) {
            float lineTotal = detail.getQuantity() * detail.getUnitPrice();
            subtotal += lineTotal;
        }

        float taxes = Boolean.TRUE.equals(sale.getIva()) ? subtotal * 0.21f : 0f;

        float packaging = sale.getPackaging() != null ? sale.getPackaging() : 0f;
        float sendingFees = sale.getSendingFees() != null ? sale.getSendingFees() : 0f;
        float productionFees = sale.getProductionFees() != null ? sale.getProductionFees() : 0f;

        float totalSale = subtotal + taxes;
        float totalNet = totalSale + packaging + sendingFees + productionFees;

        sale.setTaxes(taxes);
        sale.setTotalSale(totalSale);
        sale.setTotalNet(totalNet);

        SaleEntity savedSale = saleRepository.save(sale);

        return toDto(savedSale);
    }

    //HELPER METHODS

    private SaleEntity toEntity(SaleDto saleDto, ProfileEntity profile) {
         SaleEntity sale = SaleEntity.builder()
                .date(saleDto.getDate())
                .iva(saleDto.getIva())
                .sent(saleDto.getSent())
                .contract(saleDto.getContract())
                .packaging(saleDto.getPackaging())
                .sendingFees(saleDto.getSendingFees())
                .productionFees(saleDto.getProductionFees())
                .profile(profile)
                .build();

        List<SaleDetailEntity> details = saleDto.getDetails().stream()
                .map(detailDto -> {
                    ProductEntity product = productRepository.findById(detailDto.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    return SaleDetailEntity.builder()
                            .product(product)
                            .quantity(detailDto.getQuantity())
                            .unitPrice(detailDto.getUnitPrice())
                            .sale(sale)
                            .build();
                })
                .toList();

        sale.setDetails(details);

        return sale;
    }

    private SaleDto toDto(SaleEntity sale) {

        List<SaleDetailDto> details = sale.getDetails().stream()
                .map(detail -> SaleDetailDto.builder()
                        .productId(detail.getProduct().getId())
                        .quantity(detail.getQuantity())
                        .unitPrice(detail.getUnitPrice())
                        .build()
                )
                .toList();

        return SaleDto.builder()
                .date(sale.getDate())
                .iva(sale.getIva())
                .sent(sale.getSent())
                .contract(sale.getContract())
                .packaging(sale.getPackaging())
                .sendingFees(sale.getSendingFees())
                .productionFees(sale.getProductionFees())
                .details(details)
                .build();
    }

}
