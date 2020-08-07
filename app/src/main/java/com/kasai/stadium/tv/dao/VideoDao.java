package com.kasai.stadium.tv.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kasai.stadium.tv.dao.bean.VideoBean;

import java.util.concurrent.atomic.AtomicInteger;

public class VideoDao {
    public final static String TAG = VideoDao.class.getSimpleName();
    public static final String TABLE_VIDEO = "t_video";//表的名称
    private AtomicInteger count = new AtomicInteger();
    private static DBHelper dbHelper;
    private static VideoDao videoDao;
    private SQLiteDatabase database;

    private VideoDao(Context context) {
        dbHelper = new DBHelper(context);
    }


    public static VideoDao getInstance(Context context) {
        if (null == videoDao) {
            synchronized (VideoDao.class) {
                if (null == videoDao) {
                    videoDao = new VideoDao(context.getApplicationContext());
                }
            }
        }
        return videoDao;
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


    public void saveVideo(VideoBean bean) {
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

    private void insert(SQLiteDatabase db, VideoBean bean) {
        Log.e(TAG, "insert********************");
        String sql = "INSERT INTO " + TABLE_VIDEO + " (name,path,status) VALUES(?,?,?)";
        db.execSQL(sql, new Object[]{bean.name, bean.path, bean.status});
    }

    private void update(SQLiteDatabase db, VideoBean bean) {
        Log.e(TAG, "update********************");
        String sql = "UPDATE " + TABLE_VIDEO + " SET status = ? WHERE name = ? and path = ?";
        db.execSQL(sql, new Object[]{bean.status, bean.name, bean.path});
    }


    public VideoBean getVideo(String name) {
        SQLiteDatabase db = openDatabase();
        Cursor cursor = null;
        VideoBean bean = null;

        String sql = "SELECT * FROM " + TABLE_VIDEO + " WHERE name=?";
        try {
            cursor = db.rawQuery(sql, new String[]{name});
            while (cursor.moveToNext()) {
                bean = new VideoBean();
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
}
