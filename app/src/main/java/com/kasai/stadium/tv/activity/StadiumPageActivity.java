package com.kasai.stadium.tv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.SectionsPagerAdapter;
import com.kasai.stadium.tv.adapter.SpaceNumberAdapter;
import com.kasai.stadium.tv.bean.LoginBean;
import com.kasai.stadium.tv.constants.Api;
import com.kasai.stadium.tv.fragment.GymnasiumFragment;
import com.kasai.stadium.tv.fragment.OnlineServiceFragment;
import com.kasai.stadium.tv.fragment.StadiumFragment;
import com.kasai.stadium.tv.fragment.StadiumNoticeFragment;
import com.kasai.stadium.tv.fragment.SwimmingStadiumFragment;
import com.kasai.stadium.tv.http.HttpCallback;
import com.kasai.stadium.tv.http.HttpHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StadiumPageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

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
        testLogin();
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

        StadiumFragment stadiumFragment = StadiumFragment.newInstance();
        GymnasiumFragment gymnasiumFragment = GymnasiumFragment.newInstance();
        OnlineServiceFragment onlineServiceFragment = OnlineServiceFragment.newInstance();
        StadiumNoticeFragment noticeFragment = StadiumNoticeFragment.newInstance();
        SwimmingStadiumFragment swimmingFragment = SwimmingStadiumFragment.newInstance();

        fragments.add(stadiumFragment);
        fragments.add(gymnasiumFragment);
        fragments.add(onlineServiceFragment);
        fragments.add(noticeFragment);
        fragments.add(swimmingFragment);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.size());
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

    private void testLogin() {
        Map<String, String> body = new HashMap<>();
        body.put("username", "yuandong");
        body.put("password", "123456");
        HttpHelper.post(Api.HOST + Api.API_LOGIN, body, new HttpCallback<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean data) {
                Log.e("StadiumPageActivity", " data : " + data.toString());
            }

            @Override
            protected void onFailure(String error) {

            }
        });
    }
}
