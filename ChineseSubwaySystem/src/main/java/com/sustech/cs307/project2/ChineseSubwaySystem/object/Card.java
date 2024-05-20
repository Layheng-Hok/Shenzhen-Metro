package com.sustech.cs307.project2.ChineseSubwaySystem.object;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Card {
    @Id
    @Column(length = 10)
    private String code;

    @Column(nullable = false)
    private float money;

    @Column(nullable = false)
    private Timestamp createTime;
}
