package com.sustech.cs307.project2.shenzhenmetro.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class RideDto {
    @NotEmpty(message = "User number is required.")
    private String userNum;

    private String authType;

    private Timestamp startTime;

    private Timestamp endTime;

    private long duration;

    @NotEmpty(message = "Start station is required.")
    private String startStation;

    private String endStation;

    @NotEmpty(message = "Ride class is required.")
    private String rideClass;

    private float price;
}
