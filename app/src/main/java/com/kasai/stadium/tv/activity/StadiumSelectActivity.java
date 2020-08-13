package com.kasai.stadium.tv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.StadiumListAdapter;
import com.kasai.stadium.tv.bean.VenueBean;
import com.kasai.stadium.tv.bean.VenueListBean;
import com.kasai.stadium.tv.constants.Api;
import com.kasai.stadium.tv.constants.Constants;
import com.kasai.stadium.tv.http.HttpCallback;
import com.kasai.stadium.tv.http.HttpHelper;
import com.kasai.stadium.tv.utils.DensityUtil;
import com.kasai.stadium.tv.utils.ToastUtil;
import com.kasai.stadium.tv.utils.UserInfoUtil;
import com.kasai.stadium.tv.widget.VerticalGridSpaceItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StadiumSelectActivity extends BaseActivity implements StadiumListAdapter.onStadiumSelectedListener {
    private TextView tvStadiumName;
    private RecyclerView rvStadium;

    private StadiumListAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium_select);
        initView();
        loadData();
    }

    private void initView() {
        tvStadiumName = findViewById(R.id.tv_stadium_name);
        rvStadium = findViewById(R.id.rv_stadium);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        rvStadium.setLayoutManager(layoutManager);
        rvStadium.addItemDecoration(new VerticalGridSpaceItemDecoration(4, DensityUtil.dip2px(this, 10),
                DensityUtil.dip2px(this, 10), false));
        adapter = new StadiumListAdapter(this);
        adapter.setListener(this);
        rvStadium.setAdapter(adapter);
    }

    private void loadData() {
        showLoadingDialog();
        Map<String, String> body = new HashMap<>();
        HttpHelper.get(Api.HOST + Api.API_VENUE_LIST, body, new HttpCallback<VenueListBean>() {
            @Override
            protected void onSuccess(VenueListBean data) {
                hideLoadingDialog();
                Log.e("StadiumSelectActivity", " data : " + data.toString());
                if (data.isSuccessful() && data.getData() != null) {
                    setStadiumList(data.getData().getVenueList());
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

    private void setStadiumList(List<VenueListBean.Data.Venue> data) {
        if (data != null && data.size() > 0) {
            tvStadiumName.setText(data.get(0).venueName);
            adapter.setData(data);
        }
    }

    @Override
    public void onSelected(String id) {
        getVenueData(id);
    }

    private void getVenueData(String id) {
        showLoadingDialog();
        Map<String, String> body = new HashMap<>();
        body.put("venueId", id);
        HttpHelper.post(Api.HOST + Api.API_VENUE_SELECT, body, new HttpCallback<VenueBean>() {
            @Override
            protected void onSuccess(VenueBean data) {
                hideLoadingDialog();
                Log.e("StadiumSelectActivity", " data : " + data.toString());
                if (data.isSuccessful() && data.getData() != null) {
                    UserInfoUtil.getInstance().putValue(Constants.SP_KEY_USER_TOKEN, data.getData().token);

//                    startActivity(new Intent(StadiumSelectActivity.this, StadiumPageActivity.class));
                    startActivity(new Intent(StadiumSelectActivity.this, ResourceDownloadActivity.class));
                    finish();
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
}
