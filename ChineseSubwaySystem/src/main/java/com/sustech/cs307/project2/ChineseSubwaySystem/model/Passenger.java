package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Passenger {
    @Id
    @Column(length = 20)
    private String idNum;

    @Column(length = 50, nullable = false)
    private String name;

    @Column
    private long phoneNum;

    @Column
    private char gender;

    @Column(length = 20)
    private String district;
}
