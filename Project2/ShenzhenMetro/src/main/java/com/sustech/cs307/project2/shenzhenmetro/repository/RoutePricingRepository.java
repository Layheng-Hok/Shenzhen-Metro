package com.sustech.cs307.project2.shenzhenmetro.repository;

import com.sustech.cs307.project2.shenzhenmetro.object.RoutePricing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutePricingRepository extends JpaRepository<RoutePricing, Integer> {
    RoutePricing findByStartStationAndEndStation(String startStation, String endStation);
}
