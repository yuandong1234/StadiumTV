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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.activity.StadiumPageActivity;
import com.kasai.stadium.tv.adapter.SpaceNumberAdapter;
import com.kasai.stadium.tv.bean.GymnasiumBean;
import com.kasai.stadium.tv.utils.DensityUtil;
import com.kasai.stadium.tv.widget.HorizontalGridSpaceItemDecoration;
import com.kasai.stadium.tv.widget.ProgressView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 体育馆
 */
public class GymnasiumFragment extends BaseFragment {
    private TextView tvGymnasiumName;
    private TextView tvGymnasiumWelcome;
    private ImageView ivGymnasiumImage;
    private ImageView ivCond;
    private TextView tvCondTemp;
    private TextView tvCondDesc;
    private TextView tvDate;
    private TextView tvWeek;
    private TextView tvLunarCalendar;
    private TextView tvPersonalNum;
    private LinearLayout rlIndoorTemperature;
    private TextView tvIndoorTemperature;
    private LinearLayout rlTemperature;
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

    private GymnasiumBean gymnasiumBean;
    private int disPlayNumber;
    private boolean isCountDowning;

    public static GymnasiumFragment newInstance(GymnasiumBean gymnasiumBean) {
        GymnasiumFragment fragment = new GymnasiumFragment();
        Bundle args = new Bundle();
        args.putSerializable("gymnasium", gymnasiumBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FragmentChangeListener) activity;
        gymnasiumBean = (GymnasiumBean) getArguments().getSerializable("gymnasium");
    }

    @Override
    public int intLayoutId() {
        return R.layout.fragment_gymnasium;
    }

    @Override
    protected boolean isLaunchLazyMode() {
        return true;
    }

    @Override
    public void intView() {
        tvGymnasiumName = view.findViewById(R.id.tv_gymnasium_name);
        tvGymnasiumWelcome = view.findViewById(R.id.tv_gymnasium_welcome);
        ivGymnasiumImage = view.findViewById(R.id.iv_gymnasium_image);
        ivCond = view.findViewById(R.id.iv_cond);
        tvCondTemp = view.findViewById(R.id.tv_cond_temp);
        tvCondDesc = view.findViewById(R.id.tv_cond_desc);
        tvDate = view.findViewById(R.id.tv_date);
        tvWeek = view.findViewById(R.id.tv_week);
        tvLunarCalendar = view.findViewById(R.id.tv_lunar_calendar);
        tvPersonalNum = view.findViewById(R.id.tv_personal_num);
        rlIndoorTemperature = view.findViewById(R.id.rl_indoor_temperature);
        tvIndoorTemperature = view.findViewById(R.id.tv_indoor_temperature);
        rlTemperature = view.findViewById(R.id.rl_temperature);
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

        rlSport1.setVisibility(View.GONE);
        rlSport2.setVisibility(View.GONE);
        rlSport3.setVisibility(View.GONE);
//        test();
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
        if (gymnasiumBean != null) {
            tvGymnasiumName.setText(gymnasiumBean.getMerchantName());
            tvGymnasiumWelcome.setText(gymnasiumBean.getMerchantName() + "欢迎您!");
            setWeatherData();
            tvDate.setText(gymnasiumBean.getDate());
            tvWeek.setText(gymnasiumBean.getWeek());
            tvLunarCalendar.setText(gymnasiumBean.getChinaDate());
            tvPersonalNum.setText(gymnasiumBean.getPeopleNumber());
            rlIndoorTemperature.setVisibility(TextUtils.isEmpty(gymnasiumBean.getIndoorTemperature()) ? View.GONE : View.VISIBLE);
            tvIndoorTemperature.setText(gymnasiumBean.getIndoorTemperature());
            rlTemperature.setVisibility(TextUtils.isEmpty(gymnasiumBean.getOutdoorTemperature()) ? View.GONE : View.VISIBLE);
            tvTemperature.setText(gymnasiumBean.getOutdoorTemperature());
            List<GymnasiumBean.Sport> sports = gymnasiumBean.getSportList();
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

    private void showSportOne(GymnasiumBean.Sport data) {
        rlSport1.setVisibility(View.VISIBLE);
        setSportImage(ivSport1, data.sportId);
        tvSport1.setText(data.sportName);
        tvAvailableNumber.setText(data.fieldNum);
        if (!TextUtils.isEmpty(data.percent)) {
            progressView1.setValue(Double.parseDouble(data.percent), 100);
        }
        List<String> list = getFiledNumberList(data.fieldName);
        int spanCount = getSpanCount(list.size());
        if (spanCount > 0) {
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            rvAvailableSpace1.setLayoutManager(layoutManager);
            rvAvailableSpace1.addItemDecoration(new HorizontalGridSpaceItemDecoration(spanCount, DensityUtil.dip2px(getActivity(), 5)));
            SpaceNumberAdapter adapter = new SpaceNumberAdapter(getActivity(), disPlayNumber);
            rvAvailableSpace1.setAdapter(adapter);
            adapter.setData(list);
        }
    }

    private void showSportSecond(GymnasiumBean.Sport data) {
        rlSport2.setVisibility(View.VISIBLE);
        setSportImage(ivSport2, data.sportId);
        tvSport2.setText(data.sportName);
        tvAvailableNumber2.setText(data.fieldNum);
        if (!TextUtils.isEmpty(data.percent)) {
            progressView2.setValue(Double.parseDouble(data.percent), 100);
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

    private void showSportThird(GymnasiumBean.Sport data) {
        rlSport3.setVisibility(View.VISIBLE);
        setSportImage(ivSport3, data.sportId);
        tvSport3.setText(data.sportName);
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

//    private void test() {
//        rlSport1.setVisibility(View.VISIBLE);
//        rlSport2.setVisibility(View.VISIBLE);
//        rlSport3.setVisibility(View.VISIBLE);
//        int spanCount = 5;
//        progressView1.setValue(50,100);
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
//        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        rvAvailableSpace1.setLayoutManager(layoutManager);
//        rvAvailableSpace1.addItemDecoration(new HorizontalGridSpaceItemDecoration(spanCount, DensityUtil.dip2px(getActivity(), 5)));
//        SpaceNumberAdapter adapter = new SpaceNumberAdapter(getActivity(), 3);
//        rvAvailableSpace1.setAdapter(adapter);
//    }

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

    private void setSportImage(ImageView imageView, String id) {
        if (TextUtils.isEmpty(id)) {
            imageView.setBackgroundResource(R.mipmap.ic_swim);
            return;
        }
        switch (id.trim()) {
            case "4028f0a2556c04b401556c07d52b0007"://排球
                imageView.setBackgroundResource(R.mipmap.ic_volleyball);
                break;
            case "4028f0ce5551abf3015551b0aae50001"://羽毛球
                imageView.setBackgroundResource(R.mipmap.ic_badminton);
                break;
            case "4028f0ce5551abf3015551b42ebd0004"://篮球
                imageView.setBackgroundResource(R.mipmap.ic_basketball);
                break;
            case "4028f0ce5551abf3015551b476920006"://足球
                imageView.setBackgroundResource(R.mipmap.ic_football);
                break;
            case "4028f0ce5551abf3015551b4a7820008"://乒乓球
                imageView.setBackgroundResource(R.mipmap.ic_pingpong);
                break;
            case "4028f0ce5551abf3015551b4dfc3000a"://网球
                imageView.setBackgroundResource(R.mipmap.ic_tennis);
                break;
            case "4028f0ce5551abf3015551b53b48000c"://游泳
                imageView.setBackgroundResource(R.mipmap.ic_swim);
                break;
            case "2c9180835cae7231015d10860674384b"://气排球
                imageView.setBackgroundResource(R.mipmap.ic_gas_volleyball);
                break;
            case "2c9180835d4a9711015d63aae8795047"://门球
                imageView.setBackgroundResource(R.mipmap.ic_gete_ball);
                break;
            case "2c918083613fbf870161400c222800b7"://跑步
                imageView.setBackgroundResource(R.mipmap.ic_running);
                break;
            case "2c9180845fdca8f1015fe190b96d004d"://健身
                imageView.setBackgroundResource(R.mipmap.ic_fitness);
                break;
            case "2c9180845fdca8f1015fe190fb82004e"://瑜伽
                imageView.setBackgroundResource(R.mipmap.ic_yoga);
                break;
            case "4028f0ce5551abf3015561b53b48001f"://高尔夫
                imageView.setBackgroundResource(R.mipmap.ic_golf);
                break;
            case "2c91808263d9db2f0163edd7f5950007"://潜水
                imageView.setBackgroundResource(R.mipmap.ic_swim);
                break;
            case "2c9180835b60be34015b610f798801e7"://水上乐园
                imageView.setBackgroundResource(R.mipmap.ic_aqualand);
                break;
//            case "297edff8565431fc01566801fdc40047"://全场通用 TODO 图片暂时未提供
//                break;
            default://默认 TODO 图片暂时未提供
                imageView.setBackgroundResource(R.mipmap.ic_swim);
                break;
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
