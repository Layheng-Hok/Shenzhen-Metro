package com.sustech.cs307.project2.ChineseSubwaySystem.repository;

import com.sustech.cs307.project2.ChineseSubwaySystem.object.BusExitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusExitInfoRepository extends JpaRepository<BusExitInfo, Long> {
    List<BusExitInfo> findByStationName(String stationName);

    Optional<Object> findByStationNameAndExitGateAndBusNameAndBusLine(String stationName, String exitGate, String busName, String busLine);
}