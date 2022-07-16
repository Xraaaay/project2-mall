package com.cskaoyan.service;

import com.cskaoyan.bean.MarketOrder;
import com.cskaoyan.bean.MarketOrderExample;
import com.cskaoyan.bean.po.MarketBrandListPo;
import com.cskaoyan.bean.po.MarketOrderListPo;
import com.cskaoyan.mapper.MarketOrderMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/16 22:51
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    MarketOrderMapper marketOrderMapper;

    @Override
    public MarketOrderListPo list(Integer page, Integer limit, String sort, String order, String start, String end) {
        MarketOrderExample marketOrderExample1 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria1 = marketOrderExample1.createCriteria();
        long total = marketOrderMapper.countByExample(marketOrderExample1);
        Integer pages = Math.toIntExact((total / limit) + 1);
        //分页之后查询
        PageHelper.startPage(page, limit);
        List<MarketOrder> marketBrands = marketOrderMapper.selectByExample(marketOrderExample1);
        return new MarketOrderListPo(total, pages, new Long(limit), page, marketBrands);
    }
}
