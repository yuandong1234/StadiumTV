package com.kasai.stadium.tv.bean;

import java.io.Serializable;
import java.util.List;

public class OnlineServiceBean implements Serializable {

    private List<Service> serviceList;
    private String venueName;
    public String merchantName;
    public String teamInfo;

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
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

    public String getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(String teamInfo) {
        this.teamInfo = teamInfo;
    }

    public static class Service implements Serializable {
        /**
         * customerServiceName true string 客服名字
         * image true string 图片
         * status true string 1:在岗，2：离岗
         * customerServiceDes true string 岗位描述
         */
        public String name;
        public String image;
        public String status;
        public String serviceDes;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getServiceDes() {
            return serviceDes;
        }

        public void setServiceDes(String serviceDes) {
            this.serviceDes = serviceDes;
        }
    }
}
