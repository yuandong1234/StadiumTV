package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.activity.StadiumPageActivity;
import com.kasai.stadium.tv.adapter.OnlineServiceAdapter;
import com.kasai.stadium.tv.bean.OnlineServiceBean;
import com.kasai.stadium.tv.utils.DensityUtil;
import com.kasai.stadium.tv.widget.VerticalGridSpaceItemDecoration;

import java.util.List;

/**
 * 客服
 */
public class OnlineServiceFragment extends BaseFragment {
    private TextView tvStadiumName;
    private TextView tvStadiumWelcome;
    private TextView tvOnlineService;
    private RecyclerView rvService;
    private OnlineServiceAdapter adapter;

    private OnlineServiceBean serviceBean;
    private boolean isCountDowning;

    public static OnlineServiceFragment newInstance(OnlineServiceBean serviceBean) {
        OnlineServiceFragment fragment = new OnlineServiceFragment();
        Bundle args = new Bundle();
        args.putSerializable("service", serviceBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FragmentChangeListener) activity;
        serviceBean = (OnlineServiceBean) getArguments().getSerializable("service");
    }

    @Override
    public int intLayoutId() {
        return R.layout.fragment_online_service;
    }

    @Override
    protected boolean isLaunchLazyMode() {
        return true;
    }

    @Override
    public void intView() {
        tvStadiumName = view.findViewById(R.id.tv_stadium_name);
        tvStadiumWelcome = view.findViewById(R.id.tv_stadium_welcome);
        tvOnlineService = view.findViewById(R.id.tv_online_service);
        rvService = view.findViewById(R.id.rv_service);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        rvService.setLayoutManager(layoutManager);
        rvService.addItemDecoration(new VerticalGridSpaceItemDecoration(3, DensityUtil.dip2px(getActivity(), 40),
                DensityUtil.dip2px(getActivity(), 20), false));
        adapter = new OnlineServiceAdapter(getActivity());
        rvService.setAdapter(adapter);
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Log.e(TAG, "*****onUserVisible*****");
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        nextPage();
    }


    @Override
    public void loadData() {
        super.loadData();
        Log.e(TAG, "*****loadData*****");
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        initData();
        nextPage();
    }

    private void initData() {
        if (serviceBean != null) {
            tvStadiumName.setText(serviceBean.getMerchantName());
            tvStadiumWelcome.setText(serviceBean.getMerchantName() + "欢迎您!");
            tvOnlineService.setText(serviceBean.getTeamInfo());
            List<OnlineServiceBean.Service> serviceList = serviceBean.getServiceList();
            if (serviceList != null && serviceList.size() > 0) {
                adapter.setData(serviceList);
            }
            setOnlyLoadOnce(true);
        }
    }

    public void bindHandler(Handler handler) {
        this.handler = handler;
    }

    private synchronized void nextPage() {
        if (handler != null && StadiumPageActivity.IS_AUTO_PLAY) {
            if (!isCountDowning) {
                isCountDowning = true;
                handler.postDelayed(runnable, 8000);
            }
        }
    }

    private FragmentChangeListener listener;
    private Handler handler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (listener != null && isCountDowning) {
                isCountDowning = false;
                listener.onNext();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setOnlyLoadOnce(false);
        isCountDowning = false;
    }
}
