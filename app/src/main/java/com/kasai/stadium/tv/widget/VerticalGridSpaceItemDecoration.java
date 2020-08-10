package com.kasai.stadium.tv.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalGridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int spanCount;
    private int horizontalSpace;
    private int verticalSpace;
    private boolean includeEdge;

    public VerticalGridSpaceItemDecoration(int spanCount, int horizontalSpace, int verticalSpace, boolean includeEdge) {
        this.spanCount = spanCount;
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = horizontalSpace - column * horizontalSpace / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * horizontalSpace / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = verticalSpace;
            }
            outRect.bottom = verticalSpace; // item bottom
        } else {
            outRect.left = column * horizontalSpace / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = horizontalSpace - (column + 1) * horizontalSpace / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = verticalSpace; // item top
            }
        }
    }
}
