package com.example.hao.hua5.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by hao on 2017/9/4.
 * 封装网络请求方法
 */

public class WebUtil {
    public static void post(RequestQueue mRequestQueue, String url, final Map<String, String> map, final IResponse iResponse) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("请求成功");
                       iResponse.iResponse(0,response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("请求失败");
                iResponse.iResponse(1,error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        mRequestQueue.add(stringRequest);
    }
    public interface IResponse{
        public void iResponse(int code,String response);
    }
}

