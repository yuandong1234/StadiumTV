package com.kasai.stadium.tv.activity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.SectionsPagerAdapter;
import com.kasai.stadium.tv.bean.ImageBean;
import com.kasai.stadium.tv.bean.VideoInfoBean;
import com.kasai.stadium.tv.dao.VideoDao;
import com.kasai.stadium.tv.dao.bean.VideoBean;
import com.kasai.stadium.tv.download.QueueController;
import com.kasai.stadium.tv.fragment.BaseFragment;
import com.kasai.stadium.tv.fragment.ImageFragment;
import com.kasai.stadium.tv.fragment.VideoFragment;
import com.kasai.stadium.tv.utils.MD5Util;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, BaseFragment.FragmentChangeListener {
    private final static String TAG = MainActivity.class.getSimpleName();
    public final static int MSG_DOWNLOAD_START = 10010;
    public final static int MSG_DOWNLOAD_END = 10011;
    private QueueController queueController;

    public String[] urls = {
            "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/prod/upload_file/file/20200717/20200717172117927716.mp4",
            "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/prod/upload_file/file/20200717/20200717173242162638.mp4"
    };

    private String imageUrl = "http://saas-resources.52jiayundong.com/test/upload_file/file/20200706/20200706162559930556.jpg";

    private ViewPager viewPager;
    private TextView tvDownloadStatus;
    private TextView tvLog;
    private List<Fragment> fragments = new ArrayList<>();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // initData();
    }

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        tvDownloadStatus = findViewById(R.id.tv_download_status);
        tvLog = findViewById(R.id.tv_log);
        viewPager.addOnPageChangeListener(this);
        tvLog.setOnClickListener(this);

        ImageBean imageBean = new ImageBean();
        imageBean.setImage(imageUrl);
        ImageFragment imageFragment = ImageFragment.newInstance(imageBean);
        imageFragment.bindHandler(handler);
        fragments.add(imageFragment);

        VideoInfoBean videoInfoBean = new VideoInfoBean();
        videoInfoBean.setVideo(urls[0]);
        VideoFragment videoFragment = VideoFragment.newInstance(videoInfoBean);
        videoFragment.bindHandler(handler);
        fragments.add(videoFragment);

        VideoInfoBean videoInfoBean2 = new VideoInfoBean();
        videoInfoBean2.setVideo(urls[1]);
        VideoFragment videoFragment2 = VideoFragment.newInstance(videoInfoBean2);
        videoFragment2.bindHandler(handler);
        fragments.add(videoFragment2);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    private void initData() {
        List<String> downloadUrls = new ArrayList<>();
        Collections.addAll(downloadUrls, urls);
        List<String> needDownloadUrls = checkLocalVideo(this, downloadUrls);
        if (needDownloadUrls.size() == 0) {
            tvDownloadStatus.setText("已全部下载");
            return;
        }
        queueController = new QueueController();
        final StringBuilder builder = new StringBuilder("");
        showDownLoadStatus(MSG_DOWNLOAD_START, builder);
        queueController.initTasks(this, needDownloadUrls, new DownloadContextListener() {
            @Override
            public void taskEnd(@NonNull DownloadContext context, @NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, int remainCount) {
                builder.append("-------------------------------------------------").append("\n");
                String temp = "视频下载地址 ： " + task.getUrl() + "\n" +
                        "下载状态 : " + cause.name() + "\n" +
                        "视频名称 : " + (task.getFile() != null ? task.getFile().getName() : "") + "\n" +
                        "视频存放路径 : " + (task.getFile() != null ? task.getFile().getAbsolutePath() : "") + "\n" +
                        "异常错误 : " + (realCause != null ? realCause.getMessage() : "");
                builder.append(temp).append("\n");
                tvDownloadStatus.setText(builder.toString());
                if (cause == EndCause.COMPLETED) {
                    saveVideo(task.getFile().getName(), task.getFile().getAbsolutePath());
                }
            }

            @Override
            public void queueEnd(@NonNull DownloadContext context) {
                builder.append("-------------------------------------------------").append("\n");
                showDownLoadStatus(MSG_DOWNLOAD_END, builder);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_log:
                tvDownloadStatus.setVisibility(tvDownloadStatus.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
        Log.e(TAG, "index : " + index);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onNext() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        index++;
        if (index > fragments.size() - 1) {
            index = 0;
        }
        viewPager.setCurrentItem(index, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void showDownLoadStatus(final int msg, final StringBuilder builder) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msg == MSG_DOWNLOAD_START) {
                    builder.append("开始下载").append("\n");
                } else if (msg == MSG_DOWNLOAD_END) {
                    builder.append("下载结束").append("\n");
                }
                tvDownloadStatus.setVisibility(View.VISIBLE);
                tvDownloadStatus.setText(builder.toString());
            }
        });
    }

    private void saveVideo(String name, String path) {
        VideoBean bean = new VideoBean();
        bean.setStatus(1);
        bean.setName(name);
        bean.setPath(path);
        VideoDao.getInstance(MainActivity.this).saveVideo(bean);
    }

    private List<String> checkLocalVideo(Context context, List<String> urls) {
        List<String> downloadUrls = new ArrayList<>();
        for (String url : urls) {
            String fileName = MD5Util.getMD5(url) + ".mp4";
            VideoBean video = VideoDao.getInstance(context).getVideo(fileName);
            if (video == null) {
                downloadUrls.add(url);
            }
        }
        return downloadUrls;
    }
}
