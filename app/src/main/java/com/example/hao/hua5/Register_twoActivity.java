package com.example.hao.hua5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hao.hua5.util.Md5Util;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class Register_twoActivity extends BaseActivity {
    ImageView back;
    Button bt_next;
    EditText et_code,ed_username,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
        //返回按钮
        back = (ImageView) findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                }
        );
        Bundle extras = getIntent().getExtras();
        String phone = extras.getString("phone");
        String code = extras.getString("code");
        et_code = (EditText) findViewById(R.id.et_code);
        bt_next = (Button) findViewById(R.id.bt_next);
        ed_username = (EditText) findViewById(R.id.ed_username);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code==null||!code.equals(et_code.getText().toString().trim())) {
                    Toast.makeText(Register_twoActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkHttpUtils
                        .post()
                        .url("http://182.254.208.91:1323/user/create")
                        .addParams("phone", phone)
                        .addParams("password", Md5Util.Md5Encoder(et_password.getText().toString().trim()))
                        .addParams("username", ed_username.getText().toString())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(Register_twoActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onError: "+e.getMessage() );
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Intent intent = new Intent(Register_twoActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });
    }
}
