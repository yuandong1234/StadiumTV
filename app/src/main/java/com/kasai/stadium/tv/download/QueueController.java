package com.kasai.stadium.tv.download;

import android.content.Context;

import com.kasai.stadium.tv.utils.FileUtil;
import com.kasai.stadium.tv.utils.MD5Util;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.listener.DownloadListener4;

public class QueueController {

    public void initTasks(Context context, DownloadContextListener listener, DownloadListener4 downloadListener) {
        String parentPath = FileUtil.getVideoRootDirectory(context);
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
}
