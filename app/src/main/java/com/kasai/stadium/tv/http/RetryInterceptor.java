package com.kasai.stadium.tv.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {
    private String TAG = RetryInterceptor.class.getSimpleName();
    private int maxRetry;
    private int retryNum = 0;

    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.e(TAG, "http url : " + request.url());
        Response response = doRequest(chain, request);
        retryNum = 0;
        while ((response == null || !response.isSuccessful()) && retryNum <= maxRetry) {
            retryNum++;
            // retry the request
            Log.e(TAG, "intercept Request #maxRetry ：  " + maxRetry + " #retryNum" + retryNum);
            response = doRequest(chain, request);
        }
        return response;
    }

    private Response doRequest(Chain chain, Request request) {
        Response response = null;
        try {
            if (chain != null) {
                response = chain.proceed(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception ：  " + e.getMessage());
        }
        return response;
    }
}
