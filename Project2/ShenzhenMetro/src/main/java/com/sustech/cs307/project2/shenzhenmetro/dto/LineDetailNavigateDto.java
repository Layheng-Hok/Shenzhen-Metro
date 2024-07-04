package com.sustech.cs307.project2.shenzhenmetro.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LineDetailNavigateDto {
    @NotEmpty(message = "Start station is required.")
    @Size(max = 50, message = "Station name must not exceed 50 characters.")
    private String startStation;

    @NotEmpty(message = "End station is required.")
    @Size(max = 50, message = "Station name must not exceed 50 characters.")
    private String endStation;
}
