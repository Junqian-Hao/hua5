package com.example.hao.hua5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hao.hua5.bean.ListPlayer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Activity mContext;
    private List<ListPlayer> mDatas;
    private static final String TAG = "VideoAdapter";

    public VideoAdapter(Activity context, List<ListPlayer> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        convertView = mInflater.inflate(R.layout.item_video, parent, false);
        viewHolder = new ViewHolder();
        viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        viewHolder.player = (ImageView) convertView.findViewById(R.id.player);
        viewHolder.player2 = (ImageView) convertView.findViewById(R.id.player2);
        viewHolder.img_video_icon = (ImageView) convertView.findViewById(R.id.img_video_icon);
        viewHolder.tv_video_userName = (TextView) convertView.findViewById(R.id.tv_video_userName);
        viewHolder.palyer_message = (TextView) convertView.findViewById(R.id.palyer_message);
        viewHolder.tv_video_comment = (TextView) convertView.findViewById(R.id.tv_video_comment);

        ListPlayer listPlayer = mDatas.get(position);
        viewHolder.tv_title.setText(listPlayer.getTitle());
        viewHolder.tv_video_userName.setText(listPlayer.getUserName());
        Log.i(TAG, "getViewcode值: "+listPlayer.getCode());
        if (listPlayer.getCode() == ListPlayer.VIDEO) {
            Log.i(TAG, "getView: 视频条目加载");
            Picasso.with(mContext).load(mDatas.get(position).getHeadUrl()).into(viewHolder.img_video_icon);
            Picasso.with(mContext).load(mDatas.get(position).getImageOldUrl()).into(viewHolder.player);
            Picasso.with(mContext).load(mDatas.get(position).getImageNewUrl()).into(viewHolder.player2);
            viewHolder.player.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: 点击的第" + position + "个条目的左面被点击");
                    Intent intent = new Intent(mContext, PlayerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fenmian", mDatas.get(position).getImageOldUrl());
                    bundle.putString("shipin", mDatas.get(position).getPlayerOldUrl());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            viewHolder.player2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: 点击的第" + position + "个条目的右面被点击");
                    Intent intent = new Intent(mContext, PlayerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fenmian", mDatas.get(position).getImageNewUrl());
                    bundle.putString("shipin", mDatas.get(position).getPlayernewUrl());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
        if (listPlayer.getCode() == ListPlayer.SELECT) {
            Log.i(TAG, "getView: 搜索条目加载");
            Picasso.with(mContext).load(mDatas.get(position).getHeadUrl()).into(viewHolder.img_video_icon);
            Picasso.with(mContext).load(mDatas.get(position).getImageOldUrl()).into(viewHolder.player);
            viewHolder.player2.setVisibility(View.GONE);
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewHolder.player.getLayoutParams();
            linearParams.height = 720;
            viewHolder.player2.setLayoutParams(linearParams);
//            viewHolder.player2.setScaleType(ImageView.ScaleType.CENTER);
            viewHolder.player.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: 点击的第" + position + "个条目的左面被点击");
                    Intent intent = new Intent(mContext, PlayerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fenmian", mDatas.get(position).getImageOldUrl());
                    bundle.putString("shipin", mDatas.get(position).getPlayerOldUrl());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }

        if (listPlayer.getCode() == ListPlayer.IMAGE) {
            viewHolder.player.setVisibility(View.GONE);
            viewHolder.player2.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(mContext).load(mDatas.get(position).getImageNewUrl()).resize(400,200).into(viewHolder.player2);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VRActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("imagUrl", listPlayer.getImageNewUrl());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
        if (listPlayer.getCode() == ListPlayer.TEXT) {
            Picasso.with(mContext).load(mDatas.get(position).getHeadUrl()).into(viewHolder.img_video_icon);
            Picasso.with(mContext).load(mDatas.get(position).getImageOldUrl()).into(viewHolder.player);
            viewHolder.player2.setVisibility(View.GONE);
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewHolder.player.getLayoutParams();
            linearParams.height = 720;
            viewHolder.player2.setLayoutParams(linearParams);
//            viewHolder.player.setVisibility(View.GONE);
//            viewHolder.player2.setVisibility(View.GONE);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("textUrl", listPlayer.getTextUrl());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    private final class ViewHolder {
        TextView tv_title;
        ImageView player;
        ImageView player2;
        ImageView img_video_icon;
        TextView tv_video_userName;
        TextView palyer_message;
        TextView tv_video_comment;
    }
}