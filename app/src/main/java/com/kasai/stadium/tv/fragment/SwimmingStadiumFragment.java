package com.kasai.stadium.tv.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.widget.LineChartView;

public class SwimmingStadiumFragment extends BaseFragment {
    private TextView tvStadiumName;
    private TextView tvStadiumWelcome;
    private ImageView ivStadiumImage;
    private TextView tvDate;
    private TextView tvWeek;
    private TextView tvLunarCalendar;
    private TextView tvPersonalNum;
    private TextView tvWaterTemperature;
    private TextView tvPh;
    private TextView tvCl;
    private TextView tvLockerUnusedNumber;
    private TextView tvLockerUsedNumber;
    private LineChartView chartView;

    public static SwimmingStadiumFragment newInstance() {
        SwimmingStadiumFragment fragment = new SwimmingStadiumFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int intLayoutId() {
        return R.layout.fragment_swimming_stadium;
    }

    @Override
    public void intView() {
        tvStadiumName = view.findViewById(R.id.tv_stadium_name);
        tvStadiumWelcome = view.findViewById(R.id.tv_stadium_welcome);
        ivStadiumImage = view.findViewById(R.id.iv_stadium_image);
        tvDate = view.findViewById(R.id.tv_date);
        tvWeek = view.findViewById(R.id.tv_week);
        tvLunarCalendar = view.findViewById(R.id.tv_lunar_calendar);
        tvPersonalNum = view.findViewById(R.id.tv_personal_num);
        tvWaterTemperature = view.findViewById(R.id.tv_water_temperature);
        tvPh = view.findViewById(R.id.tv_ph);
        tvCl = view.findViewById(R.id.tv_cl);
        tvLockerUnusedNumber = view.findViewById(R.id.tv_locker_unused_number);
        tvLockerUsedNumber = view.findViewById(R.id.tv_locker_used_number);
        chartView = view.findViewById(R.id.chartView);

        int[] values = {400, 500, 600, 300, 200, 100, 800};
        int[] times = {60 * 60, 60 * 60 * 3, 60 * 60 * 5, 60 * 60 * 8, 60 * 60 * 12, 60 * 60 * 18, 60 * 60 * 19};
        chartView.setData(values, times);
    }
}
