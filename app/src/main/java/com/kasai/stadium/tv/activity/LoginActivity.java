package com.kasai.stadium.tv.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.kasai.stadium.tv.http.HttpCallback;
import com.kasai.stadium.tv.http.HttpHelper;
import com.kasai.stadium.tv.utils.ToastUtil;
import com.kasai.stadium.tv.utils.UserInfoUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText edtName;
    private EditText edtPassword;
    private Button btnLogin;

    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        applyPermissions();
    }

    private void initView() {
        edtName = findViewById(R.id.edt_name);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
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
        String userName = edtName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
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
        body.put("username", "screen01");
        body.put("password", "a12345678");
//        body.put("username", "ping01");
//        body.put("password", "a12345678");
        HttpHelper.post(Api.HOST + Api.API_LOGIN, body, new HttpCallback<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean data) {
                hideLoadingDialog();
                Log.e("LoginActivity", " data : " + data.toString());
                if (data.isSuccessful() && data.getData() != null) {
                    UserInfoUtil.getInstance().putValue(Constants.SP_KEY_USER_TOKEN, data.getData().token);
                    if (data.getData().isShowVenueList) {
                        startActivity(new Intent(LoginActivity.this, StadiumSelectActivity.class));
//                        ToastUtil.showShortCenter("将进入场馆列表界面。。。。");
                    } else {
                        startActivity(new Intent(LoginActivity.this, StadiumPageActivity.class));
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
}
