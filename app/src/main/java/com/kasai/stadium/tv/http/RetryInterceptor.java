package com.kasai.stadium.tv.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
