package com.sustech.cs307.project2.shenzhenmetro.object;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class RoutePricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pricingId;

    @Column(length = 50, nullable = false)
    private String startStation;

    @Column(length = 50, nullable = false)
    private String endStation;

    @Column(nullable = false)
    private float price;
}
