package com.sustech.cs307.project2.shenzhenmetro.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

@Data
@ToString
public class LineDto {
    @NotEmpty(message = "The name is required.")
    @Size(max = 50, message = "Name must not exceed 50 characters.")
    private String lineName;

    private Time startTime;

    private Time endTime;

    @Size(max = 1000, message = "Intro must not exceed 1000 characters.")
    private String intro;

    private double mileage;

    @Size(max = 20, message = "Color must not exceed 20 characters.")
    private String color;

    private Date firstOpening;

    @Size(max = 300, message = "URL must not exceed 300 characters.")
    private String url;
}
