package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
}
