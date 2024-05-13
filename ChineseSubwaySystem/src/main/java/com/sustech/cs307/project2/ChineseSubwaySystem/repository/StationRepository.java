package com.sustech.cs307.project2.ChineseSubwaySystem.repository;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, String> {
}