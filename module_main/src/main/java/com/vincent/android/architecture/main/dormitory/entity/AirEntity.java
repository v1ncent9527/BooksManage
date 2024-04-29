package com.vincent.android.architecture.main.dormitory.entity;

public class AirEntity {
    private int id;
    private String roomNo;
    private String date;
    private float temperature;
    private float wet;
    private float CO2;
    private float PPM25;

    public AirEntity(String roomNo, String date, float temperature, float wet, float CO2, float PPM25) {
        this.roomNo = roomNo;
        this.date = date;
        this.temperature = temperature;
        this.wet = wet;
        this.CO2 = CO2;
        this.PPM25 = PPM25;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getWet() {
        return wet;
    }

    public void setWet(float wet) {
        this.wet = wet;
    }

    public float getCO2() {
        return CO2;
    }

    public void setCO2(float CO2) {
        this.CO2 = CO2;
    }

    public float getPPM25() {
        return PPM25;
    }

    public void setPPM25(float PPM25) {
        this.PPM25 = PPM25;
    }
}
