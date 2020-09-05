package com.kasai.stadium.tv.utils;

import android.content.Context;

import com.kasai.stadium.tv.App;
import com.kasai.stadium.tv.constants.Constants;

public class UserInfoUtil extends BaseSharePreferences {


    private UserInfoUtil(Context context, String name) {
        super(context, name);
    }

    private static class SingletonHolder {
        private static final UserInfoUtil INSTANCE = new UserInfoUtil(App.getContext(), Constants.SP_USER_INFO);
    }

    public static UserInfoUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getUserAccount() {
        return getString(Constants.SP_KEY_USER_ACCOUNT, "");
    }

    public String getUserPassword() {
        return getString(Constants.SP_KEY_USER_PASSWORD, "");
    }

    public String getUserToken() {
        return getString(Constants.SP_KEY_USER_TOKEN, "");
    }

}
