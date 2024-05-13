package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class StationDto {
    @NotEmpty(message = "English Name is required!")
    @Size(max = 50, message = "English name must not exceed 50 characters.")
    private String englishName;

    @NotEmpty(message = "Chinese name is required!")
    @Size(max = 50, message = "Chinese name must not exceed 50 characters.")
    @Valid()
    private String chineseName;

    @Size(max = 50, message = "District name must not exceed 50 characters.")
    private String district;

    @Size(max = 1000, message = "Intro must not exceed 1000 characters.")
    private String intro;

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
}