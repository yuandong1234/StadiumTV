package com.kasai.stadium.tv.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;//数据库版本
    private static final String DB_NAME = "stadium.db";//数据库名称

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createVideo(db);
        createLocker(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    private void createVideo(SQLiteDatabase db) {
        String sql = "create table if not exists " + VideoDao.TABLE_VIDEO + "(" +
                "id Integer primary key autoincrement," +
                "name varchar," +
                "path varchar," +
                "status Integer);";
        db.execSQL(sql);
    }

    private void createLocker(SQLiteDatabase db) {
        String sql = "create table if not exists " + LockerDao.TABLE_LOCKER + "(" +
                "id Integer primary key autoincrement," +
                "date varchar," +
                "time Integer," +
                "userNumber Integer," +
                "unUserNumber Integer);";
        db.execSQL(sql);
    }
}
