package com.vincent.android.architecture.main.dormitory.entity;

public class RoomEntity {
    private int id;
    private String roomNo;
    private String date;
    private String username;
    private String clazz;
    private String ipAddress;

    public RoomEntity() {
    }

    public RoomEntity(String roomNo, String date, String username, String clazz, String ipAddress) {
        this.roomNo = roomNo;
        this.date = date;
        this.username = username;
        this.clazz = clazz;
        this.ipAddress = ipAddress;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}

