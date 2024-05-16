package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "line_detail", uniqueConstraints = {@UniqueConstraint(columnNames = {"line_name", "station_name"})})
public class LineDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lineDetailId;

    @Column(name = "line_name", nullable = false, length = 5)
    private String lineName;

    @Column(name = "station_name", nullable = false, length = 50)
    private String stationName;

    @Column(name = "station_order", nullable = false)
    private int stationOrder;

    public LineDetail() {
    }

    public LineDetail(String lineName, String stationName, int stationOrder) {
        this.lineName = lineName;
        this.stationName = stationName;
        this.stationOrder = stationOrder;
    }

    public int getLineDetailId() {
        return lineDetailId;
    }

    public void setLineDetailId(int lineDetailId) {
        this.lineDetailId = lineDetailId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getStationOrder() {
        return stationOrder;
    }

    public void setStationOrder(int stationOrder) {
        this.stationOrder = stationOrder;
    }

    @Override
    public String toString() {
        return "LineDetail{" +
                "lineDetailId=" + lineDetailId +
                ", lineName='" + lineName + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationOrder=" + stationOrder +
                '}';
    }
}
