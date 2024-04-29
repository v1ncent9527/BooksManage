package com.vincent.android.architecture.main.dormitory.entity;

public class ElectricEntity {
    private int id;
    private String roomNo;
    private String date;
    private String battery;
    private float electricUse;
    private String amount;

    public ElectricEntity() {
    }

    public ElectricEntity(String date, String battery, String amount) {
        this.date = date;
        this.battery = battery;
        this.amount = amount;
    }

    public ElectricEntity(String roomNo, String date, String battery, float electricUse, String amount) {
        this.roomNo = roomNo;
        this.date = date;
        this.battery = battery;
        this.electricUse = electricUse;
        this.amount = amount;
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

    public float getElectricUse() {
        return electricUse;
    }

    public void setElectricUse(float electricUse) {
        this.electricUse = electricUse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
