package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.validation.constraints.*;

public class LineDetailDto {
    @NotEmpty(message = "Line name is required!")
    @Size(max = 5, message = "Line name must not exceed 5 characters.")
    private String lineName;

    @NotEmpty(message = "Station name is required!")
    @Size(max = 50, message = "Line name must not exceed 50 characters.")
    private String stationName;

    @NotNull(message = "Station order is required!")
    @Min(value = 1, message = "Station order must be at least 1.")
    private int stationOrder;

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

}