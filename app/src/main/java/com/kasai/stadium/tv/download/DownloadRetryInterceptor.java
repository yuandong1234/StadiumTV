package com.kasai.stadium.tv.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class DownloadRetryInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
