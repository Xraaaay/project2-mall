package com.cskaoyan.bean.wx.footprint;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 浏览足迹信息
 *
 * @author Zah
 * @date 2022/07/20 10:05
 */
@Data
public class FootprintInfoVo {

    /**
     * brief : 111111111111111111111111
     * picUrl : http://182.92.235.201:8083/wx/storage/fetch/fukqx164rkacnnc6ho8e.jpg
     * addTime : 2022-07-19 15:22:21
     * goodsId : 1181070
     * name : 666666666666666666666666666666
     * id : 615
     * retailPrice : 0.0
     */

    private String brief;
    private String picUrl;
    private Date addTime;
    private Integer goodsId;
    private String name;
    private Integer id;
    private BigDecimal retailPrice;

}
