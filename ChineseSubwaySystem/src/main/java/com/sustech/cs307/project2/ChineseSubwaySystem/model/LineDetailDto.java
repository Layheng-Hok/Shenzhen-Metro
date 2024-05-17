package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LineDetailDto {
    @NotEmpty(message = "Line name is required.")
    @Size(max = 5, message = "Line name must not exceed 5 characters.")
    private String lineName;

    @NotEmpty(message = "Station name is required.")
    @Size(max = 50, message = "Line name must not exceed 50 characters.")
    private String stationName;

    @NotNull(message = "Station order is required.")
    @Min(value = 1, message = "Station order must be at least 1.")
    private int stationOrder;
}