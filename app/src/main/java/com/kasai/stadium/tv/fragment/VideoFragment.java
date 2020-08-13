package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.bean.VideoInfoBean;
import com.kasai.stadium.tv.dao.VideoDao;
import com.kasai.stadium.tv.dao.bean.VideoBean;
import com.kasai.stadium.tv.utils.MD5Util;
import com.yuong.media.player.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * 视频
 */
public class VideoFragment extends BaseFragment {

    private IjkVideoView videoView;
    private FragmentChangeListener listener;
    private Handler handler;

    //    private String path = "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/test/upload_file/file/20200703/20200703153425055871.mp4";
    private String url;
    private VideoInfoBean videoInfoBean;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (listener != null) {
                listener.onNext();
            }
        }
    };

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
        // videoView.initPlayer();
        //initListener();
    }

    private void initListener() {
        videoView.setErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                nextPage();
                return false;
            }
        });
        videoView.setCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                nextPage();
            }
        });
    }

    //TODO
    @Override
    public void onUserVisible() {
        super.onUserVisible();
        Log.e(TAG, "*****onUserVisible*****");
        nextPage();
    }


    @Override
    public void loadData() {
        super.loadData();
        // loadVideo();
        nextPage();
    }

    @Override
    public void onDestroy() {
        if (videoView != null) {
//            videoView.stop();
//            videoView.release();
//            videoView.destroyPlayer();
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void bindHandler(Handler handler) {
        this.handler = handler;
    }

//    private void nextPage() {
//        if (listener != null) {
//            listener.onNext();
//        }
//    }

    private void nextPage() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(runnable, 8000);
        }
    }

    private void loadVideo() {
        if (!TextUtils.isEmpty(url)) {
            String fileName = MD5Util.getMD5(url) + ".mp4";
            VideoBean video = VideoDao.getInstance(getActivity()).getVideo(fileName);
            if (video != null && !TextUtils.isEmpty(video.path)) {
                Log.e(TAG, "加载本地视频.....");
                videoView.setVideoPath(video.path);
            } else {
                Log.e(TAG, "加载网络视频.....");
                videoView.setVideoPath(url);
            }
            videoView.start();
        }
    }
}
