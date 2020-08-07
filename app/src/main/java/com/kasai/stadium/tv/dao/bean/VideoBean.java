package com.kasai.stadium.tv.dao.bean;

public class VideoBean {
    public long id;
    public String name;
    public String path;
    public int status;//0: 未下载； 1：已下载

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
