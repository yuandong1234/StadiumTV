package com.kasai.stadium.tv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.SectionsPagerAdapter;
import com.kasai.stadium.tv.adapter.SpaceNumberAdapter;
import com.kasai.stadium.tv.bean.AdvertInfoBean;
import com.kasai.stadium.tv.bean.GymnasiumBean;
import com.kasai.stadium.tv.bean.ImageBean;
import com.kasai.stadium.tv.bean.OnlineServiceBean;
import com.kasai.stadium.tv.bean.StadiumBean;
import com.kasai.stadium.tv.bean.StadiumNoticeBean;
import com.kasai.stadium.tv.bean.SwimmingStadiumBean;
import com.kasai.stadium.tv.bean.VideoInfoBean;
import com.kasai.stadium.tv.constants.Api;
import com.kasai.stadium.tv.fragment.GymnasiumFragment;
import com.kasai.stadium.tv.fragment.ImageFragment;
import com.kasai.stadium.tv.fragment.OnlineServiceFragment;
import com.kasai.stadium.tv.fragment.StadiumFragment;
import com.kasai.stadium.tv.fragment.StadiumNoticeFragment;
import com.kasai.stadium.tv.fragment.SwimmingStadiumFragment;
import com.kasai.stadium.tv.fragment.VideoFragment;
import com.kasai.stadium.tv.http.HttpCallback;
import com.kasai.stadium.tv.http.HttpHelper;
import com.kasai.stadium.tv.utils.DateUtil;
import com.kasai.stadium.tv.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StadiumPageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private RecyclerView rvAvailableSpace;
    private RecyclerView rvService;
    private SpaceNumberAdapter adapter;

    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium);
        initView();
        loadData();
    }

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(this);

//        ImageFragment imageFragment = ImageFragment.newInstance();
//        imageFragment.bindHandler(handler);
//        fragments.add(imageFragment);
//
//        VideoFragment videoFragment = VideoFragment.newInstance(urls[0]);
//        videoFragment.bindHandler(handler);
//        fragments.add(videoFragment);
//
//        VideoFragment videoFragment2 = VideoFragment.newInstance(urls[1]);
//        videoFragment2.bindHandler(handler);
//        fragments.add(videoFragment2);

//        StadiumFragment stadiumFragment = StadiumFragment.newInstance();
//        GymnasiumFragment gymnasiumFragment = GymnasiumFragment.newInstance();
//        OnlineServiceFragment onlineServiceFragment = OnlineServiceFragment.newInstance();
//        StadiumNoticeFragment noticeFragment = StadiumNoticeFragment.newInstance();
//        SwimmingStadiumFragment swimmingFragment = SwimmingStadiumFragment.newInstance();
//
//        fragments.add(stadiumFragment);
//        fragments.add(gymnasiumFragment);
//        fragments.add(onlineServiceFragment);
//        fragments.add(noticeFragment);
//        fragments.add(swimmingFragment);
//
//        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setOffscreenPageLimit(fragments.size());
    }

    private void loadData() {
        Map<String, String> body = new HashMap<>();
        HttpHelper.get(Api.HOST + Api.API_ADVERT_INFO, body, new HttpCallback<AdvertInfoBean>() {
            @Override
            protected void onSuccess(AdvertInfoBean data) {
                //hideLoadingDialog();
                Log.e("StadiumPageActivity", " data : " + data.toString());
                if (data.isSuccessful() && data.getData() != null) {
                    setPages(data.getData());
                } else {
                    ToastUtil.showShortCenter(data.getMsg());
                }
            }

            @Override
            protected void onFailure(String error) {
                //hideLoadingDialog();
                ToastUtil.showShortCenter(error);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setPages(List<AdvertInfoBean.Data> dataList) {
        if (dataList == null || dataList.size() == 0) return;
        fragments.clear();
        for (AdvertInfoBean.Data temp : dataList) {
            convertData(temp);
        }
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    private void convertData(AdvertInfoBean.Data data) {
        /**
         * 1:图片，2：视频，3：场馆信息，4：文字 ，5：客服信息',
         */
        int advertType = data.advertType;
        switch (advertType) {
            case 1:
                ImageBean imageBean = new ImageBean();
                imageBean.setImage(data.image);
                imageBean.setVenueName(data.venueName);
                imageBean.setMerchantName(data.merchantName);
                ImageFragment imageFragment = ImageFragment.newInstance(imageBean);
                fragments.add(imageFragment);
                break;
            case 2:
                VideoInfoBean videoBean = new VideoInfoBean();
                videoBean.setVideo(data.video);
                videoBean.setVenueName(data.venueName);
                videoBean.setMerchantName(data.merchantName);
                VideoFragment videoFragment = VideoFragment.newInstance(videoBean);
                fragments.add(videoFragment);
                break;
            case 4:
                StadiumNoticeBean noticeBean = new StadiumNoticeBean();
                noticeBean.setContent(data.content);
                noticeBean.setVenueName(data.venueName);
                noticeBean.setMerchantName(data.merchantName);
                StadiumNoticeFragment noticeFragment = StadiumNoticeFragment.newInstance(noticeBean);
                fragments.add(noticeFragment);
                break;
            case 5:
                List<OnlineServiceBean> serviceBeans = convertService(data);
                if (serviceBeans != null && serviceBeans.size() > 0) {
                    for (OnlineServiceBean temp : serviceBeans) {
                        OnlineServiceFragment serviceFragment = OnlineServiceFragment.newInstance(temp);
                        fragments.add(serviceFragment);
                    }
                }
                break;
            case 3:
                /**
                 * 1:游泳，2：体育馆，3：体育场
                 */
                int venueType = data.venueType;

                switch (venueType) {
                    case 1://游泳馆
                        SwimmingStadiumBean swimmingStadiumBean = convertSwimmingStadium(data);
                        SwimmingStadiumFragment swimmingStadiumFragment = SwimmingStadiumFragment.newInstance(swimmingStadiumBean);
                        fragments.add(swimmingStadiumFragment);
                        break;
                    case 2://体育馆
                        GymnasiumBean gymnasiumBean = convertGymnasium(data);
                        GymnasiumFragment gymnasiumFragment = GymnasiumFragment.newInstance(gymnasiumBean);
                        fragments.add(gymnasiumFragment);
                        break;
                    case 3://体育场
                        StadiumBean stadiumBean = convertStadium(data);
                        StadiumFragment stadiumFragment = StadiumFragment.newInstance(stadiumBean);
                        fragments.add(stadiumFragment);
                        break;
                }
                break;
        }
    }

    private List<OnlineServiceBean> convertService(AdvertInfoBean.Data data) {
        List<AdvertInfoBean.Data.OnlineService> services = data.getAdvertCustomerServiceList();
        if (services == null || services.size() == 0) return null;
        int totalSize = services.size();
        int size = 6;
        int page = totalSize / size;
        int extra = totalSize % size;
        List<List<AdvertInfoBean.Data.OnlineService>> lists = new ArrayList<>();
        if (page > 0) {
            for (int i = 0; i < page; i++) {
                List<AdvertInfoBean.Data.OnlineService> temp = services.subList(i * size, (i + 1) * size);
                lists.add(temp);
            }
        }

        if (extra > 0) {
            List<AdvertInfoBean.Data.OnlineService> temp = services.subList(page * size, page * size + extra);
            lists.add(temp);
        }

        List<OnlineServiceBean> serviceBeans = new ArrayList<>();
        for (List<AdvertInfoBean.Data.OnlineService> tempList : lists) {
            List<OnlineServiceBean.Service> servicesList = new ArrayList<>();
            for (AdvertInfoBean.Data.OnlineService temp : tempList) {
                OnlineServiceBean.Service service = new OnlineServiceBean.Service();
                service.setName(temp.customerServiceName);
                service.setImage(temp.image);
                service.setStatus(temp.status);
                service.setServiceDes(temp.customerServiceDes);
                servicesList.add(service);
            }
            OnlineServiceBean serviceBean = new OnlineServiceBean();
            serviceBean.setVenueName(data.venueName);
            serviceBean.setMerchantName(data.merchantName);
            serviceBean.setServiceList(servicesList);
            serviceBeans.add(serviceBean);
        }
        return serviceBeans;
    }

    private SwimmingStadiumBean convertSwimmingStadium(AdvertInfoBean.Data data) {
        SwimmingStadiumBean swimmingStadiumBean = new SwimmingStadiumBean();
        swimmingStadiumBean.setVenueName(data.venueName);
        swimmingStadiumBean.setDate(data.time);
        swimmingStadiumBean.setWeek(data.week);
        swimmingStadiumBean.setChinaDate(data.chineseDate);
        swimmingStadiumBean.setPeopleNumber(data.peopleNumber);
        swimmingStadiumBean.setWaterTemperature(data.waterTemperature);
        swimmingStadiumBean.setPhValue(data.phValue);
        swimmingStadiumBean.setChlorineValue(data.chlorineValue);
        swimmingStadiumBean.setAvailableNum(data.availableNum);
        swimmingStadiumBean.setPayReceiveNum(data.payReceiveNum);
        swimmingStadiumBean.setPayNotReceiveNum(data.payNotReceiveNum);
        swimmingStadiumBean.setMerchantName(data.merchantName);
        return swimmingStadiumBean;
    }

    private StadiumBean convertStadium(AdvertInfoBean.Data data) {
        List<AdvertInfoBean.Data.Stadium> stadiums = data.getAdvertFieldList();
        if (stadiums == null || stadiums.size() == 0) return null;
        int size = stadiums.size();
        List<AdvertInfoBean.Data.Stadium> list = null;
        if (size > 3) {
            list = stadiums.subList(0, 3);
        } else {
            list = stadiums;
        }
        StadiumBean stadiumBean = new StadiumBean();
        List<StadiumBean.Sport> sports = new ArrayList<>();
        for (AdvertInfoBean.Data.Stadium temp : list) {
            StadiumBean.Sport sport = new StadiumBean.Sport();
            sport.setFieldName(temp.fieldName);
            sport.setPercent(temp.percent);
            sport.setFieldNum(temp.fieldNum);
            sport.setSportId(temp.sportId);
            sport.setSportName(temp.sportName);
            sports.add(sport);
        }
        stadiumBean.setSportList(sports);
        stadiumBean.setVenueName(data.venueName);
        stadiumBean.setDate(data.time);
        stadiumBean.setWeek(data.week);
        stadiumBean.setChinaDate(data.chineseDate);
        stadiumBean.setPeopleNumber(data.peopleNumber);
        stadiumBean.setTemperature(data.outdoorTemperature);
        stadiumBean.setMerchantName(data.merchantName);
        return stadiumBean;
    }


    private GymnasiumBean convertGymnasium(AdvertInfoBean.Data data) {
        List<AdvertInfoBean.Data.Stadium> stadiums = data.getAdvertFieldList();
        if (stadiums == null || stadiums.size() == 0) return null;
        int size = stadiums.size();
        List<AdvertInfoBean.Data.Stadium> list = null;
        if (size > 3) {
            list = stadiums.subList(0, 3);
        } else {
            list = stadiums;
        }
        GymnasiumBean gymnasiumBean = new GymnasiumBean();
        List<GymnasiumBean.Sport> sports = new ArrayList<>();
        for (AdvertInfoBean.Data.Stadium temp : list) {
            GymnasiumBean.Sport sport = new GymnasiumBean.Sport();
            sport.setFieldName(temp.fieldName);
            sport.setPercent(temp.percent);
            sport.setFieldNum(temp.fieldNum);
            sport.setSportId(temp.sportId);
            sport.setSportName(temp.sportName);
            sports.add(sport);
        }
        gymnasiumBean.setSportList(sports);
        gymnasiumBean.setVenueName(data.venueName);
        gymnasiumBean.setDate(data.time);
        gymnasiumBean.setWeek(data.week);
        gymnasiumBean.setChinaDate(data.chineseDate);
        gymnasiumBean.setPeopleNumber(data.peopleNumber);
        gymnasiumBean.setOutdoorTemperature(data.outdoorTemperature);
        gymnasiumBean.setIndoorTemperature(data.indoorTemperature);
        return gymnasiumBean;
    }
}