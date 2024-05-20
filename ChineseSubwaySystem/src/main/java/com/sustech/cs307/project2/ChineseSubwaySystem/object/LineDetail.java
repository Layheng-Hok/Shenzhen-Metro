package com.sustech.cs307.project2.ChineseSubwaySystem.object;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"line_name", "station_name"})})
public class LineDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lineDetailId;

    @Column(length = 5, nullable = false)
    private String lineName;

    @Column(length = 50, nullable = false)
    private String stationName;

    @Column(nullable = false)
    private int stationOrder;
}
