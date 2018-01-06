package com.example.hao.hua5.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hao.hua5.bean.Result;
import com.hanuor.onyx.Onyx;
import com.hanuor.onyx.hub.OnTaskCompletion;
import com.hanuor.onyx.toolbox.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;

/**
 * Created by hao on 2017/9/6.
 * 机器学习工具类
 */

public class OnyxUtils {
    private static final String TAG = "OnyxUtils";
    public static final String TRANSLATION_URL = "http://api.fanyi.baidu" +
            ".com/api/trans/vip/translate";

    //    返回中文结果的机器学习内容
    public static void getChineseFromURL(Context context, final RequestQueue mRequestQueue, String url, final OnyxUtils.OnResult onResult) {
        Onyx.with(context).fromURL(url).getTagsfromApi(new OnTaskCompletion() {
            @Override
            public void onComplete(ArrayList<String> response) {
                try {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < response.size(); i++) {
                        Log.i(TAG, "机器学习返回: " + response.get(i));
                        String str = response.get(i);
                        sb.append(str);
                        sb.append(";");
                    }
                onResult.onUpLoadMessage(sb.toString());
                    sb.deleteCharAt(sb.length() - 1);
//                    translation(sb.toString());
                    String input = sb.toString();
                    String appid = "20160920000028985";
                    String key = "qy53qCdsTFBRCGwNTsxo";
                    int salt = new Random().nextInt(10000);
                    String result = getMD5(input, appid, key, salt);
                    appid = urlEncode(appid);
                    input = urlEncode(input);
                    String str_salt = urlEncode(Integer.toString(salt));
                    String from = urlEncode("en");
                    String to = urlEncode("zh");
                    String url = TRANSLATION_URL + "?q=" + input + "&from=" + from + "&to=" + to + "&appid="
                            + appid + "&salt=" + str_salt + "&sign=" + result;
                    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i(TAG, "百度翻译返回：" + response);
                            Gson gson = new Gson();
                            Result result = gson.fromJson(response, Result.class);
                            String dst = result.getTrans_result().get(0).getDst();
                            onResult.onResult(dst);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i(TAG, "onErrorResponse: " + "机器学习百度翻译返回错误");
                        }
                    });
                    mRequestQueue.add(stringRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getChineseFromPath(Context context, final RequestQueue mRequestQueue, String filepath, final OnyxUtils.OnResult onResult) {
        String fileName = getFileName(filepath);
        Log.i(TAG, "getChineseFromPath文件名称: "+fileName);
        Log.i(TAG, "getChineseFromPath文件本地路径: "+filepath);
        OkHttpUtils.post()
                .addFile("file", getFileName(filepath), new File(filepath))
                .url("http://119.28.27.175/hua5/hua5/savepic")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError上传图片错误: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse图片路径: " + response);
                response = "http://119.28.27.175/hua5"+response;
                onResult.onUpLoadImage(response);
                getChineseFromURL(context, mRequestQueue, response, new OnResult() {
                    @Override
                    public void onResult(String resuliMessage) {
                        onResult.onResult(resuliMessage);
                    }

                    @Override
                    public void onUpLoadImage(String resuliMessage) {

                    }

                    @Override
                    public void onUpLoadMessage(String resuliMessage) {

                    }
                });
            }
        });
    }

    private static String getMD5(String input, String appid, String key, int salt) throws
            NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        String s = appid + input + salt + key;
        messageDigest.update(s.getBytes());
        byte[] digest = messageDigest.digest();
        StringBuilder md5StrBuilder = new StringBuilder();

        //将加密后的byte数组转换为十六进制的字符串,否则的话生成的字符串会乱码
        for (int i = 0; i < digest.length; i++) {
            if (Integer.toHexString(0xFF & digest[i]).length() == 1) {
                md5StrBuilder.append("0").append(
                        Integer.toHexString(0xFF & digest[i]));
            } else {
                md5StrBuilder.append(Integer.toHexString(0xFF & digest[i]));
            }
        }
        return md5StrBuilder.toString();
    }

    private static String urlEncode(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8");
    }
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public interface OnResult {
        public void onResult(String resuliMessage);

        public void onUpLoadImage(String resuliMessage);

        public void onUpLoadMessage(String resuliMessage);



    }



    /**
     * 从文件路径获得文件名
     * @param pathandname
     * @return
     */
    public static String getFileName(String pathandname){

        int start=pathandname.lastIndexOf("/");
        int end=pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(start+1);
        }else{
            return null;
        }

    }
}
