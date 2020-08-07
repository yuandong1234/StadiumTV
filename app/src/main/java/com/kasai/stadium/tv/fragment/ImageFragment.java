package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kasai.stadium.tv.R;

/**
 * 图片
 */
public class ImageFragment extends BaseFragment {
    private ImageView imageView;
    private String imageUrl = "http://saas-resources.52jiayundong.com/test/upload_file/file/20200624/20200624190917536451.jpg";
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

    public static ImageFragment newInstance() {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FragmentChangeListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        initView(view);
        return view;
    }

    @Override
    public void loadData() {
        super.loadData();
        Log.e(TAG, "*****loadData*****");
        loadImage(imageView, imageUrl);
        nextPage();
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Log.e(TAG, "*****onUserVisible*****");
        nextPage();
    }

    private void initView(View view) {
        imageView = view.findViewById(R.id.image);
    }

    private void loadImage(ImageView imageView, String url) {
        Glide.with(this).load(url).into(imageView);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
