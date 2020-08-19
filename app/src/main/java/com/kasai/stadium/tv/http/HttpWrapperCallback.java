package com.kasai.stadium.tv.http;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Response;

public class HttpWrapperCallback {
    private Handler handler = new Handler();
    private HttpCallback callBack;
    private Gson gson = new Gson();

    public HttpWrapperCallback(HttpCallback mCallBack) {
        this.callBack = mCallBack;
    }

    public void onSuccess(Response response) {
        try {
            String content = response.body().string();
            Log.e("onSuccess", "response data : " + content);
            final Object object = gson.fromJson(content, getType());
            if (callBack != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(object);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onFailure(Exception e) {
        e.printStackTrace();
        String msg = "";
        if (e instanceof UnknownHostException) {
            msg = "连接失败 :" + e.getMessage();
        } else if (e instanceof ConnectTimeoutException) {
            msg = "连接超时 :" + e.getMessage();
        } else if (e instanceof SocketTimeoutException) {
            msg = "连接超时 :" + e.getMessage();
        } else {
            msg = "请求失败 :" + e.getMessage();
        }
        final String error = msg;
        if (callBack != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onFailure(error);
                }
            });
        }
    }

    private Type getType() {
        Type type = callBack.getClass().getGenericSuperclass();
        Type[] paramType = ((ParameterizedType) type).getActualTypeArguments();
        return paramType[0];
    }
}
