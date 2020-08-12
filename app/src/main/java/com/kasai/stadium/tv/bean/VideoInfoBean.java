package com.kasai.stadium.tv.bean;

import java.io.Serializable;

public class VideoInfoBean implements Serializable {
    public String video;
    public String venueName;
    public String merchantName;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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
