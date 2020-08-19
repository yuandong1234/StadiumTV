package com.kasai.stadium.tv.download;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.kasai.stadium.tv.constants.Constants;
import com.kasai.stadium.tv.dao.FileDao;
import com.kasai.stadium.tv.dao.LockerDao;
import com.kasai.stadium.tv.utils.DateUtil;
import com.kasai.stadium.tv.utils.DownloadUtil;
import com.kasai.stadium.tv.utils.FileUtil;

import java.io.File;

public class DownloadManager {

    private int expiryDays = 7;
    private Context context;

    public DownloadManager(Context context) {
        this.context = context;
    }

    public void deleteAllHistory() {
        Log.e("DownloadManager","deleteAllHistory......");
        boolean isNeedDelete = isNeedDelete();
        if (isNeedDelete) {
            Log.e("DownloadManager","file need delete....");
            deleteAllFile();
            FileDao.getInstance(context).deleteAll();
            LockerDao.getInstance(context).deleteAll();
        }
    }

    private void deleteAllFile() {
        String fileDirectory = FileUtil.getFileRootDirectory(context);
        FileUtil.deleteFile(new File(fileDirectory), false);
    }

    private boolean isNeedDelete() {
        String lastDate = DownloadUtil.getInstance().getFileSavingStartDate();
        String currentDate = DateUtil.getCurrentDate(DateUtil.DATE);
        if (TextUtils.isEmpty(lastDate)) {
            DownloadUtil.getInstance().putValue(Constants.SP_KEY_FILE_SAVING_START_DATE, currentDate);
            return false;
        }
        int dayOff = DateUtil.daysBetween(lastDate, currentDate, DateUtil.DATE);
        if(dayOff > expiryDays){
            DownloadUtil.getInstance().putValue(Constants.SP_KEY_FILE_SAVING_START_DATE, currentDate);
            return true;
        }
        return false;
    }
}
