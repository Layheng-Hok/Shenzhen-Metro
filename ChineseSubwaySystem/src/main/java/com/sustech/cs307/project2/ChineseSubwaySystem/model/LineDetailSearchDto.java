package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LineDetailSearchDto {
    @NotEmpty(message = "Line name is required!")
    @Size(max = 5, message = "Line name must not exceed 5 characters.")
    private String lineName;

    @NotEmpty(message = "Station name is required!")
    @Size(max = 50, message = "Line name must not exceed 50 characters.")
    private String stationName;

    @NotNull(message = "Offset is required!")
    private int offset;

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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}