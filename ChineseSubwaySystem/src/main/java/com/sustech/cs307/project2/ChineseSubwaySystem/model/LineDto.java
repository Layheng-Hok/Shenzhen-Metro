package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

@Data
@ToString
public class LineDto {
    @NotEmpty(message = "The name is required.")
    private String lineName;

    private Time startTime;

    private Time endTime;

    private String intro;

    @Min(0)
    private double mileage;

    private String color;

    private Date firstOpening;

    private String url;
}
