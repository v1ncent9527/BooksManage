package com.vincent.android.architecture.main.dormitory.entity;

public class ChamberClockEntity {
    private String bed;
    private String name;

    public ChamberClockEntity() {
    }

    public ChamberClockEntity(String bed, String name) {
        this.bed = bed;
        this.name = name;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

