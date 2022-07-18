package com.cskaoyan.service;


import com.cskaoyan.bean.MarketOrderExample;
import com.cskaoyan.bean.po.OrderPo;
import com.cskaoyan.bean.po.WxUserIndexOrderPo;
import com.cskaoyan.mapper.MarketOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author changyong
 * @since 2022/07/18 16:42
 */
@Component
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    MarketOrderMapper marketOrderMapper;

    @Override
    public WxUserIndexOrderPo index() {
        //获取未付款订单数量
        MarketOrderExample marketOrderExample1 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria1 = marketOrderExample1.createCriteria();
        criteria1.andOrderStatusEqualTo((short)101);
        long unpaid = marketOrderMapper.countByExample(marketOrderExample1);
        //获取未发货订单数量
        MarketOrderExample marketOrderExample2 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria2 = marketOrderExample2.createCriteria();
        criteria2.andOrderStatusEqualTo((short)201);
        long unship = marketOrderMapper.countByExample(marketOrderExample2);
        //获取未收货订单数量
        MarketOrderExample marketOrderExample3 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria3 = marketOrderExample3.createCriteria();
        criteria3.andOrderStatusEqualTo((short)301);
        long unrecv = marketOrderMapper.countByExample(marketOrderExample3);
        //获取未评价订单数量
        MarketOrderExample marketOrderExample4 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria4 = marketOrderExample4.createCriteria();
        criteria4.andOrderStatusBetween((short)401,(short)402)
                .andCommentsEqualTo((short)0);
        long uncomment = marketOrderMapper.countByExample(marketOrderExample4);

        return new WxUserIndexOrderPo( new OrderPo(uncomment,unpaid,unrecv,unship));
    }
}
