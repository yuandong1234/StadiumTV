package com.kasai.stadium.tv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.bean.OnlineServiceBean;

import java.util.ArrayList;
import java.util.List;

public class OnlineServiceAdapter extends RecyclerView.Adapter<OnlineServiceAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;

    private List<OnlineServiceBean.Service> dataList = new ArrayList<>();

    public OnlineServiceAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<OnlineServiceBean.Service> data) {
        dataList.clear();
        if (data != null && data.size() > 0) {
            dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_online_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OnlineServiceBean.Service bean = dataList.get(position);
        holder.tvServiceName.setText(bean.name);
        holder.tvServiceLevel.setText(bean.serviceDes);
        setServiceState(holder, bean.status);
        if (!TextUtils.isEmpty(bean.image)) {
            loadImage(holder.ivServicePhoto, bean.image);
        } else {
            holder.ivServicePhoto.setImageResource(R.mipmap.img_online_service);
        }
    }

    private void setServiceState(ViewHolder holder, String status) {
        if ("1".equals(status)) {
            holder.tvServiceState.setText("在岗");
        } else if ("2".equals(status)) {
            holder.tvServiceState.setText("离岗");
        } else {
            holder.tvServiceState.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItem;
        private ImageView ivServicePhoto;
        private TextView tvServiceName;
        private TextView tvServiceState;
        private TextView tvServiceLevel;

        public ViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            ivServicePhoto = itemView.findViewById(R.id.iv_service_photo);
            tvServiceName = itemView.findViewById(R.id.tv_service_name);
            tvServiceState = itemView.findViewById(R.id.tv_service_state);
            tvServiceLevel = itemView.findViewById(R.id.tv_service_level);
        }
    }

    private void loadImage(ImageView imageView, String url) {
        Glide.with(context).load(url).into(imageView);
    }
}
