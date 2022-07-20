package com.cskaoyan.bean.wx.wxcollect;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 小程序 收藏模块
 *
 * @author shn
 * @date 2022/07/19 21:27
 */
@Data
public class InnerListOfWXCollectVo {

    private String brief;
    private String picUrl;
    private int valueId;
    private String name;
    private int id;
    private int type;
    private BigDecimal retailPrice;


}
