package com.kasai.stadium.tv.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.bean.LoginBean;
import com.kasai.stadium.tv.constants.Api;
import com.kasai.stadium.tv.constants.Constants;
import com.kasai.stadium.tv.download.DownloadManager;
import com.kasai.stadium.tv.http.HttpCallback;
import com.kasai.stadium.tv.http.HttpHelper;
import com.kasai.stadium.tv.utils.PermissionUtil;
import com.kasai.stadium.tv.utils.ToastUtil;
import com.kasai.stadium.tv.utils.UserInfoUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText edtName;
    private EditText edtPassword;
    private Button btnLogin;

    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    hideLoadingDialog();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        applyPermissions();
        deleteHistory();
    }

    private void initView() {
        edtName = findViewById(R.id.edt_name);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        String userName = UserInfoUtil.getInstance().getUserAccount();
        String userPassword = UserInfoUtil.getInstance().getUserPassword();
        if (!TextUtils.isEmpty(userName)) {
            edtName.setText(userName);
        }
        if (!TextUtils.isEmpty(userPassword)) {
            edtPassword.setText(userPassword);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void applyPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    private void login() {
        final String userName = edtName.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showShortCenter("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShortCenter("请输入密码");
            return;
        }
        showLoadingDialog();
        Map<String, String> body = new HashMap<>();
        body.put("username", userName);
        body.put("password", password);

        Log.e("LoginActivity", " Url : " + Api.HOST + Api.API_LOGIN);
        HttpHelper.post(Api.HOST + Api.API_LOGIN, body, new HttpCallback<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean data) {
                hideLoadingDialog();
                Log.e("LoginActivity", " data : " + data.toString());
                if (data.isSuccessful() && data.getData() != null) {
                    UserInfoUtil.getInstance().putValue(Constants.SP_KEY_USER_ACCOUNT, userName);
                    UserInfoUtil.getInstance().putValue(Constants.SP_KEY_USER_PASSWORD, password);
                    UserInfoUtil.getInstance().putValue(Constants.SP_KEY_USER_TOKEN, data.getData().token);
                    if (data.getData().isShowVenueList) {
                        startActivity(new Intent(LoginActivity.this, StadiumSelectActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, ResourceDownloadActivity.class));
                    }
                } else {
                    ToastUtil.showShortCenter(data.getMsg());
                }
            }

            @Override
            protected void onFailure(String error) {
                hideLoadingDialog();
                ToastUtil.showShortCenter(error);
            }
        });
    }

    private void deleteHistory() {
        if (PermissionUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                PermissionUtil.hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showLoadingDialog(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new DownloadManager(LoginActivity.this).deleteAllHistory();
                    handler.sendEmptyMessage(0);
                }
            }).start();

        }
    }

//    public void getWeatherData() {
//        Map<String, String> body = new HashMap<>();
//        body.put("city", "shenzhen");
//        body.put("appkey", "3e33050146f17496ec581cac8c719e5c");
//        showLoadingDialog();
//        HttpHelper.post("https://way.jd.com/he/freeweather", body, new HttpCallback<WeatherBean>() {
//            @Override
//            protected void onSuccess(WeatherBean data) {
//                hideLoadingDialog();
//                Log.e("StadiumPageActivity", " weather data : " + data.toString());
////                if (data.isSuccessful() && data.getData() != null) {
////                    setPages(data.getData());
////                } else {
////                    ToastUtil.showShortCenter(data.getMsg());
////                }
//            }
//
//            @Override
//            protected void onFailure(String error) {
//                hideLoadingDialog();
//                ToastUtil.showShortCenter(error);
//            }
//        });
//    }
}
