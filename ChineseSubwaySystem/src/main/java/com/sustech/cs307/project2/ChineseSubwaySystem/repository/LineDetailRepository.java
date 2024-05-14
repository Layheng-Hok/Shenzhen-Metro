package com.sustech.cs307.project2.ChineseSubwaySystem.repository;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.LineDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LineDetailRepository extends JpaRepository<LineDetail, Integer> {
    @Modifying
    @Query("UPDATE LineDetail ld SET ld.stationOrder = ld.stationOrder - 1 WHERE ld.lineName = :lineName AND ld.stationOrder > :stationOrder") //JPQL
    void updateStationOrderAfterDeletion(String lineName, int stationOrder);

    @Modifying
    @Query("UPDATE LineDetail ld SET ld.stationOrder = ld.stationOrder + 1 WHERE ld.lineName = :lineName AND ld.stationOrder >= :stationOrder")
    void updateStationOrderBeforeCreate(String lineName, int stationOrder);

    @Query(value = "SELECT * FROM line_detail ld ORDER BY CAST(REGEXP_REPLACE(ld.line_name, '\\D+', '') AS UNSIGNED), ld.station_order ASC", nativeQuery = true) //SQL
    List<LineDetail> findAllOrderedByLineNumberAndStationOrder();

    Optional<LineDetail> findByLineNameAndStationName(String lineName, String stationName);
}
