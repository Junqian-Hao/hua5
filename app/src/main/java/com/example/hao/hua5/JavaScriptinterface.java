package com.example.hao.hua5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

/**
 * Created by hao on 2017/9/27.
 */

public class JavaScriptinterface {
    Activity context;
    Class b;
    public JavaScriptinterface(Activity c, Class b) {
        context= c;
        this.b = b;
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @JavascriptInterface
    public void startActivityForH5(String ssss , String con) {

        Intent intent = new Intent(context, b);
        Bundle bundle = new Bundle();
        bundle.putString("city", con);
        bundle.putString("contry", ssss);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
