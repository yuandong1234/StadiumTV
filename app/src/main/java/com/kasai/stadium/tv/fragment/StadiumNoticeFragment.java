package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.bean.StadiumNoticeBean;
import com.kasai.stadium.tv.widget.HtmlView;

public class StadiumNoticeFragment extends BaseFragment {
    private TextView tvStadiumName;
    private TextView tvStadiumWelcome;
    private HtmlView htmlView;
    private StadiumNoticeBean noticeBean;

    public static StadiumNoticeFragment newInstance(StadiumNoticeBean noticeBean) {
        StadiumNoticeFragment fragment = new StadiumNoticeFragment();
        Bundle args = new Bundle();
        args.putSerializable("notice", noticeBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        noticeBean = (StadiumNoticeBean) getArguments().getSerializable("notice");
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
        if (noticeBean != null) {
            tvStadiumName.setText(noticeBean.getMerchantName());
            tvStadiumWelcome.setText(noticeBean.getMerchantName() + "欢迎您!");
            if (!TextUtils.isEmpty(noticeBean.content)) {
                htmlView.loadHtml(htmlView.getHtmlData(noticeBean.content));
            }
        }
    }
}
