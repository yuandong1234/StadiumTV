package com.kasai.stadium.tv.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.SpaceNumberAdapter;
import com.kasai.stadium.tv.bean.StadiumBean;
import com.kasai.stadium.tv.utils.DensityUtil;
import com.kasai.stadium.tv.widget.HorizontalGridSpaceItemDecoration;
import com.kasai.stadium.tv.widget.ProgressView;

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


    private SpaceNumberAdapter adapter;

    public static StadiumFragment newInstance(StadiumBean stadiumBean) {
        StadiumFragment fragment = new StadiumFragment();
        Bundle args = new Bundle();
        args.putSerializable("stadium", stadiumBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int intLayoutId() {
        return R.layout.fragment_stadium;
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


        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvAvailableSpace.setLayoutManager(layoutManager);
        rvAvailableSpace.addItemDecoration(new HorizontalGridSpaceItemDecoration(5, DensityUtil.dip2px(getActivity(), 5)));
        adapter = new SpaceNumberAdapter(getActivity());
        rvAvailableSpace.setAdapter(adapter);
    }
}
