package com.kasai.stadium.tv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kasai.stadium.tv.widget.LoadingDialog;

public class BaseActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate : " + TAG);
    }

    public void showLoadingDialog() {
        showLoadingDialog(true);
    }

    public void showLoadingDialog(boolean enable) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.setCanCancel(enable);
            loadingDialog.show();
        }
    }

    public void hideLoadingDialog() {
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        hideLoadingDialog();
        super.onDestroy();
    }
}
