package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rideId;

    @Column(nullable = false)
    private String userNum;

    @Column(length = 20, nullable = false)
    private String authType;

    @Column(nullable = false)
    private Timestamp startTime;

    @Column
    private Timestamp endTime;

    @Column(length = 50, nullable = false)
    private String startStation;

    @Column(length = 50)
    private String endStation;

    @Column(name = "class", length = 20, nullable = false)
    private String rideClass;

    @Column()
    private int price;
}
