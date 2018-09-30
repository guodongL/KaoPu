package com.example.administrator.kaopu.main.Homepager.Other.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class InformationBean {


    /**
     * img : ["http://192.168.1.20:8180/demo/img/newHouse/1111.jpg"]
     * id : 1
     * upinfo : 今日上午，辽宁广播电视台经济频道《直播生活》主持人关键先生成功与靠谱不动产签约，正式成为靠谱不动产品牌形象代言人！啊，你没看错，就是那个多才多艺，沉稳、儒雅，又有点憨厚可爱的辽沈地区人气主播——关键帅哥！有靠谱，更幸福，大叔可不是说说而已的！这不，俺们沈阳这旮沓嗷嗷招老百姓稀罕，也让大家觉得人实在靠谱的MR . Clutch——关键先生 ，都来给靠谱大叔站台代言了，是不是证明了大叔的人格魅力那也是没谁了？
     * lowinfo : 关键挺靠谱，靠谱忒关键！靠谱的关键就是要让大家生活更幸福！不是一家人不进一家门，看看，俺爷俩这笑眯眯的模样，是不是有点连相？辽宁广播电视台经济频道《直播生活》栏目是2001年沈阳广播电视台推出的生活新闻栏目，因其平民化视角和生活化风格赢得了观众的关注，首创普通市民主播电视新闻的播报形式，及时快捷犀利的反映社会现实，针砭时弊，具有很高的收视率，深受广大辽沈区域观众喜欢。“直播生活就在你触手可及的地方”是栏目的宣传语，更是观众对栏目的认知，栏目在辽沈地区具有绝对强势的信誉度和知名度。
     * purDate : 2017-8-20
     * title : 辽宁广播电视台经济频道《直播生活》主持人关键成为靠谱不动产形象代言人
     */

    private String id;
    private String upinfo;
    private String lowinfo;
    private String purDate;
    private String title;
    private List<String> img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpinfo() {
        return upinfo;
    }

    public void setUpinfo(String upinfo) {
        this.upinfo = upinfo;
    }

    public String getLowinfo() {
        return lowinfo;
    }

    public void setLowinfo(String lowinfo) {
        this.lowinfo = lowinfo;
    }

    public String getPurDate() {
        return purDate;
    }

    public void setPurDate(String purDate) {
        this.purDate = purDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
