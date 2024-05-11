package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.sql.Date;
import java.sql.Time;

public class LineDto {
    @NotEmpty(message = "The name is required!")
    private String lineName;

    private Time startTime;

    private Time endTime;

    private String intro;

    @Min(0)
    private double mileage;

    private String color;

    private Date firstOpening;

    private String url;

    public @NotEmpty(message = "The name is required!") String getLineName() {
        return lineName;
    }

    public void setLineName(@NotEmpty(message = "The name is required!") String lineName) {
        this.lineName = lineName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Min(0)
    public double getMileage() {
        return mileage;
    }

    public void setMileage(@Min(0) double mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getFirstOpening() {
        return firstOpening;
    }

    public void setFirstOpening(Date firstOpening) {
        this.firstOpening = firstOpening;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
