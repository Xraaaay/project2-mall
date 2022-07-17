package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.po.MarketOrderDetailPo;
import com.cskaoyan.bean.po.MarketOrderListPo;
import com.cskaoyan.bean.bo.MarketOrderListBo;
import com.cskaoyan.mapper.MarketOrderGoodsMapper;
import com.cskaoyan.mapper.MarketOrderMapper;
import com.cskaoyan.mapper.MarketUserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author changyong
 * @since 2022/07/16 22:51
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    MarketOrderMapper marketOrderMapper;

    @Autowired
    MarketUserMapper marketUserMapper;

    @Autowired
    MarketOrderGoodsMapper marketOrderGoodsMapper;

    @Override
    public MarketOrderListPo list(MarketOrderListBo marketOrderListBo) {
        //创建marketOrderExample
        MarketOrderExample marketOrderExample1 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria1 = marketOrderExample1.createCriteria();
        marketOrderExample1.setOrderByClause(marketOrderListBo.getSort());
        //赋值
        String[] timeArray = marketOrderListBo.getTimeArray();
        if (timeArray.length!=0&&timeArray[0] != null && timeArray[1] != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                criteria1.andPayTimeBetween(sdf.parse(timeArray[0]), sdf.parse(timeArray[1]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String[] orderStatusArray = marketOrderListBo.getOrderStatusArray();
        List<Short> orderStatusList = new LinkedList<>();
        for (String s : orderStatusArray) {
            if(s!=null){
                orderStatusList.add((short) Integer.parseInt(s));
            }
        }
        if (orderStatusList.size() != 0) {
            criteria1.andOrderStatusIn(orderStatusList);
        }
        if (marketOrderListBo.getOrderId() != null && !"".equals(marketOrderListBo.getOrderId())) {
            criteria1.andIdEqualTo(marketOrderListBo.getOrderId());
        }
        if (marketOrderListBo.getUserId() != null && !"".equals(marketOrderListBo.getUserId())) {
            criteria1.andUserIdEqualTo(marketOrderListBo.getUserId());
        }
        if (marketOrderListBo.getOrderSn() != null && !"".equals(marketOrderListBo.getOrderSn())) {
            criteria1.andOrderSnEqualTo(marketOrderListBo.getOrderSn());
        }
        //分页之后查询
        PageHelper.startPage(marketOrderListBo.getPage(), marketOrderListBo.getLimit());
        List<MarketOrder> marketBrands = marketOrderMapper.selectByExample(marketOrderExample1);
        //获取total及pages
        long total = marketOrderMapper.countByExample(marketOrderExample1);
        Integer pages = Math.toIntExact((total / marketOrderListBo.getLimit()) + 1);
        return new MarketOrderListPo(total, pages, new Long(marketOrderListBo.getLimit()), marketOrderListBo.getPage(), marketBrands);
    }

    @Override
    public MarketOrderDetailPo detail(Integer id) {
        //获取order
        MarketOrder marketOrder = marketOrderMapper.selectByPrimaryKey(id);
        //获取user
        MarketUser marketUser = marketUserMapper.selectByPrimaryKey(marketOrder.getUserId());
        //获取orderGoods
        MarketOrderGoodsExample marketOrderGoodsExample = new MarketOrderGoodsExample();
        MarketOrderGoodsExample.Criteria criteria = marketOrderGoodsExample.createCriteria();
        criteria.andOrderIdEqualTo(marketOrder.getId());
        List<MarketOrderGoods> marketOrderGoods = marketOrderGoodsMapper.selectByExample(marketOrderGoodsExample);

        return new MarketOrderDetailPo(marketOrderGoods, marketUser, marketOrder);
    }

    @Override
    public void ship(Integer orderId, String shipChannel, String shipSn) {
        //获取order
        MarketOrder marketOrder = marketOrderMapper.selectByPrimaryKey(orderId);
        //赋值
        //TODO 前端页面 快递公司无数据，无法选择
        marketOrder.setShipChannel(shipChannel);
        marketOrder.setShipSn(shipSn);
        Date shipTime = new Date();
        marketOrder.setShipTime(shipTime);
        marketOrder.setOrderStatus((short) 301);
        marketOrder.setUpdateTime(shipTime);
        //更新订单状态
        marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
    }
}
