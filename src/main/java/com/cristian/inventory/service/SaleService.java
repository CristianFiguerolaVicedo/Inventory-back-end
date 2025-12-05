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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            throw new RuntimeException("A sale must have at least one product");
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
        sale.setDate(LocalDate.now());

        SaleEntity savedSale = saleRepository.save(sale);

        return toDto(savedSale);
    }

    public List<SaleDto> getCurrentMonthSalesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();

        LocalDate now = LocalDate.now();
        LocalDateTime startDate = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDate = now.withDayOfMonth(now.lengthOfMonth()).atTime(23, 59, 59);

        List<SaleEntity> sales = saleRepository.findByProfileIdAndCreatedAtBetween(profile.getId(), startDate, endDate);

        return sales.stream()
                .map(this::toDto)
                .toList();
    }

    public void deleteSale(Long id) {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        SaleEntity sale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        if (!sale.getProfile().getId().equals(currentProfile.getId())) {
            throw new RuntimeException("Unauthorized to delete this sale");
        }

        saleRepository.delete(sale);
    }

    public SaleDto updateSale (Long saleId, SaleDto saleDto) {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        SaleEntity sale = saleRepository.findByIdAndProfileId(saleId, currentProfile.getId())
                .orElseThrow(() -> new RuntimeException("Sale not found or not accessible"));

        sale.setDate(LocalDate.now());
        sale.setIva(saleDto.getIva());
        sale.setSent(saleDto.getSent());
        sale.setContract(saleDto.getContract());
        sale.setPackaging(saleDto.getPackaging());
        sale.setSendingFees(saleDto.getSendingFees());
        sale.setProductionFees(saleDto.getProductionFees());
        sale = saleRepository.save(sale);

        return toDto(sale);
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
                            .unitPrice(product.getPvp())
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
                        .build()
                )
                .toList();

        return SaleDto.builder()
                .id(sale.getId())
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
