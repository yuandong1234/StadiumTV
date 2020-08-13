package com.kasai.stadium.tv.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class SpaceNumberAdapter extends RecyclerView.Adapter<SpaceNumberAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;

    private List<String> dataList = new ArrayList<>();
    private int sportNum;

    public SpaceNumberAdapter(Context context, int sportNum) {
        this.context = context;
        this.sportNum = sportNum;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<String> data) {
        dataList.clear();
        if (data != null && data.size() > 0) {
            dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_space_number, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String bean = dataList.get(position);
        int itemWidth = adjustItemWidth();
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) holder.rlItem.getLayoutParams();
        params.width = itemWidth;
        holder.rlItem.setLayoutParams(params);
        holder.tvNumber.setText(bean);
    }

    @Override
    public int getItemCount() {
        return dataList.size() > 20 ? 20 : dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout rlItem;
        private TextView tvNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            rlItem = itemView.findViewById(R.id.rl_item);
            tvNumber = itemView.findViewById(R.id.tv_number);
        }
    }

    private int adjustItemWidth() {
        int itemWidth = 0;
        int count = getItemCount();
        if (sportNum == 1) {
            if (count > 8) {
                itemWidth = DensityUtil.dip2px(context, 85);
            } else if (count > 5) {
                itemWidth = DensityUtil.dip2px(context, 220);
            } else {
                itemWidth = DensityUtil.dip2px(context, 445);
            }
        } else if (sportNum == 2) {
            if (count > 8) {
                itemWidth = DensityUtil.dip2px(context, 60);
            } else if (count > 5) {
                itemWidth = DensityUtil.dip2px(context, 120);
            } else {
                itemWidth = DensityUtil.dip2px(context, 310);
            }
        } else if (sportNum == 3) {
            if (count > 8) {
                itemWidth = DensityUtil.dip2px(context, 40);
            } else if (count > 5) {
                itemWidth = DensityUtil.dip2px(context, 85);
            } else {
                itemWidth = DensityUtil.dip2px(context, 175);
            }
        }
        return itemWidth;
    }
}
