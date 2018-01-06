package com.example.hao.hua5.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2017/9/11.
 * 数据
 */

public class DB {
    public static Map<String, List<ListPlayer>> db;

    public static Map<String, List<ListPlayer>> getDb() {
        db = new HashMap<>();

        ArrayList<ListPlayer> listPlayers = new ArrayList<>();
        ListPlayer listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.VIDEO);
        listPlayer.setTitle("西安古今差异");
        listPlayer.setImageOldUrl("http://119.28.27.175/left.png");
        listPlayer.setImageNewUrl("http://119.28.27.175/right.png");
//        listPlayer.setHeadUrl("http://123.206.74.213/1.jpg");
        listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/chinaold1.mp4");
        listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/chinanow1.mp4");
        listPlayers.add(listPlayer);

//        ListPlayer listPlayer2 = new ListPlayer();
        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.IMAGE);
        listPlayer.setTitle("西安鼓楼夜景");
        listPlayer.setImageNewUrl("http://oukncmq42.bkt.clouddn.com/xian1.jpg");
        listPlayers.add(listPlayer);

//        ListPlayer listPlayer7 = new ListPlayer();
        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.VIDEO);
        listPlayer.setTitle("西安的历史今天");
        listPlayer.setImageOldUrl("http://oukncmq42.bkt.clouddn.com/left1111.jpg");
        listPlayer.setImageNewUrl("http://oukncmq42.bkt.clouddn.com/right1.jpg");
        listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/chinaold2.mp4");
        listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/chinaold3.mp4");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.TEXT);
        listPlayer.setTitle("文化古都——西安");
        listPlayer.setImageOldUrl("http://182.254.208.91/post/m.ce.cn/images/xian.jpeg");
        listPlayer.setTextUrl("http://182.254.208.91/post/m.ce.cn/gj/gd/201709/11/t20170911_25893631.html");
        listPlayers.add(listPlayer);

//        ListPlayer listPlayer4 = new ListPlayer();
//        listPlayer4.setCode(ListPlayer.IMAGE);
//        listPlayer4.setTitle("昆明站随手拍");
//        listPlayer4.setImageNewUrl("https://m.weibo.cn/z/panorama/img?oid=1042143:9dd29229dbb6d68fff307b0a3c5aa8fe");
//        listPlayers.add(listPlayer4);

        db.put("西安", listPlayers);

        //阿斯塔纳
        listPlayers = new ArrayList<>();
        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.VIDEO);
        listPlayer.setTitle("美丽哈萨克斯坦");
        listPlayer.setImageOldUrl("http://119.28.27.175/ha1.jpg");
        listPlayer.setImageNewUrl("http://119.28.27.175/ha2.jpg");
//        listPlayer.setHeadUrl("http://123.206.74.213/1.jpg");
        listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/hasakesitan1.mp4");
        listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/hasakesitan2.mp4");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.IMAGE);
        listPlayer.setTitle("村落小景");
        listPlayer.setImageNewUrl("https://panoimg.us.sinaimg.cn/0031L7tOjx07dQAtseub010f01003hS90k01?Expires=1505120167&ssig=W4rQbvw3i%2B&KID=unistore,video");
//        listPlayer.setImageNewUrl("http://123.206.74.213/1.png");
        listPlayers.add(listPlayer);
        db.put("阿斯塔纳", listPlayers);

        //威尼斯
        listPlayers = new ArrayList<>();
        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.VIDEO);
        listPlayer.setTitle("威尼斯之路");
        listPlayer.setImageOldUrl("http://oukncmq42.bkt.clouddn.com/veniceold.jpg");
        listPlayer.setImageNewUrl("http://oukncmq42.bkt.clouddn.com/venicenow.jpg");
//        listPlayer.setHeadUrl("http://123.206.74.213/1.jpg");
        listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/veniceold.mp4");
        listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/Venicenow.mp4");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.IMAGE);
        listPlayer.setTitle("威尼斯水城");
        listPlayer.setImageNewUrl("http://oukncmq42.bkt.clouddn.com/nuowei.jpg");
//        listPlayer.setImageNewUrl("http://123.206.74.213/1.png");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.TEXT);
        listPlayer.setTitle("美丽的水上城市——威尼斯");
        listPlayer.setImageOldUrl("http://182.254.208.91/post/m.ce.cn/images/weinisi.jpeg");
        listPlayer.setTextUrl("http://182.254.208.91/post/m.ce.cn/gj/gd/201709/11/t20170911_25886375.html");
        listPlayers.add(listPlayer);

        db.put("威尼斯", listPlayers);

        //印度
        listPlayers = new ArrayList<>();
        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.VIDEO);
        listPlayer.setTitle("印度风情");
        listPlayer.setImageOldUrl("http://oukncmq42.bkt.clouddn.com/indialeft.jpg");
        listPlayer.setImageNewUrl("http://oukncmq42.bkt.clouddn.com/indiaright.jpg");
//        listPlayer.setHeadUrl("http://123.206.74.213/1.jpg");
        listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/indiaold.mp4");
        listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/indianow.mp4");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.IMAGE);
        listPlayer.setTitle("航拍云海");
        listPlayer.setImageNewUrl("https://wx4.sinaimg.cn/large/46afb05ely1fjpv9evxsij235s1kw17c.jpg");
//        listPlayer.setImageNewUrl("http://123.206.74.213/1.png");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.TEXT);
        listPlayer.setTitle("不可思议的印度名城");
        listPlayer.setImageOldUrl("http://182.254.208.91/post/m.ce.cn/images/xindeli.jpeg");
        listPlayer.setTextUrl("http://182.254.208.91/post/m.ce.cn/gj/gd/201709/11/t20170911_25886421.html");
        listPlayers.add(listPlayer);

        db.put("新德里", listPlayers);

        //北京
        listPlayers = new ArrayList<>();

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.VIDEO);
        listPlayer.setTitle("中国首都北京");
        listPlayer.setImageOldUrl("http://oukncmq42.bkt.clouddn.com/beijingleft.jpg");
        listPlayer.setImageNewUrl("http://oukncmq42.bkt.clouddn.com/beijingright.jpg");
        listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/beijingu_.mp4");
        listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/beijingjin_.mp4");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.IMAGE);
        listPlayer.setTitle("紫禁城");
        listPlayer.setImageNewUrl("https://panoimg.us.sinaimg.cn/000PBw1Kjx07dEXCZDbF010f01004HI50k01?Expires=1508558914&ssig=i9NvhOHH9S&KID=unistore,video");
//        listPlayer.setImageNewUrl("http://123.206.74.213/1.png");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.TEXT);
        listPlayer.setTitle("文化古都——北京");
        listPlayer.setImageOldUrl("http://182.254.208.91/post/m.ce.cn/images/beijing.jpg");
        listPlayer.setTextUrl("http://182.254.208.91/post/m.ce.cn/gj/gd/201709/11/t20170911_25886440.html");
        listPlayers.add(listPlayer);

        db.put("北京", listPlayers);

        //郑州
        listPlayers = new ArrayList<>();
        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.IMAGE);
        listPlayer.setTitle("山谷里的星空");
        listPlayer.setImageNewUrl("https://wx4.sinaimg.cn/large/006Qiggsly1fjzn8p3gp9j335s1kwkjl.jpg");
//        listPlayer.setImageNewUrl("http://123.206.74.213/1.png");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.TEXT);
        listPlayer.setTitle("“九州之中，十省通衢” -- 郑州");
        listPlayer.setImageOldUrl("http://182.254.208.91/post/m.ce.cn/images/zhengzhou.png");
        listPlayer.setTextUrl("http://182.254.208.91/post/m.ce.cn/gj/gd/201709/11/t20170911_25886499.html");
        listPlayers.add(listPlayer);
        db.put("郑州", listPlayers);

        //南京
        listPlayers = new ArrayList<>();

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.VIDEO);
        listPlayer.setTitle("古都与新潮");
        listPlayer.setImageOldUrl("http://oukncmq42.bkt.clouddn.com/nanjingleft.jpg");
        listPlayer.setImageNewUrl("http://oukncmq42.bkt.clouddn.com/nanjingright.jpg");
        listPlayer.setPlayerOldUrl("http://oukncmq42.bkt.clouddn.com/nanjinggu_.mp4");
        listPlayer.setPlayernewUrl("http://oukncmq42.bkt.clouddn.com/nanjingjin_.mp4");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.IMAGE);
        listPlayer.setTitle("南京玄武湖");
        listPlayer.setImageNewUrl("https://wx4.sinaimg.cn/large/58f10d9bly1fjsesooe4mj235s1kwnlu.jpg");
//        listPlayer.setImageNewUrl("http://123.206.74.213/1.png");
        listPlayers.add(listPlayer);

        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.TEXT);
        listPlayer.setTitle("六朝古都--南京");
        listPlayer.setImageOldUrl("http://182.254.208.91/post/m.ce.cn/images/nanjing.jpg");
        listPlayer.setTextUrl("http://182.254.208.91/post/m.ce.cn/gj/gd/201709/11/t20170911_25886458.html");
        listPlayers.add(listPlayer);

        db.put("南京", listPlayers);

        listPlayers = new ArrayList<>();
        listPlayer = new ListPlayer();
        listPlayer.setCode(ListPlayer.TEXT);
        listPlayer.setTitle("苹果城”——阿拉木图");
        listPlayer.setImageOldUrl("http://182.254.208.91/post/m.ce.cn/images/alamutu.jpg");
        listPlayer.setTextUrl("http://182.254.208.91/post/m.ce.cn/gj/gd/201709/11/t20170911_25886520.html");
        listPlayers.add(listPlayer);
        db.put("阿拉木图", listPlayers);
        return db;
    }
}
