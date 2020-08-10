package com.kasai.stadium.tv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.SpaceNumberAdapter;
import com.kasai.stadium.tv.widget.HtmlView;

public class StadiumPageActivity extends AppCompatActivity {

    private RecyclerView rvAvailableSpace;
    private RecyclerView rvService;
    private SpaceNumberAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stadium_notice);
        //initView();
//        initServiceView();
        initNoticeView();
    }

//    private void initView() {
////        rvAvailableSpace = findViewById(R.id.rv_available_space1);
////        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
////        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
////        rvAvailableSpace.setLayoutManager(layoutManager);
////        rvAvailableSpace.addItemDecoration(new HorizontalGridSpaceItemDecoration(5, DensityUtil.dip2px(this, 5)));
////        adapter = new SpaceNumberAdapter(this);
////        rvAvailableSpace.setAdapter(adapter);
////    }

//    private void initServiceView() {
//        rvService = findViewById(R.id.rv_service);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
//        rvService.setLayoutManager(layoutManager);
//        rvService.addItemDecoration(new VerticalGridSpaceItemDecoration(3, DensityUtil.dip2px(this, 40),
//                DensityUtil.dip2px(this, 20),false));
//        OnlineServiceAdapter adapter = new OnlineServiceAdapter(this);
//        rvService.setAdapter(adapter);
//    }

    private void initNoticeView() {
        HtmlView htmlView = findViewById(R.id.htmlView);
        String html = "<p>0000000000000000000000000000000</p>";
        String html2 = "<p style=\"color: #ff0000\">1、每票只限一人使用,不设门票退换和离馆再入馆服务,出馆后再进馆需要重新购票。</p>";
        htmlView.loadHtml(htmlView.getHtmlData(html2));
    }
}
