package com.kasai.stadium.tv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.bean.VenueListBean;

import java.util.ArrayList;
import java.util.List;

public class StadiumListAdapter extends RecyclerView.Adapter<StadiumListAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private onStadiumSelectedListener listener;

    public void setListener(onStadiumSelectedListener listener) {
        this.listener = listener;
    }

    private List<VenueListBean.Data.Venue> dataList = new ArrayList<>();

    public StadiumListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<VenueListBean.Data.Venue> data) {
        dataList.clear();
        if (data != null && data.size() > 0) {
            dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_stadium, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VenueListBean.Data.Venue bean = dataList.get(position);
        holder.tvStadiumName.setText(bean.venueName);
        holder.itemView.setFocusable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onSelected(bean.id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStadiumName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStadiumName = itemView.findViewById(R.id.tv_stadium_name);
        }
    }

    public interface onStadiumSelectedListener {
        void onSelected(String id);
    }
}
