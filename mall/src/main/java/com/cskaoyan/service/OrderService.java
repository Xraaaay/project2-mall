package com.cskaoyan.service;

import com.cskaoyan.bean.po.MarketOrderListPo;

/**
 * @author changyong
 * @since 2022/07/16 22:50
 */

public interface OrderService {

    MarketOrderListPo list(Integer page, Integer limit, String sort, String order, String start, String end);
}
