package com.sustech.cs307.project2.ChineseSubwaySystem.repository;

import com.sustech.cs307.project2.ChineseSubwaySystem.object.LandmarkExitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandmarkExitInfoRepository extends JpaRepository<LandmarkExitInfo, Long> {
    List<LandmarkExitInfo> findByStationName(String stationName);
}