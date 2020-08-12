package com.kasai.stadium.tv.bean;

import java.util.List;

public class AdvertInfoBean extends BaseBean {
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "AdvertInfoBean{" +
                "data=" + data +
                '}';
    }

    public static class Data {
        public int id;
        public int advertType;//1:图片，2：视频，3：场馆信息，4：文字 ，5：客服信息',
        public int venueType;//1:游泳，2：体育馆，3：体育场 */
        public int merchantId;
        public String merchantName;
        public int venueId;
        public String image;
        public String video;
        public String content;
        public String waterTemperature;
        public String phValue;
        public String chlorineValue;
        public String outdoorTemperature;
        public String indoorTemperature;
        public int weatherShow;
        public String teamInfo;
        public int operatorId;
        public String operatorName;
        public int status;
        public String createTime;
        public String updateTime;
        public String showVenueId;
        public int availableNum;
        public int payNotReceiveNum;
        public int payReceiveNum;
        public String time;
        public String week;
        public String chineseDate;
        public String venueName;
        public String peopleNumber;
        public long currentTime;

        public List<OnlineService> tAdvertCustomerServiceList;
        public List<Stadium> advertFieldList;

        public int getId() {
            return id;
        }

        public List<OnlineService> getAdvertCustomerServiceList() {
            return tAdvertCustomerServiceList;
        }

        public List<Stadium> getAdvertFieldList() {
            return advertFieldList;
        }

        public long getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(long currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", advertType=" + advertType +
                    ", venueType=" + venueType +
                    ", merchantId=" + merchantId +
                    ", merchantName='" + merchantName + '\'' +
                    ", venueId=" + venueId +
                    ", image='" + image + '\'' +
                    ", video='" + video + '\'' +
                    ", content='" + content + '\'' +
                    ", waterTemperature='" + waterTemperature + '\'' +
                    ", phValue='" + phValue + '\'' +
                    ", chlorineValue='" + chlorineValue + '\'' +
                    ", outdoorTemperature='" + outdoorTemperature + '\'' +
                    ", indoorTemperature='" + indoorTemperature + '\'' +
                    ", weatherShow=" + weatherShow +
                    ", teamInfo='" + teamInfo + '\'' +
                    ", operatorId=" + operatorId +
                    ", operatorName='" + operatorName + '\'' +
                    ", status=" + status +
                    ", createTime='" + createTime + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", showVenueId='" + showVenueId + '\'' +
                    ", availableNum='" + availableNum + '\'' +
                    ", payNotReceiveNum='" + payNotReceiveNum + '\'' +
                    ", payReceiveNum='" + payReceiveNum + '\'' +
                    ", time='" + time + '\'' +
                    ", week='" + week + '\'' +
                    ", chineseDate='" + chineseDate + '\'' +
                    ", venueName='" + venueName + '\'' +
                    ", peopleNumber='" + peopleNumber + '\'' +
                    ", tAdvertCustomerServiceList=" + tAdvertCustomerServiceList +
                    ", advertFieldList=" + advertFieldList +
                    '}';
        }

        public static class OnlineService {
            /**
             * customerServiceName true string 客服名字
             * image true string 图片
             * status true string 1:在岗，2：离岗
             * customerServiceDes true string 岗位描述
             */
            public String customerServiceName;
            public String image;
            public String status;
            public String customerServiceDes;

            @Override
            public String toString() {
                return "OnlineService{" +
                        "customerServiceName='" + customerServiceName + '\'' +
                        ", image='" + image + '\'' +
                        ", status='" + status + '\'' +
                        ", customerServiceDes='" + customerServiceDes + '\'' +
                        '}';
            }
        }


        public static class Stadium {
            /**
             * percent true number 百分比
             * fieldName true string 场地名，逗号隔开
             * fieldNum true number 可订场数
             * sportName true string 运动名
             * sportId true string 运动id
             */
            public String percent;
            public String fieldName;
            public String fieldNum;
            public String sportName;
            public String sportId;

            @Override
            public String toString() {
                return "Stadium{" +
                        "percent='" + percent + '\'' +
                        ", fieldName='" + fieldName + '\'' +
                        ", fieldNum='" + fieldNum + '\'' +
                        ", sportName='" + sportName + '\'' +
                        ", sportId='" + sportId + '\'' +
                        '}';
            }
        }
    }
}
