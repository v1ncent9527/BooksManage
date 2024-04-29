package com.vincent.android.architecture.main.dormitory.entity;

public class FingerEntity {
    private int id;
    private String roomNo;
    private String clazz;
    private String username;
    private byte[] data;

    public FingerEntity(String roomNo, String clazz, String username, byte[] data) {
        this.roomNo = roomNo;
        this.clazz = clazz;
        this.username = username;
        this.data = data;
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

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
