package com.vincent.android.architecture.main.dormitory.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vincent.android.architecture.main.dormitory.entity.UserEntity;


public class UserHelper {
    private DBHelper helper;
    private SQLiteDatabase db;

    public UserHelper(Context context) {
        helper = new DBHelper(context);
        db = helper.getDb();
    }

    public void add(UserEntity entity) {
        ContentValues values = new ContentValues();
        values.put("username", entity.getUsername() + "");
        values.put("clazz", entity.getClazz() + "");
        values.put("roomNo", entity.getRoomNo() + "");
        db.insert("user", null, values);
    }

    public void update(UserEntity entity) {
        ContentValues values = new ContentValues();
        values.put("username", entity.getUsername() + "");
        values.put("clazz", entity.getClazz() + "");
        values.put("roomNo", entity.getRoomNo() + "");
        db.update("user", values, "id=?", new String[]{entity.getId() + ""});
    }

    @SuppressLint("Range")
    public UserEntity get(UserEntity entity) {
        Cursor cursor = db.rawQuery("select * from user where id=?", new String[]{"" + entity.getId()});
        UserEntity user = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String clazz = cursor.getString(cursor.getColumnIndex("clazz"));
            String roomNo = cursor.getString(cursor.getColumnIndex("roomNo"));
            user = new UserEntity(username, clazz, roomNo);
            user.setId(id);
        }
        return user;
    }

    public void delete(UserEntity entity) {
        db.delete("user", "id=?", new String[]{entity.getId() + ""});
    }
}
