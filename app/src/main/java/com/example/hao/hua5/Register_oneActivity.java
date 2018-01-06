package com.example.hao.hua5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class Register_oneActivity extends BaseActivity {
    ImageView back;
    Button next;
    EditText ed_phone;
    private static final String TAG = "Register_oneActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        //返回按钮
        back = (ImageView) findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                }
        );

        ed_phone = (EditText) findViewById(R.id.ed_phone);
        //下一步
        next = (Button) findViewById(R.id.bt_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ed_phone.getText().toString();
                int code = (int) (Math.random() * 10000);
                Log.i(TAG, "onClick: 验证码是"+code);
                OkHttpUtils
                        .post()
                        .url("http://182.254.208.91:1323/user/regsms")
                        .addParams("phone", s)
                        .addParams("code", String.valueOf(code))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(Register_oneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Intent intent = new Intent(Register_oneActivity.this, Register_twoActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("phone", s);
                                bundle.putString("code", String.valueOf(code));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

            }
        });
    }

}
