package com.vincent.android.architecture.main.dormitory.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vincent.android.architecture.main.dormitory.entity.ElectricEntity;


public class ElectricHelper {
    private SQLiteDatabase db;
    private DBHelper helper;

    public ElectricHelper(Context context) {
        helper = new DBHelper(context);
        db = helper.getDb();
    }

    public void add(ElectricEntity entity) {
        ContentValues values = new ContentValues();
        values.put("roomNo", entity.getRoomNo());
        values.put("date", entity.getDate());
        values.put("money", entity.getAmount());
        db.insert("electric", null, values);
    }

    public void update(ElectricEntity entity) {
        ContentValues values = new ContentValues();
        values.put("roomNo", entity.getRoomNo());
        values.put("date", entity.getDate());
        values.put("money", entity.getAmount());
        db.update("electric", values, "id=?", new String[]{entity.getId() + ""});
    }

    @SuppressLint("Range")
    public ElectricEntity get(ElectricEntity entity) {
        Cursor cursor = db.rawQuery("select * from electric where id=?", new String[]{entity.getId() + ""});
        ElectricEntity electricEntity = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String roomNo = cursor.getString(cursor.getColumnIndex("roomNo"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            float electricUse = cursor.getFloat(cursor.getColumnIndex("electricUse"));
            electricEntity = new ElectricEntity();
            electricEntity.setId(id);
            electricEntity.setRoomNo(roomNo);
            electricEntity.setDate(date);
            electricEntity.setElectricUse(electricUse);
        }
        return electricEntity;
    }

    public void delete(ElectricEntity entity) {
        db.delete("electric", "id=?", new String[]{entity.getId() + ""});
    }
}
