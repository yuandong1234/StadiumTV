package com.kasai.stadium.tv.bean;

import java.io.Serializable;

public class SwimmingStadiumBean implements Serializable {
    public String venueName;
    public String merchantName;
    public String date;
    public String week;
    public String chinaDate;
    public String peopleNumber;
    public String waterTemperature;
    public String phValue;
    public String chlorineValue;
    public int availableNum;
    public int payNotReceiveNum;
    public int payReceiveNum;

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

    public String getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(String waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public String getPhValue() {
        return phValue;
    }

    public void setPhValue(String phValue) {
        this.phValue = phValue;
    }

    public String getChlorineValue() {
        return chlorineValue;
    }

    public void setChlorineValue(String chlorineValue) {
        this.chlorineValue = chlorineValue;
    }

    public int getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(int availableNum) {
        this.availableNum = availableNum;
    }

    public int getPayNotReceiveNum() {
        return payNotReceiveNum;
    }

    public void setPayNotReceiveNum(int payNotReceiveNum) {
        this.payNotReceiveNum = payNotReceiveNum;
    }

    public int getPayReceiveNum() {
        return payReceiveNum;
    }

    public void setPayReceiveNum(int payReceiveNum) {
        this.payReceiveNum = payReceiveNum;
    }
}
