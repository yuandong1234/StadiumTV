package com.kasai.stadium.tv.bean;

import java.util.List;

public class WeatherBean {
    public String code;
    public String msg;
    public boolean charge;
    public Data result;

    public Data getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", charge=" + charge +
                ", result=" + result +
                '}';
    }

    public static class Data {
        public List<Weather> HeWeather5;

        @Override
        public String toString() {
            return "Data{" +
                    "HeWeather5=" + HeWeather5 +
                    '}';
        }

        public static class Weather {
            public Now now;
            public String status;

            @Override
            public String toString() {
                return "Weather{" +
                        "now=" + now +
                        ", status='" + status + '\'' +
                        '}';
            }

            public static class Now {
                public String tmp;
                public Cond cond;

                @Override
                public String toString() {
                    return "Now{" +
                            "tmp='" + tmp + '\'' +
                            ", cond=" + cond +
                            '}';
                }

                public static class Cond {
                    public String code;
                    public String txt;

                    @Override
                    public String toString() {
                        return "Cond{" +
                                "code='" + code + '\'' +
                                ", txt='" + txt + '\'' +
                                '}';
                    }
                }
            }
        }
    }

    public boolean isSuccessful() {
        return "10000".equals(code);
    }
}
