package com.sustech.cs307.project2.shenzhenmetro.repository;

import com.sustech.cs307.project2.shenzhenmetro.object.OngoingRide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OngoingRideRepository extends JpaRepository<OngoingRide, Long> {
}
