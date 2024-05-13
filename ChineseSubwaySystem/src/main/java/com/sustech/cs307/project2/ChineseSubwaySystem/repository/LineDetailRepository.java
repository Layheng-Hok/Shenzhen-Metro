package com.sustech.cs307.project2.ChineseSubwaySystem.repository;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.LineDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;

//@Repository
public interface LineDetailRepository extends JpaRepository<LineDetail, Integer> {
    @Modifying
    @Query("UPDATE LineDetail ld SET ld.stationOrder = ld.stationOrder - 1 WHERE ld.lineName = :lineName AND ld.stationOrder > :stationOrder")
    void updateStationOrderAfterDeletion(String lineName, int stationOrder);
}
