package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.SpaceNumberAdapter;
import com.kasai.stadium.tv.bean.StadiumBean;
import com.kasai.stadium.tv.utils.DensityUtil;
import com.kasai.stadium.tv.widget.HorizontalGridSpaceItemDecoration;
import com.kasai.stadium.tv.widget.ProgressView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 体育场
 */
public class StadiumFragment extends BaseFragment {
    private TextView tvStadiumName;
    private TextView tvStadiumWelcome;
    private ImageView ivStadiumImage;
    private TextView tvDate;
    private TextView tvWeek;
    private TextView tvLunarCalendar;
    private ImageView ivPersonalNum;
    private TextView tvPeopleNumber;
    private TextView tvTemperature;
    private RelativeLayout rlSport;
    private ImageView ivSport;
    private TextView tvSportName;
    private TextView tvAvailableNumber;
    private ProgressView progressView;
    private RecyclerView rvAvailableSpace;
    private RelativeLayout rlSport2;
    private ImageView ivSport2;
    private TextView tvSportName2;
    private TextView tvAvailableNumber2;
    private ProgressView progressView2;
    private RecyclerView rvAvailableSpace2;
    private RelativeLayout rlSport3;
    private ImageView ivSport3;
    private TextView tvSportName3;
    private TextView tvAvailableNumber3;
    private ProgressView progressView3;
    private RecyclerView rvAvailableSpace3;

    private StadiumBean stadiumBean;
    private int disPlayNumber;

    public static StadiumFragment newInstance(StadiumBean stadiumBean) {
        StadiumFragment fragment = new StadiumFragment();
        Bundle args = new Bundle();
        args.putSerializable("stadium", stadiumBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FragmentChangeListener) activity;
        stadiumBean = (StadiumBean) getArguments().getSerializable("stadium");
    }


    @Override
    public int intLayoutId() {
        return R.layout.fragment_stadium;
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
        tvDate = view.findViewById(R.id.tv_date);
        tvWeek = view.findViewById(R.id.tv_week);
        tvLunarCalendar = view.findViewById(R.id.tv_lunar_calendar);
        ivPersonalNum = view.findViewById(R.id.iv_personal_num);
        tvPeopleNumber = view.findViewById(R.id.tv_people_number);
        tvTemperature = view.findViewById(R.id.tv_temperature);
        ivSport = view.findViewById(R.id.iv_sport);
        tvSportName = view.findViewById(R.id.tv_sport_name);
        tvAvailableNumber = view.findViewById(R.id.tv_available_number);
        progressView = view.findViewById(R.id.progress_view);
        rvAvailableSpace = view.findViewById(R.id.rv_available_space);
        rlSport = view.findViewById(R.id.rl_sport);
        rlSport2 = view.findViewById(R.id.rl_sport2);
        ivSport2 = view.findViewById(R.id.iv_sport2);
        tvSportName2 = view.findViewById(R.id.tv_sport_name2);
        tvAvailableNumber2 = view.findViewById(R.id.tv_available_number2);
        progressView2 = view.findViewById(R.id.progress_view2);
        rvAvailableSpace2 = view.findViewById(R.id.rv_available_space2);
        rlSport3 = view.findViewById(R.id.rl_sport3);
        ivSport3 = view.findViewById(R.id.iv_sport3);
        tvSportName3 = view.findViewById(R.id.tv_sport_name3);
        tvAvailableNumber3 = view.findViewById(R.id.tv_available_number3);
        progressView3 = view.findViewById(R.id.progress_view3);
        rvAvailableSpace3 = view.findViewById(R.id.rv_available_space3);

        rlSport.setVisibility(View.GONE);
        rlSport2.setVisibility(View.GONE);
        rlSport3.setVisibility(View.GONE);
    }

    private void showSportOne(StadiumBean.Sport data) {
        rlSport.setVisibility(View.VISIBLE);
        tvSportName.setText(data.sportName);
        tvAvailableNumber.setText(data.fieldNum);
        if (!TextUtils.isEmpty(data.percent)) {
            progressView.setValue(Double.parseDouble(data.percent), 100d);
        }
        List<String> list = getFiledNumberList(data.fieldName);
        int spanCount = getSpanCount(list.size());
        if (spanCount > 0) {
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            rvAvailableSpace.setLayoutManager(layoutManager);
            rvAvailableSpace.addItemDecoration(new HorizontalGridSpaceItemDecoration(spanCount, DensityUtil.dip2px(getActivity(), 5)));
            SpaceNumberAdapter adapter = new SpaceNumberAdapter(getActivity(), disPlayNumber);
            rvAvailableSpace.setAdapter(adapter);
            adapter.setData(list);
        }
    }

    private void showSportSecond(StadiumBean.Sport data) {
        rlSport2.setVisibility(View.VISIBLE);
        tvSportName2.setText(data.sportName);
        tvAvailableNumber2.setText(data.fieldNum);
        if (!TextUtils.isEmpty(data.percent)) {
            progressView2.setValue(Double.parseDouble(data.percent), 100d);
        }
        List<String> list = getFiledNumberList(data.fieldName);
        int spanCount = getSpanCount(list.size());
        if (spanCount > 0) {
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            rvAvailableSpace2.setLayoutManager(layoutManager);
            rvAvailableSpace2.addItemDecoration(new HorizontalGridSpaceItemDecoration(spanCount, DensityUtil.dip2px(getActivity(), 5)));
            SpaceNumberAdapter adapter = new SpaceNumberAdapter(getActivity(), disPlayNumber);
            rvAvailableSpace2.setAdapter(adapter);
            adapter.setData(list);
        }
    }

    private void showSportThird(StadiumBean.Sport data) {
        rlSport3.setVisibility(View.VISIBLE);
        tvSportName3.setText(data.sportName);
        tvAvailableNumber3.setText(data.fieldNum);
        if (!TextUtils.isEmpty(data.percent)) {
            progressView3.setValue(Double.parseDouble(data.percent), 100d);
        }
        List<String> list = getFiledNumberList(data.fieldName);
        int spanCount = getSpanCount(list.size());
        if (spanCount > 0) {
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            rvAvailableSpace3.setLayoutManager(layoutManager);
            rvAvailableSpace3.addItemDecoration(new HorizontalGridSpaceItemDecoration(spanCount, DensityUtil.dip2px(getActivity(), 5)));
            SpaceNumberAdapter adapter = new SpaceNumberAdapter(getActivity(), disPlayNumber);
            rvAvailableSpace3.setAdapter(adapter);
            adapter.setData(list);
        }
    }

    private List<String> getFiledNumberList(String filedNumber) {
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(filedNumber)) {
            String[] array = filedNumber.split(",");
            Collections.addAll(list, array);
        }
        return list;
    }

    private int getSpanCount(int itemSize) {
        int spanCount = 0;
        switch (disPlayNumber) {
            case 1:
            case 2:
                if (itemSize > 5) {
                    spanCount = 4;
                } else {
                    spanCount = itemSize;
                }
                break;
            case 3:
                if (itemSize > 8) {
                    spanCount = 5;
                } else if (itemSize > 5) {
                    spanCount = 4;
                } else {
                    spanCount = itemSize;
                }
                break;
        }
        return spanCount;
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

    private void initData(){
        if (stadiumBean != null) {
            tvStadiumName.setText(stadiumBean.getMerchantName());
            tvStadiumWelcome.setText(stadiumBean.getMerchantName() + "欢迎您!");
            tvDate.setText(stadiumBean.getDate());
            tvWeek.setText(stadiumBean.getWeek());
            tvLunarCalendar.setText(stadiumBean.getChinaDate());
            tvPeopleNumber.setText(stadiumBean.getPeopleNumber());
            tvTemperature.setText(stadiumBean.getTemperature());
            List<StadiumBean.Sport> sports = stadiumBean.getSportList();
            if (sports != null) {
                if (sports.size() == 1) {
                    disPlayNumber = 1;
                    showSportOne(sports.get(0));
                } else if (sports.size() == 2) {
                    disPlayNumber = 2;
                    showSportOne(sports.get(0));
                    showSportSecond(sports.get(1));
                } else if (sports.size() >= 3) {
                    disPlayNumber = 3;
                    showSportOne(sports.get(0));
                    showSportSecond(sports.get(1));
                    showSportThird(sports.get(2));
                }
            }
            setOnlyLoadOnce(true);
        }
    }

    public void bindHandler(Handler handler) {
        this.handler = handler;
    }

    private void nextPage() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(runnable, 8000);
        }
    }

    private FragmentChangeListener listener;
    private Handler handler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (listener != null) {
                listener.onNext();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setOnlyLoadOnce(false);
//        if (handler != null) {
//            handler.removeCallbacksAndMessages(null);
//        }
    }
}
