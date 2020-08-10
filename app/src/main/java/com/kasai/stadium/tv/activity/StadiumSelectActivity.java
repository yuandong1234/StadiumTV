package com.kasai.stadium.tv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.StadiumListAdapter;
import com.kasai.stadium.tv.utils.DensityUtil;
import com.kasai.stadium.tv.widget.VerticalGridSpaceItemDecoration;

public class StadiumSelectActivity extends AppCompatActivity {
    private TextView tvStadiumName;
    private RecyclerView rvStadium;

    private StadiumListAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium_select);
        initView();
    }

    private void initView() {
        tvStadiumName = findViewById(R.id.tv_stadium_name);
        rvStadium = findViewById(R.id.rv_stadium);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        rvStadium.setLayoutManager(layoutManager);
        rvStadium.addItemDecoration(new VerticalGridSpaceItemDecoration(4, DensityUtil.dip2px(this, 10),
                DensityUtil.dip2px(this, 10), false));
        adapter = new StadiumListAdapter(this);
        rvStadium.setAdapter(adapter);
    }
}
