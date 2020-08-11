package com.kasai.stadium.tv.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.SpaceNumberAdapter;
import com.kasai.stadium.tv.utils.DensityUtil;
import com.kasai.stadium.tv.widget.HorizontalGridSpaceItemDecoration;
import com.kasai.stadium.tv.widget.ProgressView;

public class GymnasiumFragment extends BaseFragment {
    private TextView tvGymnasiumName;
    private TextView tvGymnasiumWelcome;
    private ImageView ivGymnasiumImage;
    private TextView tvDate;
    private TextView tvWeek;
    private TextView tvLunarCalendar;
    private TextView tvPersonalNum;
    private TextView tvIndoorTemperature;
    private TextView tvTemperature;
    private RelativeLayout rlSport1;
    private ImageView ivSport1;
    private TextView tvSport1;
    private TextView tvAvailableNumber;
    private ProgressView progressView1;
    private RecyclerView rvAvailableSpace1;
    private RelativeLayout rlSport2;
    private ImageView ivSport2;
    private TextView tvSport2;
    private TextView tvAvailableNumber2;
    private ProgressView progressView2;
    private RecyclerView rvAvailableSpace2;
    private RelativeLayout rlSport3;
    private ImageView ivSport3;
    private TextView tvSport3;
    private TextView tvAvailableNumber3;
    private ProgressView progressView3;
    private RecyclerView rvAvailableSpace3;

    private SpaceNumberAdapter adapter;

    public static GymnasiumFragment newInstance() {
        GymnasiumFragment fragment = new GymnasiumFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int intLayoutId() {
        return R.layout.fragment_gymnasium;
    }

    @Override
    public void intView() {
        tvGymnasiumName = view.findViewById(R.id.tv_gymnasium_name);
        tvGymnasiumWelcome = view.findViewById(R.id.tv_gymnasium_welcome);
        ivGymnasiumImage = view.findViewById(R.id.iv_gymnasium_image);
        tvDate = view.findViewById(R.id.tv_date);
        tvWeek = view.findViewById(R.id.tv_week);
        tvLunarCalendar = view.findViewById(R.id.tv_lunar_calendar);
        tvPersonalNum = view.findViewById(R.id.tv_personal_num);
        tvIndoorTemperature = view.findViewById(R.id.tv_indoor_temperature);
        tvTemperature = view.findViewById(R.id.tv_temperature);
        rlSport1 = view.findViewById(R.id.rl_sport1);
        ivSport1 = view.findViewById(R.id.iv_sport1);
        tvSport1 = view.findViewById(R.id.tv_sport1);
        tvAvailableNumber = view.findViewById(R.id.tv_available_number);
        progressView1 = view.findViewById(R.id.progress_view1);
        rvAvailableSpace1 = view.findViewById(R.id.rv_available_space1);
        rlSport2 = view.findViewById(R.id.rl_sport2);
        ivSport2 = view.findViewById(R.id.iv_sport2);
        tvSport2 = view.findViewById(R.id.tv_sport2);
        tvAvailableNumber2 = view.findViewById(R.id.tv_available_number2);
        progressView2 = view.findViewById(R.id.progress_view2);
        rvAvailableSpace2 = view.findViewById(R.id.rv_available_space2);
        rlSport3 = view.findViewById(R.id.rl_sport3);
        ivSport3 = view.findViewById(R.id.iv_sport3);
        tvSport3 = view.findViewById(R.id.tv_sport3);
        tvAvailableNumber3 = view.findViewById(R.id.tv_available_number3);
        progressView3 = view.findViewById(R.id.progress_view3);
        rvAvailableSpace3 = view.findViewById(R.id.rv_available_space3);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvAvailableSpace1.setLayoutManager(layoutManager);
        rvAvailableSpace1.addItemDecoration(new HorizontalGridSpaceItemDecoration(5, DensityUtil.dip2px(getActivity(), 5)));
        adapter = new SpaceNumberAdapter(getActivity());
        rvAvailableSpace1.setAdapter(adapter);
    }
}
