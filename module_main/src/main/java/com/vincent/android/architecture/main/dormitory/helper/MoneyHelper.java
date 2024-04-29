package com.vincent.android.architecture.main.dormitory.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vincent.android.architecture.main.dormitory.entity.MoneyEntity;


public class MoneyHelper {
    private DBHelper helper;
    private SQLiteDatabase db;

    public MoneyHelper(Context context) {
        helper = new DBHelper(context);
        db = helper.getDb();
    }

    public void insert(MoneyEntity entity) {
        ContentValues values = new ContentValues();
        values.put("roomNo", entity.getRoomNo());
        values.put("date", entity.getDate());
        values.put("money", entity.getMoney());
        db.insert("money", null, values);
    }

    public void update(MoneyEntity entity) {
        ContentValues values = new ContentValues();
        values.put("roomNo", entity.getRoomNo());
        values.put("date", entity.getDate());
        values.put("money", entity.getMoney());
        db.update("money", values, "id=?", new String[]{entity.getId() + ""});
    }

    @SuppressLint("Range")
    public MoneyEntity get(MoneyEntity entity) {
        Cursor cursor = db.rawQuery("select * from money where id=?", new String[]{entity.getId() + ""});
        MoneyEntity moneyEntity = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String roomNo = cursor.getString(cursor.getColumnIndex("roomNo"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            moneyEntity = new MoneyEntity(roomNo, date, money);
            moneyEntity.setId(id);
        }
        return moneyEntity;
    }

    public void delete(MoneyEntity entity) {
        db.delete("money", "id=?", new String[]{entity.getId() + ""});
    }
}
