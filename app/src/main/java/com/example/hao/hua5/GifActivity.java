package com.example.hao.hua5;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.hao.hua5.util.MyAnimationDrawable;

public class GifActivity extends AppCompatActivity {
    private static final String TAG = "GifActivity";
    long starTime;
    long endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtils.setWindowStatusBarColor(GifActivity.this, R.color.yello);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_gif);
//        x.Ext.init(getApplication());
        ImageView imageView = (ImageView) findViewById(R.id.iv_gif);
//        ImageOptions options = new ImageOptions.Builder()
//                // 是否忽略GIF格式的图片
//                .setIgnoreGif(false)
//                // 得到ImageOptions对象
//                .build();
//
//        x.image().bind(imageView,"assets://timg.gif",options);
//        imageView.setImageResource(R.drawable.animation);
//        AnimationDrawable drawable = (AnimationDrawable) imageView.getDrawable();
//        drawable.start();
        MyAnimationDrawable.animateRawManuallyFromXML(R.drawable.animation,
                imageView, new Runnable() {

                    @Override
                    public void run() {
                        // 动画开始时回调
                        starTime=System.currentTimeMillis();

                    }
                }, new Runnable() {

                    @Override
                    public void run() {
                        // 动画结束时回调
                        endTime=System.currentTimeMillis();
                        Log.i(TAG, "run: 动画结束,用时-》"+(endTime-starTime));

                        startActivity(new Intent(GifActivity.this,SelectContry2Activity.class));
                        finish();
                    }
                });
    }
}
