package com.kasai.stadium.tv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.bean.AdvertInfoBean;
import com.kasai.stadium.tv.constants.Api;
import com.kasai.stadium.tv.constants.Constants;
import com.kasai.stadium.tv.http.HttpCallback;
import com.kasai.stadium.tv.http.HttpHelper;
import com.kasai.stadium.tv.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源下载
 */
public class ResourceDownloadActivity extends BaseActivity {
    private TextView tvStadiumName;
    private TextView tvStadiumWelcome;
    private TextView tvStadiumName2;
    private LinearLayout llDownload;
    private Button btnDown;
    private LinearLayout llDownloading;
    private RecyclerView rvDowning;
    private LinearLayout llDownloadNone;

    private List<String> videoUrls = new ArrayList<>();
    private Handler handler = new Handler();
    private List<AdvertInfoBean.Data> dataList;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            enterHomePage();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_download);
        initView();
        getData();
    }

    private void initView() {
        tvStadiumName = findViewById(R.id.tv_stadium_name);
        tvStadiumWelcome = findViewById(R.id.tv_stadium_welcome);
        tvStadiumName2 = findViewById(R.id.tv_stadium_name2);
        llDownload = findViewById(R.id.ll_download);
        btnDown = findViewById(R.id.btn_down);
        llDownloading = findViewById(R.id.ll_downloading);
        rvDowning = findViewById(R.id.rv_downing);
        llDownloadNone = findViewById(R.id.ll_download_none);
    }


    private void getData() {
        Map<String, String> body = new HashMap<>();
        showLoadingDialog();
        HttpHelper.get(Api.HOST + Api.API_ADVERT_INFO, body, new HttpCallback<AdvertInfoBean>() {
            @Override
            protected void onSuccess(AdvertInfoBean data) {
                hideLoadingDialog();
                Log.e("ResourceDownloadActivity", " data : " + data.toString());
                if (data.isSuccessful() && data.getData() != null) {
                    checkData(data.getData());
                } else {
                    ToastUtil.showShortCenter(data.getMsg());
                }
            }

            @Override
            protected void onFailure(String error) {
                hideLoadingDialog();
                ToastUtil.showShortCenter(error);
            }
        });
    }

    private void checkData(List<AdvertInfoBean.Data> data) {
        videoUrls.clear();
        if (data == null || data.size() == 0) return;
        this.dataList = data;
        AdvertInfoBean.Data temp = data.get(0);
        tvStadiumName.setText(temp.merchantName);
        tvStadiumName2.setText(temp.merchantName);
        tvStadiumWelcome.setText(temp.merchantName + "欢迎您!");


//        for (AdvertInfoBean.Data temp : dataList) {
//            if (temp.advertType == 2) {
//                videoUrls.add(temp.video);
//            }
//        }

        if (videoUrls.size() > 0) {
            llDownload.setVisibility(View.VISIBLE);
            llDownloading.setVisibility(View.GONE);
            llDownloadNone.setVisibility(View.GONE);
        } else {
            llDownload.setVisibility(View.GONE);
            llDownloading.setVisibility(View.GONE);
            llDownloadNone.setVisibility(View.VISIBLE);
            handler.postDelayed(runnable, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void enterHomePage() {
        Intent intent = new Intent(ResourceDownloadActivity.this, StadiumPageActivity.class);
        if (dataList != null) {
            intent.putExtra(Constants.STADIUM_DATA, (Serializable) dataList);
        }
        startActivity(intent);
        finish();
    }
}
