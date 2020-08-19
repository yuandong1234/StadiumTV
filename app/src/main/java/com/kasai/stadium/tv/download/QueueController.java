package com.kasai.stadium.tv.download;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kasai.stadium.tv.utils.FileUtil;
import com.kasai.stadium.tv.utils.MD5Util;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener3;
import com.liulishuo.okdownload.core.listener.DownloadListener4;

import java.util.List;

public class QueueController {
    public final static String TAG = QueueController.class.getSimpleName();

    public void initTasks(Context context, DownloadContextListener listener, DownloadListener4 downloadListener) {
        String parentPath = FileUtil.getFileRootDirectory(context);
        DownloadContext.QueueSet queueSet = new DownloadContext.QueueSet();
        queueSet.setParentPath(parentPath);
        queueSet.setMinIntervalMillisCallbackProcess(200);
        DownloadContext.Builder builder = queueSet.commit();

        String url = "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/prod/upload_file/file/20200717/20200717172117927716.mp4";
        String fileName = MD5Util.getMD5(url) + ".mp4";
        DownloadTask task = new DownloadTask.Builder(url, parentPath, fileName)
                .setMinIntervalMillisCallbackProcess(30)
                .setPassIfAlreadyCompleted(false)
                .setFilenameFromResponse(false)
                .build();
        builder.bindSetTask(task);

        String url2 = "https://venue-saas.oss-cn-shenzhen.aliyuncs.com/prod/upload_file/file/20200717/20200717173242162638.mp4";
        String fileName2 = MD5Util.getMD5(url2) + ".mp4";
        DownloadTask task2 = new DownloadTask.Builder(url2, parentPath, fileName2)
                .setMinIntervalMillisCallbackProcess(30)
                .setPassIfAlreadyCompleted(false)
                .setFilenameFromResponse(false)
                .build();
        builder.bindSetTask(task2);

        builder.setListener(listener);
        DownloadContext downloadContext = builder.build();
        downloadContext.startOnParallel(downloadListener);
    }


    public void initTasks(Context context, List<String> urls, DownloadContextListener listener) {
        String parentPath = FileUtil.getFileRootDirectory(context);
        DownloadContext.QueueSet queueSet = new DownloadContext.QueueSet();
        queueSet.setParentPath(parentPath);
        queueSet.setMinIntervalMillisCallbackProcess(200);
        DownloadContext.Builder builder = queueSet.commit();

        for (String url : urls) {
            DownloadTask task = createDownTask(url, parentPath);
            builder.bindSetTask(task);
        }

        builder.setListener(listener);
        DownloadContext downloadContext = builder.build();
        downloadContext.start(downloadListener, true);
    }

    public void initTasks(Context context, List<String> urls, DownloadContextListener listener, DownloadListener3 downloadListener) {
        String parentPath = FileUtil.getFileRootDirectory(context);
        DownloadContext.QueueSet queueSet = new DownloadContext.QueueSet();
        queueSet.setParentPath(parentPath);
        queueSet.setMinIntervalMillisCallbackProcess(200);
        DownloadContext.Builder builder = queueSet.commit();

        for (String url : urls) {
            DownloadTask task = createDownTask(url, parentPath);
            builder.bindSetTask(task);
        }

        builder.setListener(listener);
        DownloadContext downloadContext = builder.build();
        downloadContext.start(downloadListener, true);
    }


    private DownloadTask createDownTask(String url, String parentPath) {
        String fileType = getFileType(url);
        String fileName = MD5Util.getMD5(url) + "." + fileType;
        DownloadTask task = new DownloadTask.Builder(url, parentPath, fileName)
                .setMinIntervalMillisCallbackProcess(30)
                .setPassIfAlreadyCompleted(false)
                .setFilenameFromResponse(false)
                .build();
        return task;
    }

    private String getFileType(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }

    private DownloadListener3 downloadListener = new DownloadListener3() {
        @Override
        protected void started(@NonNull DownloadTask task) {
            Log.e(TAG, "started .....");
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
        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
            Log.e(TAG, "progress .....");
        }

        @Override
        protected void completed(@NonNull DownloadTask task) {
            Log.e(TAG, "completed .....");
        }
    };
}
