package com.kasai.stadium.tv.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.widget.HtmlView;

public class StadiumNoticeFragment extends BaseFragment {
    private TextView tvStadiumName;
    private TextView tvStadiumWelcome;
    private HtmlView htmlView;

    public static StadiumNoticeFragment newInstance() {
        StadiumNoticeFragment fragment = new StadiumNoticeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int intLayoutId() {
        return R.layout.fragment_stadium_notice;
    }

    @Override
    public void intView() {
        tvStadiumName = view.findViewById(R.id.tv_stadium_name);
        tvStadiumWelcome = view.findViewById(R.id.tv_stadium_welcome);
        htmlView = view.findViewById(R.id.htmlView);

        String html = "<p style=\"color: #ff0000\">1、每票只限一人使用,不设门票退换和离馆再入馆服务,出馆后再进馆需要重新购票。</p>";
        htmlView.loadHtml(htmlView.getHtmlData(html));
    }
}
