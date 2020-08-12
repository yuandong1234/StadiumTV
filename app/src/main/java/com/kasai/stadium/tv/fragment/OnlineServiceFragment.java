package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.OnlineServiceAdapter;
import com.kasai.stadium.tv.bean.OnlineServiceBean;
import com.kasai.stadium.tv.utils.DensityUtil;
import com.kasai.stadium.tv.widget.VerticalGridSpaceItemDecoration;

import java.util.List;

public class OnlineServiceFragment extends BaseFragment {
    private TextView tvStadiumName;
    private TextView tvStadiumWelcome;
    private TextView tvOnlineService;
    private RecyclerView rvService;

    private OnlineServiceBean serviceBean;

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
        serviceBean = (OnlineServiceBean) getArguments().getSerializable("service");
    }

    @Override
    public int intLayoutId() {
        return R.layout.fragment_online_service;
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
        OnlineServiceAdapter adapter = new OnlineServiceAdapter(getActivity());
        rvService.setAdapter(adapter);

        if (serviceBean != null) {
            tvStadiumName.setText(serviceBean.getMerchantName());
            tvStadiumWelcome.setText(serviceBean.getMerchantName() + "欢迎您!");
            List<OnlineServiceBean.Service> serviceList = serviceBean.getServiceList();
            if (serviceList != null && serviceList.size() > 0) {
                adapter.setData(serviceList);
            }
        }
    }
}
