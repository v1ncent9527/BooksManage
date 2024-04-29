package com.vincent.android.architecture.main.dormitory.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vincent.android.architecture.main.dormitory.entity.RoomEntity;


public class RoomHelper {
    private DBHelper helper;
    private SQLiteDatabase db;

    public RoomHelper(Context context) {
        helper = new DBHelper(context);
        db = helper.getDb();
    }

    public void add(RoomEntity entity) {
        ContentValues values = new ContentValues();
        values.put("roomNo", entity.getRoomNo() + "");
        values.put("date", entity.getDate() + "");
        values.put("username", entity.getUsername() + "");
        values.put("clazz", entity.getClazz() + "");
        values.put("ipAddress", entity.getIpAddress() + "");
        db.insert("room", null, values);
    }

    public void update(RoomEntity entity) {
        ContentValues values = new ContentValues();
        values.put("roomNo", entity.getRoomNo() + "");
        values.put("date", entity.getDate() + "");
        values.put("username", entity.getUsername() + "");
        values.put("clazz", entity.getClazz() + "");
        values.put("ipAddress", entity.getIpAddress() + "");
        db.update("room", values, "id=?", new String[]{entity.getId() + ""});

    }

    @SuppressLint("Range")
    public RoomEntity get(RoomEntity entity) {
        Cursor cursor = db.rawQuery("select * from room where id=?", new String[]{entity.getId() + ""});
        RoomEntity roomEntity = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String roomNo = cursor.getString(cursor.getColumnIndex("roomNo"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String clazz = cursor.getString(cursor.getColumnIndex("clazz"));
            String ipAddress = cursor.getString(cursor.getColumnIndex("ipAddress"));
            roomEntity = new RoomEntity(roomNo, date, username, clazz, ipAddress);
            roomEntity.setId(id);
        }
        return roomEntity;
    }

    public void delete(RoomEntity entity) {
        db.delete("room", "id=?", new String[]{entity.getId() + ""});
    }
}
