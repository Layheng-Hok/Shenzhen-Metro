package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Line {
    @Id
    private int lineId;

    @Column(unique = true, nullable = false)
    private String lineName;

    @Column(nullable = false)
    private Time startTime;

    @Column(nullable = false)
    private Time endTime;

    @Column(columnDefinition = "TEXT")
    private String intro;

    @Column
    private double mileage;

    @Column
    private String color;

    @Column
    private Date firstOpening;

    @Column
    private String url;

    public Line() {

    }

    public Line(String lineName, Time startTime, Time endTime, String intro, double mileage, String color, Date firstOpening, String url) {
        this.lineName = lineName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intro = intro;
        this.mileage = mileage;
        this.color = color;
        this.firstOpening = firstOpening;
        this.url = url;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
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

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
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

    @Override
    public String toString() {
        return "Line{" +
                "lineId=" + lineId +
                ", lineName='" + lineName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", intro='" + intro + '\'' +
                ", mileage=" + mileage +
                ", color='" + color + '\'' +
                ", firstOpening=" + firstOpening +
                ", url='" + url + '\'' +
                '}';
    }
}
