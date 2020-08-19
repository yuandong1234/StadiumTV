package com.kasai.stadium.tv.utils;

import android.content.Context;

import com.kasai.stadium.tv.App;
import com.kasai.stadium.tv.constants.Constants;

public class DownloadUtil extends BaseSharePreferences {
    private DownloadUtil(Context context, String name) {
        super(context, name);
    }

    private static class SingletonHolder {
        private static final DownloadUtil INSTANCE = new DownloadUtil(App.getContext(), Constants.SP_DOWNLOAD_INFO);
    }

    public static DownloadUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public String getFileSavingStartDate() {
        return getString(Constants.SP_KEY_FILE_SAVING_START_DATE, "");
    }
}
