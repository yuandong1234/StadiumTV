package com.kasai.stadium.tv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.bean.StadiumBean;

import java.util.ArrayList;
import java.util.List;

public class StadiumListAdapter extends RecyclerView.Adapter<StadiumListAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;

    private List<StadiumBean> dataList = new ArrayList<>();

    public StadiumListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_stadium, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //StadiumBean bean = dataList.get(position);

    }

    @Override
    public int getItemCount() {
//        return dataList.size();
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItem;
        private TextView tvStadiumName;

        public ViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            tvStadiumName = itemView.findViewById(R.id.tv_stadium_name);

        }
    }
}
