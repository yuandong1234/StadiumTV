package com.kasai.stadium.tv.download;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kasai.stadium.tv.R;

import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<DownloadBean> dataList = new ArrayList<>();

    public DownloadAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<DownloadBean> data) {
        dataList.clear();
        if (data != null && data.size() > 0) {
            dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setProgress(String url, String progress) {
        for (DownloadBean bean : dataList) {
            if (bean.url.equals(url)) {
                bean.setProgress(progress);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_download_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DownloadBean bean = dataList.get(position);
        holder.tvFileName.setText("文件" + (position + 1) + "下载进度：");
        holder.tvFileProgress.setText(TextUtils.isEmpty(bean.progress) ? "等待下载" : bean.progress);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFileName;
        private TextView tvFileProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            tvFileProgress = itemView.findViewById(R.id.tv_file_progress);
        }
    }
}
