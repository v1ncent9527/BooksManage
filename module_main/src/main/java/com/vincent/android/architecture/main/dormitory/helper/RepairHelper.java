package com.vincent.android.architecture.main.dormitory.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vincent.android.architecture.main.dormitory.entity.RepairEntity;


public class RepairHelper {
    private SQLiteDatabase db;
    private DBHelper helper;

    public RepairHelper(Context context) {
        helper = new DBHelper(context);
        db = helper.getDb();
    }

    public void add(RepairEntity entity) {
        ContentValues values = new ContentValues();
        values.put("date", entity.getDate());
        values.put("repairContent", entity.getRepairContent());
        values.put("brokenDegree", entity.getBrokenDegree());
        values.put("repairDegree", entity.getRepairDegree());
        db.insert("repair", null, values);
    }

    public void update(RepairEntity entity) {
        ContentValues values = new ContentValues();
        values.put("date", entity.getDate());
        values.put("repairContent", entity.getRepairContent());
        values.put("brokenDegree", entity.getBrokenDegree());
        values.put("repairDegree", entity.getRepairDegree());
        db.update("repair", values, "id=?", new String[]{entity.getId() + ""});
    }

    @SuppressLint("Range")
    public RepairEntity get(RepairEntity entity) {
        Cursor cursor = db.rawQuery("select * from repair where id=?", new String[]{entity.getId() + ""});
        RepairEntity repairEntity = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String repairContent = cursor.getString(cursor.getColumnIndex("repairContent"));
            String brokenDegree = cursor.getString(cursor.getColumnIndex("brokenDegree"));
            String repairDegree = cursor.getString(cursor.getColumnIndex("repairDegree"));
            repairEntity = new RepairEntity(date, repairContent, brokenDegree, repairDegree);
            repairEntity.setId(id);
        }
        return repairEntity;
    }

    public void delete(RepairEntity entity) {
        db.delete("repair", "id=?", new String[]{entity.getId() + ""});
    }
}
