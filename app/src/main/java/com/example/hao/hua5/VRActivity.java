package com.example.hao.hua5;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hao.hua5.util.StatusBarUtils;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import okhttp3.Call;

public class VRActivity extends AppCompatActivity {
    private static final String TAG = "VRActivity";
    private ProgressDialog dialog;
    private VrPanoramaView panoWidgetView;//上面说的Google提供给我们现实全景图片的View
    WebView webView = null;
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();//VrPanoramaView需要的设置
    private ImageLoaderTask backgroundImageLoaderTask;//异步加载图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr);
        StatusBarUtils.setWindowStatusBarColor(VRActivity.this, R.color.yello);
        Bundle extras = getIntent().getExtras();
        String imagUrl = extras.getString("imagUrl");
        Log.i(TAG, "onCreate上个界面传来的url: "+imagUrl);
        dialog =ProgressDialog.show(this, null, "正在加载图片，请稍候...", true, false);
        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view);//初始化VrPanoramaView
        panoWidgetView.setEventListener(new ActivityEventListener());//为VrPanoramaView添加监听
//      禁用眼镜按钮
//        panoWidgetView.setStereoModeButtonEnabled(false);
//        禁用信息按钮
        panoWidgetView.setInfoButtonEnabled(false);

        webView = (WebView) findViewById(R.id.wv_web);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("http://182.254.208.91/");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        //如果有任务在执行则停止它
        if (backgroundImageLoaderTask != null) {
            backgroundImageLoaderTask.cancel(true);
        }
        //设置inputType 为TYPE_STEREO_OVER_UNDER. 在后面会介绍TYPE_STEREO_OVER_UNDER的，暂时当做一个图片的显示类型就行
        panoOptions.inputType = VrPanoramaView.Options.TYPE_MONO;
        //创建一个任务
        backgroundImageLoaderTask = new ImageLoaderTask();
        //执行任务。将图片名（根据项目实际情况传吧）和设置传入
        backgroundImageLoaderTask.execute(Pair.create(imagUrl, panoOptions));

    }

    class ImageLoaderTask extends AsyncTask<Pair<String, VrPanoramaView.Options>, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Pair<String, VrPanoramaView.Options>... fileInformation) {//真正写项目根据情况添加条件判断吧
            Log.i(TAG, "doInBackground异步对象获得的url: "+fileInformation[0].first);
            OkHttpUtils
                    .get()//
                    .url(fileInformation[0].first)//
                    .build()//
                    .execute(new BitmapCallback()
                    {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i(TAG, "onError: "+call.toString());
                        }

                        @Override
                        public void onResponse(Bitmap response, int id) {
                            panoWidgetView.loadImageFromBitmap(response, fileInformation[0].second);//参数一为图片的bitmap，参数二为 VrPanoramaView 所需要的设置
                            //        设置全屏显示
                            if (dialog.isShowing())
                                dialog.dismiss();
                        }

                    });
//            InputStream istr = null;
//            try {
//                istr = getAssets().open(fileInformation[0].first);//获取图片的输入流
//            } catch (IOException e) {
//                Log.e(TAG, "Could not decode default bitmap: " + e);
//                return false;
//            }

//            Bitmap bitmap = BitmapFactory.decodeStream(istr);//创建bitmap
//            panoWidgetView.loadImageFromBitmap(bitmap, fileInformation[0].second);//参数一为图片的bitmap，参数二为 VrPanoramaView 所需要的设置
//            try {
//                istr.close();//关闭InputStream
//            } catch (IOException e) {
//                Log.e(TAG, "输入流无法关闭: " + e);
//            }

            return true;
        }
    }

    private class ActivityEventListener extends VrPanoramaEventListener {

        @Override
        public void onLoadSuccess() {//图片加载成功
            if (dialog.isShowing())
                dialog.dismiss();
            Log.i(TAG, "图片加载成功");
        }


        @Override
        public void onLoadError(String errorMessage) {//图片加载失败
            Log.e(TAG, "图片加载失败: " + errorMessage);
        }

        @Override
        public void onClick() {//当我们点击了VrPanoramaView 时候出发
            super.onClick();
            Log.i(TAG, "VrPanoramaView被点击");
        }

        @Override
        public void onDisplayModeChanged(int newDisplayMode) {//改变显示模式时候出发（全屏模式和纸板模式）
            super.onDisplayModeChanged(newDisplayMode);
            if (newDisplayMode !=2) {
                Log.i(TAG, "显示模式改变");
            }
        }


    }

    @Override
    protected void onPause() {
        panoWidgetView.pauseRendering();//暂停3D渲染和跟踪
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        panoWidgetView.resumeRendering();//恢复3D渲染和跟踪
    }

    @Override
    protected void onDestroy() {
        panoWidgetView.pauseRendering();
        panoWidgetView.shutdown();//关闭渲染下并释放相关的内存

        if (backgroundImageLoaderTask != null) {
            backgroundImageLoaderTask.cancel(true);//停止异步任务
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
