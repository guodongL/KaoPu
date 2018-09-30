package com.example.administrator.kaopu.main.Details;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class NewDetailsBean {

    /**
     * familyType : 2室 2厅 1卫
     * img : ["http://192.168.1.20:8180/demo/img/newHouse/20170504102753_32nic4w15x.jpg","http://192.168.1.20:8180/demo/img/newHouse/20170504102756_oa2d7y9v7w.jpg"]
     * address : 浑南区-浑南二路
     * developers : 保利达地产（沈阳）高悦有限公司
     * buildingArea : 75.0万㎡
     * imgHouse : ["http://192.168.1.20:8180/demo/img/newHouse/20170504103643_kptpoqx3ui.jpg","http://192.168.1.20:8180/demo/img/newHouse/20170504103646_ki5xehp87c.jpg"]
     * fitment : 毛坯
     * afforestation : 40%
     * affiliation : 奥体
     * phone : 13238859001
     * price : 8600 元/平
     * name : 保利达江湾城
     * propertyTime : 50年
     * id : 2
     * openTime : 2015-12-20
     * covers : 16.55万㎡
     */

    private String familyType;
    private String address;
    private String developers;
    private String buildingArea;
    private String fitment;
    private String afforestation;
    private String affiliation;
    private String phone;
    private String price;
    private String name;
    private String propertyTime;
    private String id;
    private String openTime;
    private String covers;
    private List<String> img;
    private List<String> imgHouse;

    public String getFamilyType() {
        return familyType;
    }

    public void setFamilyType(String familyType) {
        this.familyType = familyType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getFitment() {
        return fitment;
    }

    public void setFitment(String fitment) {
        this.fitment = fitment;
    }

    public String getAfforestation() {
        return afforestation;
    }

    public void setAfforestation(String afforestation) {
        this.afforestation = afforestation;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropertyTime() {
        return propertyTime;
    }

    public void setPropertyTime(String propertyTime) {
        this.propertyTime = propertyTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public List<String> getImgHouse() {
        return imgHouse;
    }

    public void setImgHouse(List<String> imgHouse) {
        this.imgHouse = imgHouse;
    }
}
