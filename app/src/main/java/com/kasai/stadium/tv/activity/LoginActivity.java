package com.kasai.stadium.tv.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kasai.stadium.tv.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName;
    private EditText edtPassword;
    private Button btnLogin;

    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        setContentView(R.layout.fragment_stadium);
//        initView();
        applyPermissions();
    }

    private void initView() {
        edtName = findViewById(R.id.edt_name);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btn_login:
//                break;
        }
    }

    private void applyPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }
}
