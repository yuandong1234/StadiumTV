package com.kasai.stadium.tv.http;

import okhttp3.OkHttpClient;

public class HttpProvider {
    private HttpFactory factory;

    private static class SingletonHolder {
        private static final HttpProvider INSTANCE = new HttpProvider();
    }

    private HttpProvider() {
        factory = new HttpFactory();
    }

    public static final HttpProvider getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public OkHttpClient getClient() {
        return factory.crateClient();
    }
}
