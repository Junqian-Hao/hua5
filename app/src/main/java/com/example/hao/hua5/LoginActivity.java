package com.example.hao.hua5;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hao.hua5.util.Md5Util;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;

public class LoginActivity extends BaseActivity {
    Button login;
    TextView registe;
    EditText et_phone, et_password;
    private AlertDialog dialog;
    private static final String TAG = "LoginActivity";
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化输入
        et_password = (EditText) findViewById(R.id.et_password);
        et_phone = (EditText) findViewById(R.id.et_phone);
        //登录按钮
        login = (Button) findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 登录按钮点击");
                //Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                String s = Md5Util.Md5Encoder(et_password.getText().toString());
                String s1 = et_phone.getText().toString();
                Log.i(TAG, "onClick: 手机号-》" + s1 + "密码-》" + s);
                OkHttpUtils
                        .post()
                        .url("http://182.254.208.91:1323/user/login")
                        .addParams("phone", s1)
                        .addParams("password", s)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onError: "+e.getMessage() );
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String token = jsonObject.getString("token");
                                    SharedPreferences.Editor editor = getSharedPreferences("lock", MODE_PRIVATE).edit();
                                    editor.putString("token", token);
                                    boolean commit = editor.commit();
                                    if (!commit) {
                                        Log.w(TAG, "onResponse: token保存失败" );
                                    }
                                    Log.i(TAG, "onResponse: " + response);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
//                                finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            }
        });

        //注册
        registe = (TextView) findViewById(R.id.tv_registe);
        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Register_oneActivity.class);
                startActivity(intent);
            }
        });
    }

}
