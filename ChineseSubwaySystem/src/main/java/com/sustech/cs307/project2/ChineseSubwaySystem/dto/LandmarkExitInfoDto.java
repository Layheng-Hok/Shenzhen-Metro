package com.sustech.cs307.project2.ChineseSubwaySystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LandmarkExitInfoDto {
    private String stationName;

    @NotEmpty(message = "Exit gate is required.")
    @Size(max = 100, message = "Exit gate must not exceed 100 characters.")
    private String exitGate;

    @NotEmpty(message = "Landmark is required.")
    @Size(max = 50, message = "Landmark must not exceed 50 characters.")
    private String landmark;
}

