package com.example.hao.hua5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.hao.hua5.bean.ListPlayer;
import com.example.hao.hua5.util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {
    private ListView listView;
    private List<ListPlayer> datas;
    VideoAdapter videoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(SelectActivity.this, R.color.yello);
        setContentView(R.layout.activity_select);
        listView = (ListView) findViewById(R.id.lV_list);
        Bundle extras = getIntent().getExtras();
        String tag = extras.getString("tag");
        if ("天山".equals(tag)) {
            datas = new ArrayList<>();
            ListPlayer listPlayer = new ListPlayer();
            listPlayer.setCode(ListPlayer.SELECT);
            listPlayer.setTitle("天山之巅");
            listPlayer.setImageOldUrl("http://oukncmq42.bkt.clouddn.com/tianshanico.jpg");
//            listPlayer.setHeadUrl("http://123.206.74.213/1.jpg");
            listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/tianshan.mp4");
//            listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/hasakesitan2.mp4");
            datas.add(listPlayer);
        }
        if ("故宫".equals(tag)) {
            datas = new ArrayList<>();
            ListPlayer listPlayer = new ListPlayer();
            listPlayer.setCode(ListPlayer.SELECT);
            listPlayer.setTitle("故宫，明清两代的皇家宫殿");
            listPlayer.setImageOldUrl("http://oukncmq42.bkt.clouddn.com/gugongpic.jpg");
//            listPlayer.setHeadUrl("http://123.206.74.213/1.jpg");
            listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/gugong.mp4");
//            listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/hasakesitan2.mp4");
            datas.add(listPlayer);
        }
        if ("长城".equals(tag)) {
            datas = new ArrayList<>();
            ListPlayer listPlayer = new ListPlayer();
            listPlayer.setCode(ListPlayer.SELECT);
            listPlayer.setTitle("伟大的长城");
            listPlayer.setImageOldUrl("http://oukncmq42.bkt.clouddn.com/changchengpic.jpg");
//            listPlayer.setHeadUrl("http://123.206.74.213/1.jpg");
            listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/changcheng.mp4");
//            listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/hasakesitan2.mp4");
            datas.add(listPlayer);
        }
        if ("沙溪".equals(tag)) {
            datas = new ArrayList<>();
            ListPlayer listPlayer = new ListPlayer();
            listPlayer.setCode(ListPlayer.SELECT);
            listPlayer.setTitle("七彩云南 沙溪古镇");
            listPlayer.setImageOldUrl("http://oukncmq42.bkt.clouddn.com/shaxiguzhenfengmian.jpg");
//            listPlayer.setHeadUrl("http://123.206.74.213/1.jpg");
            listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/shaxi.mp4");
//            listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/hasakesitan2.mp4");
            datas.add(listPlayer);
        }
        if ("张壁".equals(tag)) {
            datas = new ArrayList<>();
            ListPlayer listPlayer = new ListPlayer();
            listPlayer.setCode(ListPlayer.SELECT);
            listPlayer.setTitle("张壁古堡");
            listPlayer.setImageOldUrl("http://oukncmq42.bkt.clouddn.com/zhangbigubaofengmian.jpg");
//            listPlayer.setHeadUrl("http://123.206.74.213/1.jpg");
            listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/zhangbigubao.mp4");
//            listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/hasakesitan2.mp4");
            datas.add(listPlayer);
        }
        if (videoAdapter == null) {
            videoAdapter = new VideoAdapter(SelectActivity.this, datas);
            listView.setAdapter(videoAdapter);
        } else {
            videoAdapter.notifyDataSetChanged();
        }
    }
}
