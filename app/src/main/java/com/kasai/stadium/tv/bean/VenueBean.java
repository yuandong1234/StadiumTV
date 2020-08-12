package com.kasai.stadium.tv.bean;

public class VenueBean extends BaseBean {
    public Data data;

    @Override
    public String toString() {
        return "VenueBean{" +
                "data=" + data +
                '}';
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        public String token;

        @Override
        public String toString() {
            return "Data{" +
                    "token='" + token + '\'' +
                    '}';
        }
    }
}
