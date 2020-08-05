package com.kasai.stadium.tv.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    private final static String TAG = FileUtil.class.getSimpleName();

    public static boolean isExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getVideoRootDirectory(Context context) {
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
}
