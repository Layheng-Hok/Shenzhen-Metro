package com.sustech.cs307.project2.ChineseSubwaySystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BusExitInfoDto {
    private String stationName;

    @NotEmpty(message = "Exit gate is required.")
    @Size(max = 100, message = "Exit gate must not exceed 100 characters.")
    private String exitGate;

    @NotEmpty(message = "Bus name is required.")
    @Size(max = 50, message = "Bus name must not exceed 50 characters.")
    private String busName;

    @NotEmpty(message = "Bus line is required.")
    @Size(max = 50, message = "Bus line must not exceed 50 characters.")
    private String busLine;
}
