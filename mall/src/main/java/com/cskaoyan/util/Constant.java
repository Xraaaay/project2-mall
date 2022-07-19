package com.cskaoyan.util;

import com.cskaoyan.bean.po.HandleOption;
import com.cskaoyan.bean.po.MarketChannel;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author changyong
 * @since 2022/07/19 09:20
 */

public class Constant {

    public static final Map<Integer, HandleOption> handleOptionMap = new HashMap<>();

    public static final List<MarketChannel> channelList = new LinkedList<>();

    static {
        HandleOption handleOption1 = new HandleOption();
        handleOption1.setDelete(true);
        handleOptionMap.put(102, handleOption1);
        handleOptionMap.put(103, handleOption1);
        handleOptionMap.put(203, handleOption1);
        HandleOption handleOption2 = new HandleOption();
        handleOption2.setRefund(true);
        handleOptionMap.put(201, handleOption2);
        HandleOption handleOption3 = new HandleOption();
        handleOptionMap.put(202, handleOption3);
        HandleOption handleOption4 = new HandleOption();
        handleOption4.setConfirm(true);
        handleOptionMap.put(301, handleOption4);
        HandleOption handleOption5 = new HandleOption();
        handleOption5.setDelete(true);
        handleOption5.setAftersale(true);
        handleOption5.setRebuy(true);
        handleOption5.setComment(true);
        handleOptionMap.put(401, handleOption5);
        handleOptionMap.put(402, handleOption5);
        HandleOption handleOption6 = new HandleOption();
        handleOption6.setDelete(true);
        handleOption6.setAftersale(true);
        handleOption6.setRebuy(true);
        //系统无对应状态码，自己定义“已收货已评价”为403
        handleOptionMap.put(403, handleOption6);
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
