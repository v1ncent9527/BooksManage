package com.vincent.android.architecture.main.dormitory.entity;

public class UserEntity {
    private int id;
    private String username;
    private String clazz;
    private String roomNo;

    public UserEntity(String username, String clazz, String roomNo) {
        this.username = username;
        this.clazz = clazz;
        this.roomNo = roomNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
}
