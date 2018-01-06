package com.example.hao.hua5.bean;

import java.io.Serializable;

/**
 * Created by hao on 2017/9/3.
 * 视频滑动列表
 */

public class ListPlayer implements Serializable {
    //    标识符
    int code;
    //    标题
    String title;
    //    古代视频url
    String playerOldUrl;
    //    现代视频url
    String playernewUrl;
    //    古代封面url
    String imageOldUrl;
    //    现代的url
    String imageNewUrl;
    //    头像url
    String headUrl;
    //    用户名称
    String userName;
    //    文章url
    String textUrl;
    public static int VIDEO = 0;
    public static int IMAGE = 1;
    public static int TEXT = 2;
    public static int SELECT = 3;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayerOldUrl() {
        return playerOldUrl;
    }

    public void setPlayerOldUrl(String playerOldUrl) {
        this.playerOldUrl = playerOldUrl;
    }

    public String getPlayernewUrl() {
        return playernewUrl;
    }

    public void setPlayernewUrl(String playernewUrl) {
        this.playernewUrl = playernewUrl;
    }

    public String getImageOldUrl() {
        return imageOldUrl;
    }

    public void setImageOldUrl(String imageOldUrl) {
        this.imageOldUrl = imageOldUrl;
    }

    public String getImageNewUrl() {
        return imageNewUrl;
    }

    public void setImageNewUrl(String imageNewUrl) {
        this.imageNewUrl = imageNewUrl;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTextUrl() {
        return textUrl;
    }

    public void setTextUrl(String textUrl) {
        this.textUrl = textUrl;
    }
}
