package com.kasai.stadium.tv.bean;

import java.io.Serializable;

public class StadiumNoticeBean implements Serializable {
    public String content;
    public String venueName;
    public String merchantName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
}
