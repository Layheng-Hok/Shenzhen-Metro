package com.sustech.cs307.project2.ChineseSubwaySystem.repository;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.LineDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LineDetailRepository extends JpaRepository<LineDetail, Integer> {
    @Modifying
    @Query(value = "UPDATE LineDetail ld SET ld.stationOrder = ld.stationOrder - 1 WHERE ld.lineName = :lineName AND ld.stationOrder > :stationOrder")
    void updateStationOrderAfterDelete(String lineName, int stationOrder);

    @Modifying
    @Query(value = "UPDATE LineDetail ld SET ld.stationOrder = ld.stationOrder + 1 WHERE ld.lineName = :lineName AND ld.stationOrder >= :stationOrder")
    void updateStationBeforeCreate(String lineName, int stationOrder);

    @Query(value = "SELECT * FROM line_detail ORDER BY LPAD((line_name), 10, 0), station_order", nativeQuery = true)
    List<LineDetail> findAllOrderByLineNumberAndStationOrder();

    Optional<LineDetail> findByLineNameAndStationName(String lineName, String stationName);

    Optional<LineDetail> findByLineNameAndStationOrder(String lineName, int stationOrder);

    boolean existsByLineName(String lineName);
}
