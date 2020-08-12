package com.kasai.stadium.tv.bean;

import java.util.List;

public class VenueListBean extends BaseBean {
    public Data data;

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return "VenueListBean{" +
                "data=" + data +
                '}';
    }

    public static class Data {

        public List<Venue> venueList;

        @Override
        public String toString() {
            return "Data{" +
                    "venueList=" + venueList +
                    '}';
        }

        public List<Venue> getVenueList() {
            return venueList;
        }

        public static class Venue {
            /**
             * id true string
             * thumbnail true string 缩略图
             * venueName true string
             */
            public String id;
            public String venueName;
            public String thumbnail;

            @Override
            public String toString() {
                return "Venue{" +
                        "id='" + id + '\'' +
                        ", venueName='" + venueName + '\'' +
                        ", thumbnail='" + thumbnail + '\'' +
                        '}';
            }
        }
    }
}
