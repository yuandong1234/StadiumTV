package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.activity.StadiumPageActivity;
import com.kasai.stadium.tv.bean.ImageBean;
import com.kasai.stadium.tv.dao.FileDao;
import com.kasai.stadium.tv.dao.bean.FileBean;
import com.kasai.stadium.tv.utils.MD5Util;

import java.io.File;

/**
 * 图片
 */
public class ImageFragment extends BaseFragment {
    private ImageView imageView;
    private String imageUrl = "http://saas-resources.52jiayundong.com/test/upload_file/file/20200624/20200624190917536451.jpg";
    private ImageBean imageBean;

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

    public static ImageFragment newInstance(ImageBean bean) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putSerializable("image", bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FragmentChangeListener) activity;
        imageBean = (ImageBean) getArguments().getSerializable("image");
    }


    @Override
    public int intLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected boolean isLaunchLazyMode() {
        return true;
    }

    @Override
    public void intView() {
        imageView = view.findViewById(R.id.image);
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
        if (imageBean != null && !TextUtils.isEmpty(imageBean.getImage())) {
            loadImage(imageView, imageBean.getImage());
        }
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Log.e(TAG, "*****onUserVisible*****");
    }

    private void loadImage(ImageView imageView, String url) {
        Log.e(TAG, "*****loadImage*****");
        String fileType = getFileType(url);
        String fileName = MD5Util.getMD5(url) + "." + fileType;
        FileBean fileBean = FileDao.getInstance(getActivity()).getFile(fileName);
        if (fileBean != null) {
            File file = new File(fileBean.path);
            if (file.exists()) {
                Log.e(TAG, "加载本地图片..... : " + fileBean.name);
                Log.e(TAG, "加载本地图片..... : " + fileBean.path);
                Glide.with(this).load(file).into(imageView);
            } else {
                Log.e(TAG, "加载网络图片..... ");
                Glide.with(this).load(url).into(imageView);
            }
        } else {
            Log.e(TAG, "加载网络图片..... ");
            Glide.with(this).load(url).into(imageView);
        }
    }

    public void bindHandler(Handler handler) {
        this.handler = handler;
    }

    private void nextPage() {
        if (handler != null && StadiumPageActivity.IS_AUTO_PLAY) {
            handler.postDelayed(runnable, 8000);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setOnlyLoadOnce(false);
    }

    private String getFileType(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }
}
