package com.sustech.cs307.project2.shenzhenmetro.repository;

import com.sustech.cs307.project2.shenzhenmetro.object.LandmarkExitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LandmarkExitInfoRepository extends JpaRepository<LandmarkExitInfo, Long> {
    List<LandmarkExitInfo> findByStationName(String stationName);

    Optional<Object> findByStationNameAndExitGateAndLandmark(String stationName, String exitGate, String landmark);
}