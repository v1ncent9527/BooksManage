package com.vincent.android.architecture.main.dormitory.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vincent.android.architecture.main.dormitory.entity.AirEntity;


public class AirHelper {
    private DBHelper helper;
    private SQLiteDatabase db;

    public AirHelper(Context context) {
        helper = new DBHelper(context);
        db = helper.getDb();
    }

    public void insert(AirEntity entity) {
        ContentValues values = new ContentValues();
        values.put("roomNo", entity.getRoomNo());
        values.put("date", entity.getDate());
        values.put("temperature", entity.getTemperature());
        values.put("wet", entity.getWet());
        values.put("CO2", entity.getCO2());
        values.put("PPM25", entity.getPPM25());
        db.insert("air", null, values);
    }

    public void update(AirEntity entity) {
        ContentValues values = new ContentValues();
        values.put("roomNo", entity.getRoomNo());
        values.put("date", entity.getDate());
        values.put("temperature", entity.getTemperature());
        values.put("wet", entity.getWet());
        values.put("CO2", entity.getCO2());
        values.put("PPM25", entity.getPPM25());
        db.update("air", values, "id=?", new String[]{entity.getId() + ""});
    }

    @SuppressLint("Range")
    public AirEntity get(AirEntity entity) {
        Cursor cursor = db.rawQuery("select * from air where id=?", new String[]{entity.getId() + ""});
        AirEntity airEntity = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String roomNo = cursor.getString(cursor.getColumnIndex("roomNo"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            float temperature = cursor.getFloat(cursor.getColumnIndex("temperature"));
            float wet = cursor.getFloat(cursor.getColumnIndex("wet"));
            float CO2 = cursor.getFloat(cursor.getColumnIndex("CO2"));
            float PPM25 = cursor.getFloat(cursor.getColumnIndex("PPM25"));
            airEntity = new AirEntity(roomNo, date, temperature, wet, CO2, PPM25);
        }
        return airEntity;
    }

    public void delete(AirEntity entity) {
        db.delete("air", "id=?", new String[]{entity.getId() + ""});
    }
}
