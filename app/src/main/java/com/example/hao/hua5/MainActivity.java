package com.example.hao.hua5;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.hao.hua5.bean.DB;
import com.example.hao.hua5.bean.ListPlayer;
import com.example.hao.hua5.bean.MessageBean;
import com.example.hao.hua5.util.OnyxUtils;
import com.example.hao.hua5.util.StatusBarUtils;
import com.example.hao.hua5.util.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView iv_menu;
    ImageView iv_sousuo;
    DrawerLayout drawer;
    TextView sousuo;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private RequestQueue mRequestQueue;
    private static final String TAG = "MainActivity";
    private String videoUrl = "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";
    private ListView listView;
    private List<ListPlayer> datas;
    private ProgressDialog dialog;
    VideoAdapter videoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(MainActivity.this, R.color.yello);
        setContentView(R.layout.activity_main);
        //申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                startRequestPermission();
            }
        }
        //准备网络请求框架
        mRequestQueue = Volley.newRequestQueue(this);


        //侧滑菜单控件
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //侧滑菜单栏上面的部分
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        View viewById = view.findViewById(R.id.iv_head);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        //点击菜单按钮展开侧滑菜单
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
//                sousuo.setCursorVisible(false);
            }
        });

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                sousuo.setCursorVisible(false);
                return false;
            }
        });

        //搜索输入框
        sousuo = (TextView) findViewById(R.id.tv_city);
        // 编辑框设置触摸监听
     /*   sousuo.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    sousuo.setCursorVisible(true);// 再次点击显示光标
                }
                return false;
            }
        });*/

//        获得用户选择的城市
        String city = "";
        String contry = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            city = extras.getString("city");
            contry = extras.getString("contry");
        }
        if (TextUtils.isEmpty(city)) {
            contry = "中国";
        }
        sousuo.setText(city);
        datas = DB.getDb().get(city);
        if (datas == null) {
            datas = DB.getDb().get(city);
            if (datas == null) {
                datas = new ArrayList<>();
            Toast.makeText(this, "未收录"+city+"信息，敬请期待", Toast.LENGTH_SHORT).show();

            }
        }
        if (videoAdapter == null) {
            videoAdapter = new VideoAdapter(MainActivity.this, datas);
            listView.setAdapter(videoAdapter);
        } else {
            videoAdapter.notifyDataSetChanged();
        }
//        设置搜索按钮
        iv_sousuo = (ImageView) findViewById(R.id.iv_sousuo);
        iv_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "以图搜视频", Toast.LENGTH_SHORT).show();
                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"
                innerIntent.setType("image/*"); //查看类型 String IMAGE_UNSPECIFIED = "image/*";
                innerIntent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(innerIntent, null), 2);
            }
        });

    }

    //后退键回调监听
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * @param item
     * @return
     */
    //    侧滑菜单栏点击回调
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
//            Toast.makeText(this, "以图搜视频", Toast.LENGTH_SHORT).show();
            Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"
            innerIntent.setType("image/*"); //查看类型 String IMAGE_UNSPECIFIED = "image/*";
            innerIntent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(innerIntent, null), 2);

        } else if (id == R.id.nav_send) {
//            Toast.makeText(this, "选择国家", Toast.LENGTH_SHORT).show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//            builder.setTitle("选择一个国家");
//            //    指定下拉列表的显示数据
//            Map<String, List<ListPlayer>> db = DB.getDb();
//            Set<String> strings = db.keySet();
//            String[] objects = strings.toArray(new String[0]);
//            //    设置一个下拉的列表选择项
//            builder.setItems(objects, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
////                    Toast.makeText(MainActivity.this, "选择的国家为：" + objects[which], Toast.LENGTH_SHORT).show();
//                    datas.clear();
//                    datas.addAll(DB.getDb().get(objects[which]));
//                    videoAdapter.notifyDataSetChanged();
//                }
//            });
//            builder.show();
            startActivity(new Intent(MainActivity.this, SelectContryActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        JCVideoPlayer.releaseAllVideos();
        super.onPause();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && Activity.RESULT_OK == resultCode && null != data) {
            try {
                dialog = ProgressDialog.show(this, null, "正在解析图片路径...", true, false);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Uri data1 = data.getData();
                String realFilePath = OnyxUtils.getPath(MainActivity.this, data.getData());
                Log.i(TAG, "onActivityResult图片地址: " + realFilePath);
                OnyxUtils.getChineseFromPath(MainActivity.this, mRequestQueue, realFilePath, new OnyxUtils.OnResult() {
                    @Override
                    public void onResult(String resuliMessage) {
                        Log.i(TAG, "onResult机器学习返回: " + resuliMessage);
                        if (dialog.isShowing())
                            dialog.dismiss();
//                        Toast.makeText(MainActivity.this, resuliMessage, Toast.LENGTH_SHORT).show();
//                        sousuo.setText(resuliMessage);
                        if (resuliMessage.contains("山；雪；无人；旅行；风景；自然")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "天山");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else if (resuliMessage.contains("人；团体；宗教；庙宇；博物馆；许多；旅游")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "故宫");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else if (resuliMessage.contains("建筑；山；旅行；古代；风景；旅游；城堡")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "长城");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("古代；建筑；旅行；石头；")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "长城");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("山；风景；全景；旅行；无人")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "长城");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("雪；山；没有人；旅行；冬天；")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "天山");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("寺庙；华盖；建筑；建筑；旅游；")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "故宫");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("山；景观；流域；旅游；雪；自然；")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "天山");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("旅行；人；宗教；庙宇；许多；")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "故宫");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("山；雪；没有人；山谷；风景；旅行；")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "天山");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("没有人；房子；建筑；建筑")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "沙溪");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("建筑；墙；没有人；建筑物")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "张壁");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("建筑；房屋；建筑；家")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "沙溪");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if (resuliMessage.contains("建筑；建筑；没有人；房子")) {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "张壁");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "故宫");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onUpLoadImage(String resuliMessage) {
                        dialog.setMessage("正在解析图片内容...");
                    }

                    @Override
                    public void onUpLoadMessage(String resuliMessage) {
                        dialog.setMessage("正在解析结果信息...");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 网络请求
     */
    public void requestNet() {
        //        准备网络请求框架
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("mcountry", "中国");
        WebUtil.post(mRequestQueue, "http://119.28.27.175/hua5/selectMovie", stringStringHashMap, new WebUtil.IResponse() {
            @Override
            public void iResponse(int code, String response) {
                if (code == 0) {
                    System.out.println("回调成功");
                    System.out.println("iResponse: code->" + code + "response->" + response);
                    Log.d(TAG, "iResponse: code->" + code + "response->" + response);
                    String replace = response.replace("郝俊谦", "张鑫");
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<ArrayList<MessageBean>>() {
                    }.getType();
                    datas = gson.fromJson(replace, type);
                    if (videoAdapter == null) {
                        videoAdapter = new VideoAdapter(MainActivity.this, datas);
                        listView.setAdapter(videoAdapter);
                    } else {
                        videoAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }
}
