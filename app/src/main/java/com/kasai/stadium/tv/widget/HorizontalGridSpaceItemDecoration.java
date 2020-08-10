package com.kasai.stadium.tv.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HorizontalGridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int spanCount;
    private int spacing;


    public HorizontalGridSpaceItemDecoration(int spanCount, int spacing) {
        this.spanCount = spanCount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        outRect.top = column * spacing / spanCount;
        outRect.bottom = spacing - (column + 1) * spacing / spanCount;

        if (position >= spanCount) {
            outRect.left = spacing;
        }
    }
}
