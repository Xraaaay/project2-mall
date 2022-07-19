package com.cskaoyan.bean.wx.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pqk
 * @since 2022/07/19 22:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsListVo {

    /**
     * brief : iPhone
     * picUrl : http://182.92.235.201:8083/wx/storage/fetch/d7x9tvkx3agurdu0310p.jpg
     * name : 手机
     * counterPrice : 100
     * id : 1181025
     * isNew : true
     * retailPrice : 100
     * isHot : false
     */
    private String brief;
    private String picUrl;
    private String name;
    private Integer counterPrice;
    private Integer id;
    private boolean isNew;
    private Integer retailPrice;
    private boolean isHot;

}
