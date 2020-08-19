package com.kasai.stadium.tv.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtil {
    private final static String TAG = FileUtil.class.getSimpleName();

    public static boolean isExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getFileRootDirectory(Context context) {
        String path = null;
        if (isExistSDCard()) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + AppUtil.getAppName(context);
        } else {
            path = context.getApplicationContext().getExternalFilesDir(null).getPath();
        }
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            Log.i(TAG, "create directory ：" + path);
            file.mkdirs();
        }
        Log.i(TAG, "path : " + path);
        return file.getAbsolutePath();
    }


    public static void deleteFile(File file, boolean isDeleteDirectory) {
        if (file == null || !file.exists())
            return;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f, isDeleteDirectory);
            }
            if (isDeleteDirectory) {
                file.delete();
            }
        } else if (file.exists()) {
            file.delete();
        }
    }
}
