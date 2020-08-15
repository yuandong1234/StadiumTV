package com.kasai.stadium.tv;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.kasai.stadium.tv.crash.CrashHandler;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.connection.DownloadOkHttp3Connection;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Stetho.initializeWithDefaults(this);
        initDownload();
        initBugly();
//        CrashHandler.getInstance().init(this);
    }

    private void initDownload() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpBuilder.readTimeout(30, TimeUnit.SECONDS);
        httpBuilder.writeTimeout(15, TimeUnit.SECONDS);
        DownloadOkHttp3Connection.Factory factory = new DownloadOkHttp3Connection.Factory()
                .setBuilder(httpBuilder);
        OkDownload.Builder builder = new OkDownload.Builder(this)
                .connectionFactory(factory);
        OkDownload.setSingletonInstance(builder.build());
        Util.enableConsoleLog();
    }

    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "3efa8cdf42", true);
    }

    public static Context getContext() {
        return context;
    }
}
