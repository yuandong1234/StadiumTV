package com.kasai.stadium.tv.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kasai.stadium.tv.dao.bean.FileBean;

import java.util.concurrent.atomic.AtomicInteger;

public class FileDao {
    public final static String TAG = FileDao.class.getSimpleName();
    public static final String TABLE_VIDEO = "t_file";//表的名称
    private AtomicInteger count = new AtomicInteger();
    private static DBHelper dbHelper;
    private static FileDao fileDao;
    private SQLiteDatabase database;

    private FileDao(Context context) {
        dbHelper = new DBHelper(context);
    }


    public static FileDao getInstance(Context context) {
        if (null == fileDao) {
            synchronized (FileDao.class) {
                if (null == fileDao) {
                    fileDao = new FileDao(context.getApplicationContext());
                }
            }
        }
        return fileDao;
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


    public void saveFile(FileBean bean) {
        SQLiteDatabase db = openDatabase();
        db.beginTransaction();
        Cursor cursor = null;
        String sql = "SELECT * FROM " + TABLE_VIDEO + " WHERE name=?";
        try {
            cursor = db.rawQuery(sql, new String[]{bean.name});
            if (cursor.getCount() > 0) {
                update(db, bean);
            } else {
                insert(db, bean);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            db.endTransaction();
            closeDatabase();
        }
    }

    private void insert(SQLiteDatabase db, FileBean bean) {
        Log.e(TAG, "insert********************");
        String sql = "INSERT INTO " + TABLE_VIDEO + " (name,path,status) VALUES(?,?,?)";
        db.execSQL(sql, new Object[]{bean.name, bean.path, bean.status});
    }

    private void update(SQLiteDatabase db, FileBean bean) {
        Log.e(TAG, "update********************");
        String sql = "UPDATE " + TABLE_VIDEO + " SET status = ? WHERE name = ? and path = ?";
        db.execSQL(sql, new Object[]{bean.status, bean.name, bean.path});
    }


    public FileBean getFile(String name) {
        SQLiteDatabase db = openDatabase();
        Cursor cursor = null;
        FileBean bean = null;

        String sql = "SELECT * FROM " + TABLE_VIDEO + " WHERE name=?";
        try {
            cursor = db.rawQuery(sql, new String[]{name});
            while (cursor.moveToNext()) {
                bean = new FileBean();
                bean.setName(cursor.getString(cursor.getColumnIndex("name")));
                bean.setPath(cursor.getString(cursor.getColumnIndex("path")));
                bean.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDatabase();
        }
        return bean;
    }


    public void deleteAll() {
        SQLiteDatabase db = openDatabase();
        try {
            db.beginTransaction();
            String sql = "delete from " + TABLE_VIDEO;
            db.execSQL(sql);
            db.setTransactionSuccessful();
            Log.e(TAG, " 清空文件下载记录");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            closeDatabase();
        }
    }
}
