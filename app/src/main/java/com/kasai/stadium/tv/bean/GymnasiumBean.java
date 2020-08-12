package com.kasai.stadium.tv.bean;

import java.io.Serializable;
import java.util.List;

public class GymnasiumBean implements Serializable {
    public String venueName;
    public String merchantName;
    public String date;
    public String week;
    public String chinaDate;
    public String peopleNumber;
    public String indoorTemperature;
    public String outdoorTemperature;
    public List<Sport> sportList;

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getChinaDate() {
        return chinaDate;
    }

    public void setChinaDate(String chinaDate) {
        this.chinaDate = chinaDate;
    }

    public String getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(String peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getIndoorTemperature() {
        return indoorTemperature;
    }

    public void setIndoorTemperature(String indoorTemperature) {
        this.indoorTemperature = indoorTemperature;
    }

    public String getOutdoorTemperature() {
        return outdoorTemperature;
    }

    public void setOutdoorTemperature(String outdoorTemperature) {
        this.outdoorTemperature = outdoorTemperature;
    }

    public List<Sport> getSportList() {
        return sportList;
    }

    public void setSportList(List<Sport> sportList) {
        this.sportList = sportList;
    }

    public static class Sport implements Serializable {
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

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldNum() {
            return fieldNum;
        }

        public void setFieldNum(String fieldNum) {
            this.fieldNum = fieldNum;
        }

        public String getSportName() {
            return sportName;
        }

        public void setSportName(String sportName) {
            this.sportName = sportName;
        }

        public String getSportId() {
            return sportId;
        }

        public void setSportId(String sportId) {
            this.sportId = sportId;
        }
    }
}
