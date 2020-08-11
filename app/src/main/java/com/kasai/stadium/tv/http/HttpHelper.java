package com.kasai.stadium.tv.http;

import android.support.annotation.NonNull;

import com.kasai.stadium.tv.utils.UserInfoUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper {

    public static void get(String url, Map<String, String> body, HttpCallback callback) {
        get(url, body, null, callback);
    }

    public static void get(String url, Map<String, String> body, Map<String, String> header, HttpCallback callback) {
        http(url, HttpMethod.GET, body, header, callback);
    }


    public static void post(String url, Map<String, String> body, HttpCallback callback) {
        post(url, body, null, callback);
    }

    public static void post(String url, Map<String, String> body, Map<String, String> header, HttpCallback callback) {
        http(url, HttpMethod.POST, body, header, callback);
    }

    private static void http(String url, HttpMethod method, Map<String, String> body, Map<String, String> header, HttpCallback callback) {
        RequestBody requestBody = getBody(body);
        Request.Builder builder = new Request.Builder();
        addHeader(header, builder);
        Map<String, String> commonHeader = getCommonHeader();
        if (commonHeader != null && commonHeader.size() > 0) {
            addHeader(commonHeader, builder);
        }

        if (method == HttpMethod.POST) {
            builder.post(requestBody);
        }
        builder.url(method == HttpMethod.POST ? url : getUrl(url, header));
        Request request = builder.build();
        final HttpWrapperCallback wrapperCallback = new HttpWrapperCallback(callback);
        HttpProvider.getInstance().getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                wrapperCallback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                wrapperCallback.onSuccess(response);
            }
        });
    }

    private static RequestBody getBody(Map<String, String> body) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : body.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.add(key, value);
        }
        return builder.build();
    }

    private static void addHeader(Map<String, String> header, Request.Builder builder) {
        if (header == null || header.size() == 0) return;
        for (Map.Entry<String, String> entry : header.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.header(key, value);
        }
    }

    private static String getUrl(String url, Map<String, String> params) {
        if (url == null || url.equals("")) return null;
        if (params == null || params.size() == 0) return url;
        StringBuilder sb = new StringBuilder();
        sb.append(url).append("?");
        if (!params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private static Map<String, String> getCommonHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", UserInfoUtil.getInstance().getUserToken());
        header.put("platform", "2");
        header.put("apiVersion", "1");
        return header;
    }
}
