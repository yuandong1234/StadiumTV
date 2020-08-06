package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kasai.stadium.tv.R;
import com.yuong.media.player.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * 视频
 */
public class VideoFragment extends BaseFragment {

    private IjkVideoView videoView;
    private FragmentChangeListener listener;

    private String path = "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/test/upload_file/file/20200703/20200703153425055871.mp4";

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FragmentChangeListener) activity;
    }

    @Override
    protected boolean isLaunchLazyMode() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        videoView = view.findViewById(R.id.video_view);
        videoView.initPlayer();
    }

    private void initListener() {
        videoView.setErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                return false;
            }
        });
        videoView.setCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                if (listener != null) {
                    listener.onNext();
                }
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
        videoView.setVideoPath(path);
        videoView.start();
    }

    @Override
    public void onDestroy() {
        if (videoView != null) {
            videoView.stop();
            videoView.release();
            videoView.destroyPlayer();
        }
        super.onDestroy();
    }
}
