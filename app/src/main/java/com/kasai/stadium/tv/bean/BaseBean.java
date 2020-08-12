package com.kasai.stadium.tv.bean;

import java.io.Serializable;

public class BaseBean implements Serializable {
    /**
     * code true string
     * msg true string
     */
    public int code;
    public String msg;

    public boolean isSuccessful() {
        return code == 200;
    }

    public String getMsg() {
        return msg;
    }
}
