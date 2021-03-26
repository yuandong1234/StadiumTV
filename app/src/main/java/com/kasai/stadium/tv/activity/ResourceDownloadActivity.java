package com.kasai.stadium.tv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.bean.AdvertInfoBean;
import com.kasai.stadium.tv.constants.Api;
import com.kasai.stadium.tv.constants.Constants;
import com.kasai.stadium.tv.dao.FileDao;
import com.kasai.stadium.tv.dao.bean.FileBean;
import com.kasai.stadium.tv.download.DownloadAdapter;
import com.kasai.stadium.tv.download.DownloadBean;
import com.kasai.stadium.tv.download.QueueController;
import com.kasai.stadium.tv.http.HttpCallback;
import com.kasai.stadium.tv.http.HttpHelper;
import com.kasai.stadium.tv.utils.MD5Util;
import com.kasai.stadium.tv.utils.ToastUtil;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener3;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源下载
 */
public class ResourceDownloadActivity extends BaseActivity {
    private static final String TAG = ResourceDownloadActivity.class.getSimpleName();
    private TextView tvStadiumName;
    private TextView tvStadiumWelcome;
    private TextView tvStadiumName2;
    private LinearLayout llDownload;
    private Button btnDown;
    private LinearLayout llDownloading;
    private RecyclerView rvDowning;
    private LinearLayout llDownloadNone;

    private List<String> downLoadUrls = new ArrayList<>();
    private Handler handler = new Handler();
    private List<AdvertInfoBean.Data> dataList;
    private boolean isExistFailure;
    private int retryCount;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            enterHomePage();
        }
    };

    private List<AdvertInfoBean.Data> advertInfoData;


    private DownloadAdapter adapter;
    private QueueController queueController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_download);
        initView();
        getData();
    }

    private void initView() {
        tvStadiumName = findViewById(R.id.tv_stadium_name);
        tvStadiumWelcome = findViewById(R.id.tv_stadium_welcome);
        tvStadiumName2 = findViewById(R.id.tv_stadium_name2);
        llDownload = findViewById(R.id.ll_download);
        btnDown = findViewById(R.id.btn_down);
        llDownloading = findViewById(R.id.ll_downloading);
        rvDowning = findViewById(R.id.rv_downing);
        llDownloadNone = findViewById(R.id.ll_download_none);
    }


    private void getData() {
        Map<String, String> body = new HashMap<>();
        showLoadingDialog();
        Log.e(TAG, " Url : " + Api.HOST + Api.API_ADVERT_INFO);
        HttpHelper.get(Api.HOST + Api.API_ADVERT_INFO, body, new HttpCallback<AdvertInfoBean>() {
            @Override
            protected void onSuccess(AdvertInfoBean data) {
                hideLoadingDialog();
                Log.e(TAG, " data : " + data.toString());
                if (data.isSuccessful() && data.getData() != null) {
                    advertInfoData = data.getData();
                    checkNeedDownloadData(data.getData());
                } else {
                    ToastUtil.showShortCenter(data.getMsg());
                }
            }

            @Override
            protected void onFailure(String error) {
                hideLoadingDialog();
                ToastUtil.showShortCenter(error);
            }
        });
    }

    private void checkNeedDownloadData(List<AdvertInfoBean.Data> data) {
        downLoadUrls.clear();
        if (data == null || data.size() == 0) return;
        this.dataList = data;
        AdvertInfoBean.Data firstData = data.get(0);
        tvStadiumName.setText(firstData.merchantName);
        tvStadiumName2.setText(firstData.merchantName);
        tvStadiumWelcome.setText(firstData.merchantName + "欢迎您!");


        for (AdvertInfoBean.Data temp : dataList) {
            if (temp.advertType == 2) {//视频
                String url = convertUrl(temp.video);
                if (!TextUtils.isEmpty(url)) {
                    downLoadUrls.add(url);
                }
            } else if (temp.advertType == 1) {//图片
                String url = convertUrl(temp.image);
                if (!TextUtils.isEmpty(url)) {
                    downLoadUrls.add(url);
                }
            }
        }

        if (downLoadUrls.size() == 0) {
            showDownloadNone();
            return;
        }

        showLoadingDialog();
        List<String> needDownloadUrls = new ArrayList<>();
        for (String temp : downLoadUrls) {
            boolean isExist = checkLocalFile(temp);
            if (!isExist) {
                needDownloadUrls.add(temp);
            }
        }
        hideLoadingDialog();
        if (needDownloadUrls.size() > 0) {
            showDownloading(needDownloadUrls);
        } else {
            showDownloadNone();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void enterHomePage() {
        Intent intent = new Intent(ResourceDownloadActivity.this, StadiumPageActivity.class);
        if (dataList != null) {
            intent.putExtra(Constants.STADIUM_DATA, (Serializable) dataList);
        }
        startActivity(intent);
        finish();
    }

    private String convertUrl(String url) {
        if (TextUtils.isEmpty(url)) return null;
        String flag = "http://saas-resources.52jiayundong.com";
        if (url.startsWith(flag)) {
            return url.replace(flag, "https://venue-saas.oss-cn-shenzhen.aliyuncs.com");
        }
        return url;
    }

    private boolean checkLocalFile(String url) {
        if (TextUtils.isEmpty(url)) return false;
        String fileType = getFileType(url);
        String fileName = MD5Util.getMD5(url) + "." + fileType;
        FileBean video = FileDao.getInstance(this).getFile(fileName);
        if (video == null) {
            return false;
        }
        File file = new File(video.path);
        return file.exists();
    }


    private String getFileType(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }

    private void showDownloadNone() {
        llDownload.setVisibility(View.GONE);
        llDownloading.setVisibility(View.GONE);
        llDownloadNone.setVisibility(View.VISIBLE);
        handler.postDelayed(runnable, 3000);
    }

    private void showDownloading(List<String> urls) {
        llDownload.setVisibility(View.GONE);
        llDownloading.setVisibility(View.VISIBLE);
        llDownloadNone.setVisibility(View.GONE);
        initDownload(urls);
    }

    private void initDownload(List<String> urls) {
        isExistFailure = false;
        List<DownloadBean> downloadList = new ArrayList<>();
        for (String temp : urls) {
            DownloadBean bean = new DownloadBean();
            bean.setUrl(temp);
            downloadList.add(bean);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvDowning.setLayoutManager(layoutManager);
        adapter = new DownloadAdapter(this);
        rvDowning.setAdapter(adapter);
        adapter.setData(downloadList);

        startDownload(urls);
    }

    private void startDownload(List<String> urls) {
        queueController = new QueueController();
        queueController.initTasks(this, urls, downloadListener, downloadTaskListener);
    }

    private DownloadListener3 downloadTaskListener = new DownloadListener3() {
        @Override
        protected void started(@NonNull DownloadTask task) {
            Log.e(TAG, "started .....");
            adapter.setProgress(task.getUrl(), "开始下载");
        }

        @Override
        public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
            Log.e(TAG, "connected .....");
        }

        @Override
        protected void warn(@NonNull DownloadTask task) {
            Log.e(TAG, "warn .....");
        }

        @Override
        public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
            Log.e(TAG, "retry .....");
        }

        @Override
        protected void canceled(@NonNull DownloadTask task) {
            Log.e(TAG, "canceled .....");
        }

        @Override
        protected void error(@NonNull DownloadTask task, @NonNull Exception e) {
            Log.e(TAG, "error .....");
            Log.e(TAG, task.getUrl() + " download error : " + e.getMessage());
            adapter.setProgress(task.getUrl(), "下载失败");
        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
            Log.e(TAG, "progress .....");
            adapter.setProgress(task.getUrl(), calcProgress(currentOffset, totalLength));
        }

        @Override
        protected void completed(@NonNull DownloadTask task) {
            Log.e(TAG, "completed .....");
            adapter.setProgress(task.getUrl(), "已下载");
        }
    };

    private DownloadContextListener downloadListener = new DownloadContextListener() {
        @Override
        public void taskEnd(@NonNull DownloadContext context, @NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, int remainCount) {
            if (cause == EndCause.COMPLETED) {
                saveFile(task.getFile().getName(), task.getFile().getAbsolutePath());
            } else {
                isExistFailure = true;
            }
        }

        @Override
        public void queueEnd(@NonNull DownloadContext context) {
            Log.e(TAG, "下载结束.....");
            if (!isExistFailure) {
                enterHomePage();
            } else {
                if (retryCount <= 3) {
                    retryCount++;
                    checkNeedDownloadData(advertInfoData);
                }
            }
        }
    };

    private String calcProgress(long offset, long total) {
        double progress = 0;
        if (total > 0) {
            progress = offset * 100d / total;
        }
        DecimalFormat df = new DecimalFormat("#.0");
        return progress == 100 ? "100%" : df.format(progress) + "%";
    }

    private void saveFile(String name, String path) {
        FileBean bean = new FileBean();
        bean.setStatus(1);
        bean.setName(name);
        bean.setPath(path);
        FileDao.getInstance(this).saveFile(bean);
    }
}
