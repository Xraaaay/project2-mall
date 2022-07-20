package com.cskaoyan.util;

import com.cskaoyan.bean.admin.mallmanagement.po.HandleOption;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketChannel;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 一些数据库中不存在的数据，但不想建表
 *
 * @author changyong
 * @since 2022/07/19 09:20
 */

public class Constant {
    /**
     * wx/order/list及wx/order/detail 请求需要的handleOption
     */
    public static final Map<Short, HandleOption> handleOptionMap = new HashMap<>();
    /**
     * wx/order/list 由showType得到相应状态码
     */
    public static final Map<Integer, Integer> showTypeMap = new HashMap<>();
    /**
     * wx/order/list 由orderStatus得到相应orderStatusText
     */
    public static final Map<Short, String> orderStatusTextMap = new HashMap<>();

    /**
     * admin/order/channel 请求需要的快递公司列表channelList
     */
    public static final List<MarketChannel> channelList = new LinkedList<>();

    static {
        HandleOption handleOption1 = new HandleOption();
        handleOption1.setDelete(true);
        handleOptionMap.put((short)102, handleOption1);
        handleOptionMap.put((short)103, handleOption1);
        handleOptionMap.put((short)203, handleOption1);
        HandleOption handleOption2 = new HandleOption();
        handleOption2.setRefund(true);
        handleOptionMap.put((short)201, handleOption2);
        HandleOption handleOption3 = new HandleOption();
        handleOptionMap.put((short)202, handleOption3);
        HandleOption handleOption4 = new HandleOption();
        handleOption4.setConfirm(true);
        handleOptionMap.put((short)301, handleOption4);
        HandleOption handleOption5 = new HandleOption();
        handleOption5.setDelete(true);
        handleOption5.setAftersale(true);
        handleOption5.setRebuy(true);
        handleOption5.setComment(true);
        handleOptionMap.put((short)401, handleOption5);
        handleOptionMap.put((short)402, handleOption5);
        HandleOption handleOption6 = new HandleOption();
        handleOption6.setDelete(true);
        handleOption6.setAftersale(true);
        handleOption6.setRebuy(true);
        //系统无对应状态码，自己定义“已收货已评价”为403
        handleOptionMap.put((short)403, handleOption6);
        HandleOption handleOption7 = new HandleOption();
        handleOption7.setPay(true);
        handleOption7.setCancel(true);
        handleOptionMap.put((short)101,handleOption7);
    }

    static {
        showTypeMap.put(1,101);
        showTypeMap.put(2,201);
        showTypeMap.put(3,301);
        showTypeMap.put(4,401);
    }

    static {
        orderStatusTextMap.put((short) 101,"待付款");
        orderStatusTextMap.put((short) 102,"已取消（用户）");
        orderStatusTextMap.put((short)103,"已取消（系统）");
        orderStatusTextMap.put((short)201,"已付款");
        orderStatusTextMap.put((short)202,"订单取消，退款中");
        orderStatusTextMap.put((short)203,"已退款");
        orderStatusTextMap.put((short)301,"已发货");
        orderStatusTextMap.put((short)401,"已收货");
        orderStatusTextMap.put((short)402,"已收货");

    }

    static {
        channelList.add(new MarketChannel("ZTO", "中通快递"));
        channelList.add(new MarketChannel("YTO", "圆通速递"));
        channelList.add(new MarketChannel("YD", "韵达速递"));
        channelList.add(new MarketChannel("YZPY", "邮政快递包裹"));
        channelList.add(new MarketChannel("EMS", "EMS"));
        channelList.add(new MarketChannel("DBL", "德邦快递"));
        channelList.add(new MarketChannel("FAST", "快捷快递"));
        channelList.add(new MarketChannel("ZJS", "宅急送"));
        channelList.add(new MarketChannel("TNT", "TNT快递"));
        channelList.add(new MarketChannel("UPS", "UPS"));
        channelList.add(new MarketChannel("DHL", "DHL"));
        channelList.add(new MarketChannel("FEDEX", "FEDEX联邦(国内件)"));
        channelList.add(new MarketChannel("FEDEX_GJ", "FEDEX联邦(国际件)"));
    }
}
