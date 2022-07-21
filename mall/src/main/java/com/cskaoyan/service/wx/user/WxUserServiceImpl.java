package com.cskaoyan.service.wx.user;


import com.cskaoyan.bean.common.MarketOrder;
import com.cskaoyan.bean.common.MarketOrderExample;
import com.cskaoyan.bean.admin.mallmanagement.po.OrderPo;
import com.cskaoyan.bean.admin.mallmanagement.po.WxUserIndexOrderPo;
import com.cskaoyan.bean.common.MarketOrderGoodsExample;
import com.cskaoyan.mapper.common.MarketOrderGoodsMapper;
import com.cskaoyan.mapper.common.MarketOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/18 16:42
 */
@Component
@Transactional
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    MarketOrderMapper marketOrderMapper;

    @Autowired
    MarketOrderGoodsMapper marketOrderGoodsMapper;

    /**
     * 返回个人中心首页
     *
     * @return com.cskaoyan.bean.admin.mallmanagement.po.WxUserIndexOrderPo
     * @author changyong
     * @since 2022/07/20 11:54
     */
    @Override
    public WxUserIndexOrderPo index() {

        //页面显示的数量是订单数量（未付款，代发货，已发货）和已收货订单的待评价商品数量（待评价），需先查出所有order，再foreach，得到所有商品总数
        //获取未付款订单的总数
        MarketOrderExample marketOrderExample1 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria1 = marketOrderExample1.createCriteria();
        criteria1.andOrderStatusEqualTo((short) 101)
                .andDeletedEqualTo(false);
        long unpaid = marketOrderMapper.countByExample(marketOrderExample1);
        // long unpaid = 0L;
        // List<MarketOrder> marketOrderList1 = marketOrderMapper.selectByExample(marketOrderExample1);
        // for (int i = 0; i < marketOrderList1.size(); i++) {
        //     MarketOrderGoodsExample marketOrderGoodsExample = new MarketOrderGoodsExample();
        //     MarketOrderGoodsExample.Criteria criteria = marketOrderGoodsExample.createCriteria();
        //     criteria.andOrderIdEqualTo(marketOrderList1.get(i).getId())
        //             .andDeletedEqualTo(false);
        //     long l = marketOrderGoodsMapper.countByExample(marketOrderGoodsExample);
        //     unpaid = unpaid + l;
        // }

        //获取未发货订单的总数
        MarketOrderExample marketOrderExample2 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria2 = marketOrderExample2.createCriteria();
        criteria2.andOrderStatusEqualTo((short) 201)
                .andDeletedEqualTo(false);
        long unship = marketOrderMapper.countByExample(marketOrderExample2);
        // List<MarketOrder> marketOrderList2 = marketOrderMapper.selectByExample(marketOrderExample2);
        // long unship = 0L;
        // for (int i = 0; i < marketOrderList2.size(); i++) {
        //     MarketOrderGoodsExample marketOrderGoodsExample = new MarketOrderGoodsExample();
        //     MarketOrderGoodsExample.Criteria criteria = marketOrderGoodsExample.createCriteria();
        //     criteria.andOrderIdEqualTo(marketOrderList2.get(i).getId())
        //             .andDeletedEqualTo(false);
        //     long l = marketOrderGoodsMapper.countByExample(marketOrderGoodsExample);
        //     unship = unship + l;
        // }

        //获取未收货订单的总数
        MarketOrderExample marketOrderExample3 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria3 = marketOrderExample3.createCriteria();
        criteria3.andOrderStatusEqualTo((short) 301)
                .andDeletedEqualTo(false);
        long unrecv = marketOrderMapper.countByExample(marketOrderExample3);
        // List<MarketOrder> marketOrderList3 = marketOrderMapper.selectByExample(marketOrderExample3);
        // long unrecv = 0L;
        // for (int i = 0; i < marketOrderList3.size(); i++) {
        //     MarketOrderGoodsExample marketOrderGoodsExample = new MarketOrderGoodsExample();
        //     MarketOrderGoodsExample.Criteria criteria = marketOrderGoodsExample.createCriteria();
        //     criteria.andOrderIdEqualTo(marketOrderList3.get(i).getId())
        //             .andDeletedEqualTo(false);
        //     long l = marketOrderGoodsMapper.countByExample(marketOrderGoodsExample);
        //     unrecv = unrecv + l;
        // }

        //获取未评价订单的商品总数
        MarketOrderExample marketOrderExample4 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria4 = marketOrderExample4.createCriteria();
        criteria4.andOrderStatusBetween((short) 401, (short) 402)
                .andDeletedEqualTo(false);
        // long uncomment = marketOrderMapper.countByExample(marketOrderExample4);
        List<MarketOrder> marketOrderList4 = marketOrderMapper.selectByExample(marketOrderExample4);
        long uncomment = 0L;
        for (int i = 0; i < marketOrderList4.size(); i++) {
            // MarketOrderGoodsExample marketOrderGoodsExample = new MarketOrderGoodsExample();
            // MarketOrderGoodsExample.Criteria criteria = marketOrderGoodsExample.createCriteria();
            // criteria.andOrderIdEqualTo(marketOrderList4.get(i).getId())
            //         .andDeletedEqualTo(false);
            // long l = marketOrderGoodsMapper.countByExample(marketOrderGoodsExample);
            //查询order表中“comments”字段表示该订单尚未评价的商品数量
            long l=marketOrderList4.get(i).getComments();
            uncomment = uncomment + l;
        }
        return new WxUserIndexOrderPo(new OrderPo(uncomment, unpaid, unrecv, unship));
    }
}
