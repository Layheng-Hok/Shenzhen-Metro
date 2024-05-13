package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Station {
    @Id
    @Column(length = 50)
    private String englishName;

    @Column(length = 50, nullable = false, unique = true)
    private String chineseName;

    @Column(length = 50)
    private String district;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String intro;

    public Station() {
    }

    public Station(String englishName, String chineseName, String distinct, String intro) {
        this.englishName = englishName;
        this.chineseName = chineseName;
        this.district = district;
        this.intro = intro;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        return "Station{" +
                "englishName='" + englishName + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", district='" + district + '\'' +
                ", intro='" + intro + '\'' +
                '}';
    }
}