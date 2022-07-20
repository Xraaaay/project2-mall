package com.cskaoyan.bean.wx.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pqk
 * @since 2022/07/19 21:22
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListWxVo {

    /**
     * brief : 1212
     * picUrl : http://182.92.235.201:8083/wx/storage/fetch/kuv51worjo2ell608sw8.jpg
     * name : 12121212
     * counterPrice : 200.0
     * id : 1181039
     * isNew : true
     * retailPrice : 200.0
     * isHot : false
     */
    private String brief;
    private String picUrl;
    private String name;
    private double counterPrice;
    private int id;
    private boolean isNew;
    private double retailPrice;
    private boolean isHot;

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCounterPrice(double counterPrice) {
        this.counterPrice = counterPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void setIsHot(boolean isHot) {
        this.isHot = isHot;
    }

    public String getBrief() {
        return brief;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getName() {
        return name;
    }

    public double getCounterPrice() {
        return counterPrice;
    }

    public int getId() {
        return id;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public boolean isIsHot() {
        return isHot;
    }
}
