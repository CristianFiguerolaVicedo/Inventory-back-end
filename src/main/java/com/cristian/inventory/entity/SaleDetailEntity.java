package com.cristian.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_sale_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    private Float unitPrice;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private SaleEntity sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
