package com.sustech.cs307.project2.ChineseSubwaySystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LineDetailSearchDto {
    @NotEmpty(message = "Line name is required.")
    @Size(max = 50, message = "Line name must not exceed 50 characters.")
    private String lineName;

    @NotEmpty(message = "Station name is required.")
    @Size(max = 50, message = "Station name must not exceed 50 characters.")
    private String stationName;

    @NotNull(message = "Offset is required.")
    private int offset;
}