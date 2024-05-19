package com.sustech.cs307.project2.ChineseSubwaySystem.repository;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.BusExitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusExitInfoRepository extends JpaRepository<BusExitInfo, Long> {
    List<BusExitInfo> findByStationName(String stationName);
}