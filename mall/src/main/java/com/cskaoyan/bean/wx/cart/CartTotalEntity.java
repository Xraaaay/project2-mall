package com.cskaoyan.bean.wx.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @since 2022/07/19 11:30
 * @author lyx
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartTotalEntity {

    /**
     * goodsCount : 1
     * checkedGoodsCount : 0
     * goodsAmount : 6488.0
     * checkedGoodsAmount : 0
     */
    private int goodsCount;
    private int checkedGoodsCount;
    private double goodsAmount;
    private int checkedGoodsAmount;

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public void setCheckedGoodsCount(int checkedGoodsCount) {
        this.checkedGoodsCount = checkedGoodsCount;
    }

    public void setGoodsAmount(double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public void setCheckedGoodsAmount(int checkedGoodsAmount) {
        this.checkedGoodsAmount = checkedGoodsAmount;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public int getCheckedGoodsCount() {
        return checkedGoodsCount;
    }

    public double getGoodsAmount() {
        return goodsAmount;
    }

    public int getCheckedGoodsAmount() {
        return checkedGoodsAmount;
    }
}
