package com.kasai.stadium.tv.bean;

public class LoginBean extends BaseBean {
    public Data data;

    @Override
    public String toString() {
        return "LoginBean{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class Data {
        /**
         * token true string
         * isShowVenueList true boolean 是否需要跳转到场馆选择界面
         */
        public String token;
        public boolean isShowVenueList;

        @Override
        public String toString() {
            return "Data{" +
                    "token='" + token + '\'' +
                    ", isShowVenueList=" + isShowVenueList +
                    '}';
        }
    }

    public Data getData() {
        return data;
    }
}
