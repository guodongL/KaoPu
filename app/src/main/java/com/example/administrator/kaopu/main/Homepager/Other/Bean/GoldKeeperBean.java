package com.example.administrator.kaopu.main.Homepager.Other.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class GoldKeeperBean {

    /**
     * img : ["http://192.168.1.20:8180/demo/img/goldManager/1.png"]
     * stores : 保工街店
     * name : 王雷
     * id : 1
     * position : 见习管家
     */

    private String stores;
    private String name;
    private String id;
    private String position;
    private List<String> img;

    public String getStores() {
        return stores;
    }

    public void setStores(String stores) {
        this.stores = stores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
