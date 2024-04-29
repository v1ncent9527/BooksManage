package com.vincent.android.architecture.main.dormitory.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, "data", null, 1);
        db = getWritableDatabase();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS user (id integer primary key autoincrement," +
                "username text," +
                "clazz text," +
                "roomNo text);";
        String sql2 = "CREATE TABLE IF NOT EXISTS room (id integer primary key autoincrement," +
                "roomNo text," +
                "date text," +
                "username text," +
                "clazz text," +
                "ipAddress text);";
        String sql3 = "CREATE TABLE IF NOT EXISTS finger(id integer primary key autoincrement," +
                "roomNo text," +
                "clazz text," +
                "username text," +
                "data blob);";
        String sql4 = "CREATE TABLE IF NOT EXISTS air(id integer primary key autoincrement," +
                "roomNo text," +
                "date text," +
                "temperature float," +
                "wet float," +
                "CO2 float," +
                "PPM25 float);";
        String sql5 = "CREATE TABLE IF NOT EXISTS money(id integer primary key autoincrement," +
                "roomNo text," +
                "date text," +
                "money float);";
        String sql6 = "CREATE TABLE IF NOT EXISTS electric(id integer primary key autoincrement," +
                "roomNo text," +
                "date text," +
                "electricUse float);";
        String sql7 = "CREATE TABLE IF NOT EXISTS repair(id integer primary key autoincrement," +
                "date text," +
                "repairContent text," +
                "brokenDegree text," +
                "repairDegree text);";
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
