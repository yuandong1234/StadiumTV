package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.activity.StadiumPageActivity;
import com.kasai.stadium.tv.bean.VideoInfoBean;
import com.kasai.stadium.tv.dao.FileDao;
import com.kasai.stadium.tv.dao.bean.FileBean;
import com.kasai.stadium.tv.utils.MD5Util;
import com.yuong.media.player.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * 视频
 */
public class VideoFragment extends BaseFragment {

    private IjkVideoView videoView;
    private FragmentChangeListener listener;
    private String url;
    private VideoInfoBean videoInfoBean;

    public static VideoFragment newInstance(VideoInfoBean videoBean) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putSerializable("video", videoBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FragmentChangeListener) activity;
        videoInfoBean = (VideoInfoBean) getArguments().getSerializable("video");
        if (videoInfoBean != null) {
            url = videoInfoBean.getVideo();
        }
    }

    @Override
    protected boolean isLaunchLazyMode() {
        return true;
    }

    @Override
    public int intLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public void intView() {
        videoView = view.findViewById(R.id.video_view);
    }

    private void initListener() {
        videoView.setErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                destroyPlayView();
                nextPage();
                return true;
            }
        });
        videoView.setCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                destroyPlayView();
                nextPage();
            }
        });
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Log.e(TAG, "*****onUserVisible*****");
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }


    @Override
    public void loadData() {
        super.loadData();
        Log.e(TAG, "*****loadData*****");
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        loadVideo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setOnlyLoadOnce(false);
        destroyPlayView();
    }

    private void nextPage() {
        if (listener != null && StadiumPageActivity.IS_AUTO_PLAY) {
            listener.onNext();
        }
    }

    private void initPlayView() {
        videoView.initPlayer();
        initListener();
    }

    private void destroyPlayView() {
        if (videoView != null) {
            videoView.setVisibility(View.GONE);
            videoView.setRenderViewVisible(false);
            videoView.stop();
            videoView.release();
            videoView.destroyPlayer();
        }
    }


    private void loadVideo() {
        if (!TextUtils.isEmpty(url)) {
            String newUrl = convertVideoUrl(url);
            String fileName = MD5Util.getMD5(newUrl) + ".mp4";
            FileBean file = FileDao.getInstance(getActivity()).getFile(fileName);
            if (file != null && !TextUtils.isEmpty(file.path)) {
                Log.e(TAG, "加载本地视频..... : " + file.name);
                Log.e(TAG, "加载本地视频..... : " + file.path);
                videoView.setVideoPath(file.path);
            } else {
                Log.e(TAG, "加载网络视频.....");
                videoView.setVideoPath(newUrl);
            }
            initPlayView();
            videoView.setVisibility(View.VISIBLE);
            videoView.setRenderViewVisible(true);
            videoView.start();
        }

        //TODO 测试
//        videoView.setVisibility(View.VISIBLE);
//        String url = "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/prod/upload_file/file/20200717/20200717173242162638.mp4";
//        videoView.setVideoPath(url);
//        videoView.start();
    }

    private String convertVideoUrl(String url) {
        if (TextUtils.isEmpty(url)) return null;
        String flag = "http://saas-resources.52jiayundong.com";
        if (url.startsWith(flag)) {
            return url.replace(flag, "https://venue-saas.oss-cn-shenzhen.aliyuncs.com");
        }
        return null;
    }

}
