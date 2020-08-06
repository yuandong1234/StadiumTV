package com.kasai.stadium.tv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kasai.stadium.tv.utils.FileUtil;
import com.kasai.stadium.tv.utils.MD5Util;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4;
import com.liulishuo.okdownload.core.listener.assist.Listener4Assist;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();
    private Button btnDownload;
    private TextView tvDownloadProgress;

    private DownloadTask task;
    private String url = "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/test/upload_file/file/20200703/20200703153425055871.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnDownload = findViewById(R.id.btn_download);
        tvDownloadProgress = findViewById(R.id.tv_download_progress);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                startTask();
                break;
        }
    }

    private void startTask() {
        String fileName = MD5Util.getMD5(url) + ".mp4";
        task = new DownloadTask.Builder(url, FileUtil.getVideoRootDirectory(this), fileName)
                .setMinIntervalMillisCallbackProcess(30)
                .setPassIfAlreadyCompleted(false)
                .setFilenameFromResponse(false)
                .build();
//        task.enqueue(new DownloadListener() {
//            @Override
//            public void taskStart(@NonNull DownloadTask task) {
//                Log.e(TAG, "taskStart .....");
//            }
//
//            @Override
//            public void connectTrialStart(@NonNull DownloadTask task, @NonNull Map<String, List<String>> requestHeaderFields) {
//                Log.e(TAG, "connectTrialStart .....");
//            }
//
//            @Override
//            public void connectTrialEnd(@NonNull DownloadTask task, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
//                Log.e(TAG, "connectTrialEnd .....");
//            }
//
//            @Override
//            public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @NonNull ResumeFailedCause cause) {
//                Log.e(TAG, "downloadFromBeginning .....");
//            }
//
//            @Override
//            public void downloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {
//                Log.e(TAG, "downloadFromBreakpoint .....");
//            }
//
//            @Override
//            public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
//                Log.e(TAG, "connectStart .....");
//            }
//
//            @Override
//            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
//                Log.e(TAG, "connectEnd .....");
//            }
//
//            @Override
//            public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {
//                Log.e(TAG, "fetchStart .....");
//            }
//
//            @Override
//            public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {
//                Log.e(TAG, "fetchProgress .....");
//            }
//
//            @Override
//            public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {
//                Log.e(TAG, "fetchEnd .....");
//            }
//
//            @Override
//            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
//                Log.e(TAG, "taskEnd  ....." + " EndCause : " + cause.toString() + "  realCause : " + (realCause != null ? realCause.getMessage() : ""));
//            }
//        });
        task.enqueue(new DownloadListener4() {
            long totalLength = 0;

            @Override
            public void taskStart(@NonNull DownloadTask task) {
                Log.e(TAG, "taskStart .....");
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
                Log.e(TAG, "progress ..... " + calcProgress(currentOffset, totalLength));
            }

            @Override
            public void blockEnd(DownloadTask task, int blockIndex, BlockInfo info) {
                Log.e(TAG, "blockEnd .....");
            }

            @Override
            public void taskEnd(DownloadTask task, EndCause cause, @Nullable Exception realCause, @NonNull Listener4Assist.Listener4Model model) {
                Log.e(TAG, "taskEnd  ....." + " EndCause : " + cause.toString() + "  realCause : " + (realCause != null ? realCause.getMessage() : ""));

            }
        });
    }

    private String calcProgress(long offset, long total) {
        double progress = 0;
        if (total > 0) {
            progress = offset * 100d / total;
        }
        DecimalFormat df = new DecimalFormat("#.0");
        return progress == 100 ? "100" : df.format(progress);
    }
}
