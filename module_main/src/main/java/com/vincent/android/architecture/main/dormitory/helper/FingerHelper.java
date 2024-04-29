package com.vincent.android.architecture.main.dormitory.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vincent.android.architecture.main.dormitory.entity.FingerEntity;


public class FingerHelper {
    private DBHelper helper;
    private SQLiteDatabase db;

    public FingerHelper(Context context) {
        helper = new DBHelper(context);
        db = helper.getDb();
    }

    public void add(FingerEntity entity) {
        ContentValues values = new ContentValues();
        values.put("roomNo", entity.getRoomNo());
        values.put("clazz", entity.getClazz());
        values.put("username", entity.getUsername());
        values.put("data", entity.getData());
        db.insert("finger", null, values);
    }

    public void update(FingerEntity entity) {
        ContentValues values = new ContentValues();
        values.put("roomNo", entity.getRoomNo());
        values.put("clazz", entity.getClazz());
        values.put("username", entity.getUsername());
        values.put("data", entity.getData());
        db.update("finger", values, "id=?", new String[]{entity.getId() + ""});
    }

    @SuppressLint("Range")
    public FingerEntity get(FingerEntity entity) {
        Cursor cursor = db.rawQuery("select * from finger where id=?", new String[]{entity.getId() + ""});
        FingerEntity fingerEntity = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String roomNo = cursor.getString(cursor.getColumnIndex("roomNo"));
            String clazz = cursor.getString(cursor.getColumnIndex("clazz"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            byte[] data = cursor.getBlob(cursor.getColumnIndex("data"));
            fingerEntity = new FingerEntity(roomNo, clazz, username, data);
            fingerEntity.setId(id);
        }
        return fingerEntity;
    }

    public void delete(FingerEntity entity) {
        db.delete("finger", "id=?", new String[]{entity.getId() + ""});
    }


}
