package com.kasai.stadium.tv.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.kasai.stadium.tv.App;

public class ToastUtil {

    private static Toast toast;

    /**
     * 居中
     *
     * @param msg
     */
    public static void showShortCenter(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
