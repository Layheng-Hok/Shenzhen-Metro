package com.sustech.cs307.project2.ChineseSubwaySystem.object;

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
public class Station {
    @Id
    @Column(length = 50)
    private String englishName;

    @Column(length = 50, nullable = false, unique = true)
    private String chineseName;

    @Column(length = 50)
    private String district;

    @Column(columnDefinition = "TEXT")
    private String intro;

    @Column()
    private String status;
}