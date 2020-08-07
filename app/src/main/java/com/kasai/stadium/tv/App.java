package com.kasai.stadium.tv;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.connection.DownloadOkHttp3Connection;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        initDownload();
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
}
