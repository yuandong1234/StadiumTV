package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.activity.StadiumPageActivity;
import com.kasai.stadium.tv.bean.SwimmingStadiumBean;
import com.kasai.stadium.tv.dao.LockerDao;
import com.kasai.stadium.tv.dao.bean.LockerBean;
import com.kasai.stadium.tv.utils.DateUtil;
import com.kasai.stadium.tv.widget.LineChartView;

import java.util.List;

/**
 * 游泳馆
 */
public class SwimmingStadiumFragment extends BaseFragment {
    private TextView tvStadiumName;
    private TextView tvStadiumWelcome;
    private ImageView ivStadiumImage;
    private ImageView ivCond;
    private TextView tvCondTemp;
    private TextView tvCondDesc;
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

    private SwimmingStadiumBean swimmingStadiumBean;
    private boolean isCountDowning;

    public static SwimmingStadiumFragment newInstance(SwimmingStadiumBean swimmingStadiumBean) {
        SwimmingStadiumFragment fragment = new SwimmingStadiumFragment();
        Bundle args = new Bundle();
        args.putSerializable("swimming", swimmingStadiumBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FragmentChangeListener) activity;
        swimmingStadiumBean = (SwimmingStadiumBean) getArguments().getSerializable("swimming");
    }

    @Override
    public int intLayoutId() {
        return R.layout.fragment_swimming_stadium;
    }

    @Override
    protected boolean isLaunchLazyMode() {
        return true;
    }

    @Override
    public void intView() {
        tvStadiumName = view.findViewById(R.id.tv_stadium_name);
        tvStadiumWelcome = view.findViewById(R.id.tv_stadium_welcome);
        ivStadiumImage = view.findViewById(R.id.iv_stadium_image);
        ivCond = view.findViewById(R.id.iv_cond);
        tvCondTemp = view.findViewById(R.id.tv_cond_temp);
        tvCondDesc = view.findViewById(R.id.tv_cond_desc);
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


//        int[] values = {400, 500, 600, 300, 200, 100, 800};
//        int[] times = {60 * 60, 60 * 60 * 3, 60 * 60 * 5, 60 * 60 * 8, 60 * 60 * 12, 60 * 60 * 18, 60 * 60 * 19};
//        chartView.setData(values, times);

    }

    private void setLockerUseState() {
        String currentDate = DateUtil.getCurrentDate(DateUtil.DATE);
        List<LockerBean> lockers = LockerDao.getInstance(getActivity()).getLocker(currentDate);
        if (lockers == null || lockers.size() == 0) return;
//        Log.e("****************", lockers.toString());
        int[] times = new int[lockers.size()];
        int[] unUseValues = new int[lockers.size()];
//        int[] useValues = new int[lockers.size()];
        int[] totalValues = new int[lockers.size()];
        for (int i = 0; i < lockers.size(); i++) {
            LockerBean bean = lockers.get(i);
            times[i] = bean.getTime();
            unUseValues[i] = bean.getUnUserNumber();
//            useValues[i] = bean.getUserNumber();
            totalValues[i] = bean.getUnUserNumber() + bean.getUserNumber();
        }

        int maxValue = getMax(totalValues);
        int maxLimit = String.valueOf(maxValue).length() * 10;
        int space = maxLimit / 5;
        String[] yTitles = new String[6];
        for (int i = 0; i < 6; i++) {
            yTitles[i] = String.valueOf(maxLimit - i * space);
        }
        chartView.setLimitValue(maxLimit, 0);
        chartView.setYtitle(yTitles);
        chartView.setData(unUseValues, times);
    }

    private void saveLockerData(int unUserNumber, int userNumber) {
        LockerBean bean = new LockerBean();
        bean.setUserNumber(userNumber);
        bean.setUnUserNumber(unUserNumber);
        bean.setDate(DateUtil.getCurrentDate(DateUtil.DATE));
        bean.setTime(getSeconds());
        LockerDao.getInstance(getActivity()).saveLocker(bean);
    }

    private int getSeconds() {
        int[] times = DateUtil.getCurrentTime();
        return times[0] * 60 * 60 + times[1] * 60 + times[2];
    }

    private int getMax(int[] array) {
        int max = array[0];
        for (int temp : array) {
            if (max < temp) {
                max = temp;
            }
        }
        return max;
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
        if (swimmingStadiumBean != null) {
            tvStadiumName.setText(swimmingStadiumBean.getMerchantName());
            tvStadiumWelcome.setText(swimmingStadiumBean.getMerchantName() + "欢迎您!");
            setWeatherData();
            tvDate.setText(swimmingStadiumBean.getDate());
            tvWeek.setText(swimmingStadiumBean.getWeek());
            tvLunarCalendar.setText(swimmingStadiumBean.getChinaDate());
            tvPersonalNum.setText(swimmingStadiumBean.getPeopleNumber());
            tvWaterTemperature.setText(swimmingStadiumBean.getWaterTemperature());
            tvPh.setText(swimmingStadiumBean.getPhValue());
            tvCl.setText(swimmingStadiumBean.getChlorineValue());
            tvLockerUnusedNumber.setText(swimmingStadiumBean.getAvailableNum() + "");
            tvLockerUsedNumber.setText(swimmingStadiumBean.getPayReceiveNum() + "");

            saveLockerData(swimmingStadiumBean.getAvailableNum(), swimmingStadiumBean.getPayReceiveNum());

            setLockerUseState();

            setOnlyLoadOnce(true);
        }
    }

    private void setWeatherData() {
        if (StadiumPageActivity.weatherBean != null) {
            tvCondTemp.setText(StadiumPageActivity.weatherBean.tmp + "℃");
            if (StadiumPageActivity.weatherBean.cond != null) {
                String desc = StadiumPageActivity.weatherBean.cond.txt;
                tvCondDesc.setVisibility(View.VISIBLE);
                ivCond.setVisibility(View.VISIBLE);
                tvCondDesc.setText(desc);
                if (desc.contains("晴")) {
                    ivCond.setImageResource(R.mipmap.ic_sunny);
                } else if (desc.contains("云") || desc.contains("阴")) {
                    ivCond.setImageResource(R.mipmap.ic_cloudy);
                } else if (desc.contains("雨")) {
                    ivCond.setImageResource(R.mipmap.ic_rainy);
                }
            } else {
                tvCondDesc.setVisibility(View.GONE);
                ivCond.setVisibility(View.GONE);
            }
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
