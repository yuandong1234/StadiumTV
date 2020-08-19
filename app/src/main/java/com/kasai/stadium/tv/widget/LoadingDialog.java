package com.kasai.stadium.tv.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kasai.stadium.tv.R;


public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        this(context, R.style.theme_loading_dialog);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.layout_dialog_loading, null);
        setContentView(view);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.dialog_common_window_anim); //设置窗口弹出动画
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = dp2px(getContext(), 76);
            lp.height = dp2px(getContext(), 76);
            lp.gravity = Gravity.CENTER;
            window.setAttributes(lp);
        }
    }

    public void setCanCancel(boolean enable) {
        setCancelable(enable);
        setCanceledOnTouchOutside(enable);
    }


    /**
     * dp转px
     */
    private static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }


    /**
     * 获得屏幕宽度
     */
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        return outMetrics.widthPixels;
    }
}
