package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity

public class LandmarkExitInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long landmarkExitInfoId;

    @Column(length = 50, nullable = false, unique = true)
    private String stationName;

    @Column(length = 100, nullable = false, unique = true)
    private String exitGate;

    @Column(length = 50, nullable = false, unique = true)
    private String landmark;
}
