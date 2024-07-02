package com.sustech.cs307.project2.shenzhenmetro.repository;

import com.sustech.cs307.project2.shenzhenmetro.object.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RideRepository extends JpaRepository<Ride, Long>,PagingAndSortingRepository<Ride, Long>, JpaSpecificationExecutor<Ride> {
}
