package com.kasai.stadium.tv.bean;

import java.io.Serializable;
import java.util.List;

public class VideoInfoBean implements Serializable {
    public String venueName;
    public String merchantName;
    public String video;
    public List<String> videos;

    public String getVideo() {
        return video;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
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
