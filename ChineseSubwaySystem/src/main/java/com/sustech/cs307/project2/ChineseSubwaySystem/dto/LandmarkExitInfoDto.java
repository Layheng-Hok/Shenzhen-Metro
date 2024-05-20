package com.sustech.cs307.project2.ChineseSubwaySystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LandmarkExitInfoDto {
    private String stationName;

    @NotEmpty(message = "The exit gate is required.")
    private String exitGate;

    @NotEmpty(message = "The landmark is required.")
    private String landmark;
}
