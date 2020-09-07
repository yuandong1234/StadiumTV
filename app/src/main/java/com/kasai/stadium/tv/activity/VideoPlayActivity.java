package com.kasai.stadium.tv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kasai.stadium.tv.R;
import com.yuong.media.player.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoPlayActivity extends BaseActivity {

    private IjkVideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video);
        intView();
    }

    public void intView() {
        videoView = findViewById(R.id.video_view);
        initPlayView();
        //TODO 待测试
        loadVideo();
    }

    private void initPlayView() {
        videoView.initPlayer();
        initListener();
    }

    private void initListener() {
        videoView.setErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                return true;
            }
        });
        videoView.setCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
            }
        });
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
        videoView.setVisibility(View.VISIBLE);
        String url = "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/prod/upload_file/file/20200717/20200717173242162638.mp4";
        videoView.setVideoPath(url);
        videoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyPlayView();
    }
}
