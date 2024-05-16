package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class RideDto {
    @NotEmpty(message = "User number is required!")
    private String userNum;

    @NotEmpty(message = "Authentication type is required!")
    private String authType;

    @NotEmpty(message = "Start time is required!")
    private Timestamp startTime;

    private Timestamp endTime;

    @NotEmpty(message = "Start station is required!")
    private String startStation;

    private String endStation;

    @NotEmpty(message = "Ride class is required!")
    private String rideClass;

    @NotEmpty(message = "Price is required!")
    private int price;
}
