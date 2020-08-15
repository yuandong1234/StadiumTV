package com.kasai.stadium.tv.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kasai.stadium.tv.dao.bean.LockerBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LockerDao {
    public final static String TAG = LockerDao.class.getSimpleName();
    public static final String TABLE_LOCKER = "t_locker";//表的名称
    private AtomicInteger count = new AtomicInteger();
    private static DBHelper dbHelper;
    private static LockerDao dao;
    private SQLiteDatabase database;

    private LockerDao(Context context) {
        dbHelper = new DBHelper(context);
    }


    public static LockerDao getInstance(Context context) {
        if (null == dao) {
            synchronized (LockerDao.class) {
                if (null == dao) {
                    dao = new LockerDao(context.getApplicationContext());
                }
            }
        }
        return dao;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (count.incrementAndGet() == 1) {
            database = dbHelper.getWritableDatabase();
        }
        return database;
    }

    public synchronized void closeDatabase() {
        if (count.decrementAndGet() == 0) {
            database.close();
        }
    }


    public void saveLocker(LockerBean bean) {
        SQLiteDatabase db = openDatabase();
        db.beginTransaction();
        try {
            insert(db, bean);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            closeDatabase();
        }
    }

    public void saveLocker2(LockerBean bean) {
        SQLiteDatabase db = openDatabase();
        db.beginTransaction();
        try {
            insert(db, bean);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            closeDatabase();
        }
    }

    private void insert(SQLiteDatabase db, LockerBean bean) {
        Log.e(TAG, "insert********************");
        String sql = "INSERT INTO " + TABLE_LOCKER + " (date,time,userNumber,unUserNumber) VALUES(?,?,?,?)";
        db.execSQL(sql, new Object[]{bean.date, bean.time, bean.userNumber, bean.unUserNumber});
    }


    public List<LockerBean> getLocker(String date) {
        List<LockerBean> lockers = new ArrayList<>();
        SQLiteDatabase db = openDatabase();
        Cursor cursor = null;

        String sql = "SELECT * FROM " + TABLE_LOCKER + " WHERE date=?";
        try {
            cursor = db.rawQuery(sql, new String[]{date});
            while (cursor.moveToNext()) {
                LockerBean bean = new LockerBean();
                bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bean.setDate(cursor.getString(cursor.getColumnIndex("date")));
                bean.setTime(cursor.getInt(cursor.getColumnIndex("time")));
                bean.setUserNumber(cursor.getInt(cursor.getColumnIndex("userNumber")));
                bean.setUnUserNumber(cursor.getInt(cursor.getColumnIndex("unUserNumber")));
                lockers.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDatabase();
        }
        return lockers;
    }
}
