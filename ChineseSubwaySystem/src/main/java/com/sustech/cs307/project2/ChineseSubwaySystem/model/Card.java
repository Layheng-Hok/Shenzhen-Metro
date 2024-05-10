package com.sustech.cs307.project2.ChineseSubwaySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Card {
    @Id
    private String code;
    
    @Column
    private double money;
    
    @Column
    private Timestamp createTime;

    public Card() {
    }

    public Card(String code, double money, Timestamp createTime) {
        this.code = code;
        this.money = money;
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Card{" +
                "code='" + code + '\'' +
                ", money=" + money +
                ", createTime=" + createTime +
                '}';
    }
}
