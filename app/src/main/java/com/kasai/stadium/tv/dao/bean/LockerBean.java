package com.kasai.stadium.tv.dao.bean;

public class LockerBean {
    public int id;
    public String date;
    public int time;
    public int userNumber;
    public int unUserNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public int getUnUserNumber() {
        return unUserNumber;
    }

    public void setUnUserNumber(int unUserNumber) {
        this.unUserNumber = unUserNumber;
    }

    @Override
    public String toString() {
        return "LockerBean{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", userNumber=" + userNumber +
                ", unUserNumber=" + unUserNumber +
                '}';
    }
}
