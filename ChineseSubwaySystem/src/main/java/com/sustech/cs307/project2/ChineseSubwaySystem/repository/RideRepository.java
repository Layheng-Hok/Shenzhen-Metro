package com.sustech.cs307.project2.ChineseSubwaySystem.repository;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.Ride;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RideRepository extends PagingAndSortingRepository<Ride, Long> {
}
