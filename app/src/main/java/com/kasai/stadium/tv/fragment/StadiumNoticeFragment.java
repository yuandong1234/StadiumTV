package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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
        listener = (FragmentChangeListener) activity;
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

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Log.e(TAG, "*****onUserVisible*****");
        nextPage();
    }



    @Override
    public void loadData() {
        super.loadData();
        nextPage();
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
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
