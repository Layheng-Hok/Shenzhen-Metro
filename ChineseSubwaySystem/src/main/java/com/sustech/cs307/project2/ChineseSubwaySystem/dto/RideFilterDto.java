package com.sustech.cs307.project2.ChineseSubwaySystem.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RideFilterDto {
    private String userNum;

    private Long duration;

    private String startStation;

    private String endStation;

    private String rideClass;

    private Float price;
}
