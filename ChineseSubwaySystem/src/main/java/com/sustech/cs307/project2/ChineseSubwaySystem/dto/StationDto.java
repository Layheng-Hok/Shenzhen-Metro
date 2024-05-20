package com.sustech.cs307.project2.ChineseSubwaySystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StationDto {
    @NotEmpty(message = "English Name is required.")
    @Size(max = 50, message = "English name must not exceed 50 characters.")
    private String englishName;

    @NotEmpty(message = "Chinese name is required.")
    @Size(max = 50, message = "Chinese name must not exceed 50 characters.")
    @Valid()
    private String chineseName;

    @Size(max = 50, message = "District name must not exceed 50 characters.")
    private String district;

    @Size(max = 1000, message = "Intro must not exceed 1000 characters.")
    private String intro;

    @NotEmpty(message = "Status is required.")
    private String status;
}