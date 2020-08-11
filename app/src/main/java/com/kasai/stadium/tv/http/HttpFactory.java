package com.kasai.stadium.tv.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpFactory {
    private long connectTime = 30;
    private long readTime = 30;
    private long writeTime = 15;
    private OkHttpClient client;

    public void setConnectTime(long connectTime) {
        this.connectTime = connectTime;
    }

    public void setReadTime(long readTime) {
        this.readTime = readTime;
    }

    public void setWriteTime(long writeTime) {
        this.writeTime = writeTime;
    }

    public OkHttpClient crateClient() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(connectTime, TimeUnit.SECONDS);
            builder.readTimeout(readTime, TimeUnit.SECONDS);
            builder.writeTimeout(writeTime, TimeUnit.SECONDS);
            builder.addInterceptor(new RetryInterceptor(3));
            client = builder.build();
        }
        return client;
    }
}
