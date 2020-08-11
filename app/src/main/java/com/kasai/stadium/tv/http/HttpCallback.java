package com.kasai.stadium.tv.http;

public abstract class HttpCallback<T> {
    protected abstract void onSuccess(T data);

    protected abstract void onFailure(String error);
}
