package com.cskaoyan.bean.admin.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @since 2022/07/17 15:06
 * @author lyx
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketStorageListVo {

    /**
     * deleted : false
     * size : 348103
     * addTime : 2022-07-17 14:19:10
     * name : SpringMVC的核心流程.jpg
     * updateTime : 2022-07-17 14:19:10
     * id : 268
     * type : image/jpeg
     * key : hzklp1wsngtaiicr42d9.jpg
     * url : http://182.92.235.201:8083/wx/storage/fetch/hzklp1wsngtaiicr42d9.jpg
     */
    private boolean deleted;
    private int size;
    private String addTime;
    private String name;
    private String updateTime;
    private int id;
    private String type;
    private String key;
    private String url;


}
