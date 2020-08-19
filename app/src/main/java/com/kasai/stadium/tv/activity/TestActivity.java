package com.kasai.stadium.tv.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.download.QueueController;
import com.kasai.stadium.tv.utils.FileUtil;
import com.kasai.stadium.tv.utils.MD5Util;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4;
import com.liulishuo.okdownload.core.listener.assist.Listener4Assist;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = TestActivity.class.getSimpleName();
    private Button btnStart;
    private Button btnStop;
    private TextView tvDownloadProgress;

    private DownloadTask task;
    //    private String url = "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/test/upload_file/file/20200703/20200703153425055871.mp4";
    private String url = "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/prod/upload_file/file/20200717/20200717172117927716.mp4";
    private String url2 = "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/prod/upload_file/file/20200717/20200717173242162638.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        tvDownloadProgress = findViewById(R.id.tv_download_progress);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startTask();
//                startQueueTask();
                break;
            case R.id.btn_stop:
                stopTask();
                break;
        }
    }

    private void startTask() {
        String fileName = MD5Util.getMD5(url) + ".mp4";
        Log.e(TAG, "url : " + url);
        task = new DownloadTask.Builder(url, FileUtil.getFileRootDirectory(this), fileName)
                .setMinIntervalMillisCallbackProcess(30)
                .setPassIfAlreadyCompleted(false)
                .setFilenameFromResponse(false)
                .build();
        task.enqueue(downloadListener4);
    }

    private void stopTask() {
        task.cancel();
    }

    private void startQueueTask() {
        QueueController queueController = new QueueController();
        queueController.initTasks(this, new DownloadContextListener() {
            @Override
            public void taskEnd(@NonNull DownloadContext context, @NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, int remainCount) {
                Log.e(TAG, "queue taskEnd ....." + " EndCause : " + cause.toString() + "  remainCount : " + remainCount);
            }

            @Override
            public void queueEnd(@NonNull DownloadContext context) {
                Log.e(TAG, "queueEnd .....");
            }
        }, downloadListener4);
    }

    private DownloadListener4 downloadListener4 = new DownloadListener4() {
        long totalLength = 0;

        @Override
        public void taskStart(@NonNull DownloadTask task) {
            Log.e(TAG, "taskStart .....");
            tvDownloadProgress.setText("开始下载");
        }

        @Override
        public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
            Log.e(TAG, "connectStart .....");
        }

        @Override
        public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
            Log.e(TAG, "connectEnd .....");
        }

        @Override
        public void infoReady(DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4Assist.Listener4Model model) {
            Log.e(TAG, "infoReady .....");
            totalLength = info.getTotalLength();
        }

        @Override
        public void progressBlock(DownloadTask task, int blockIndex, long currentBlockOffset) {
            Log.e(TAG, "progressBlock .....");
        }

        @Override
        public void progress(DownloadTask task, long currentOffset) {
            String progress = calcProgress(currentOffset, totalLength);
            Log.e(TAG, "progress ..... " + progress);
            tvDownloadProgress.setText("下载进度： " + progress + "%");
        }

        @Override
        public void blockEnd(DownloadTask task, int blockIndex, BlockInfo info) {
            Log.e(TAG, "blockEnd .....");
        }

        @Override
        public void taskEnd(DownloadTask task, EndCause cause, @Nullable Exception realCause, @NonNull Listener4Assist.Listener4Model model) {
            Log.e(TAG, "taskEnd  ....." + " EndCause : " + cause.toString() + "  realCause : " + (realCause != null ? realCause.getMessage() : ""));
            if (cause == EndCause.COMPLETED) {
                tvDownloadProgress.setText("下载完成");
            }

        }
    };

    private String calcProgress(long offset, long total) {
        double progress = 0;
        if (total > 0) {
            progress = offset * 100d / total;
        }
        DecimalFormat df = new DecimalFormat("#.0");
        return progress == 100 ? "100" : df.format(progress);
    }
}
