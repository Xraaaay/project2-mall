package com.cskaoyan.service.wx.order;

import com.cskaoyan.bean.admin.mallmanagement.po.HandleOption;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketChannel;
import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.order.*;
import com.cskaoyan.mapper.common.MarketCommentMapper;
import com.cskaoyan.mapper.common.MarketOrderGoodsMapper;
import com.cskaoyan.mapper.common.MarketOrderMapper;
import com.cskaoyan.util.Constant;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author changyong
 * @since 2022/07/19 10:44
 */
@Component
public class WxOrderServiceImpl implements WxOrderService {

    @Autowired
    MarketOrderMapper marketOrderMapper;

    @Autowired
    MarketOrderGoodsMapper marketOrderGoodsMapper;

    @Autowired
    MarketCommentMapper marketCommentMapper;

    @Override
    public WxOrderListPo list(Integer showType, Integer page, Integer limit) {
        //利用工具类Constant，由showType得到相应状态码
        MarketOrderExample marketOrderExample1 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria1 = marketOrderExample1.createCriteria();
        if (showType == 4) {
            Integer orderStatus = Constant.showTypeMap.get(showType);
            //showType=4 对应已收货待评价的订单
            criteria1.andOrderStatusBetween(orderStatus.shortValue(), (short) (orderStatus + 1))
                    .andCommentsEqualTo((short) 0);
        }
        if (showType != 0) {
            //showType=0 表示除了已被删除的订单之外的全部订单
            Integer orderStatus = Constant.showTypeMap.get(showType);
            criteria1.andOrderStatusEqualTo(orderStatus.shortValue());
        }
        //只显示未被删除的订单
        criteria1.andDeletedEqualTo(false);
        //获取total及pages
        long total = marketOrderMapper.countByExample(marketOrderExample1);
        long pages = (total / limit) + 1;
        //分页之后查询
        PageHelper.startPage(page, limit);
        //1.先查询order表，得到orderList
        List<MarketOrder> orderList = marketOrderMapper.selectByExample(marketOrderExample1);
        //2.foreach循环 order赋值给orderOfList(orderOfList有成员变量isGroupin，order表中无该数据，不能直接使用orderList)
        List<OrderOfList> orderOfLists = new LinkedList<>();
        for (MarketOrder order : orderList) {
            //3.循环里面，由orderId查询OrderGoods表，得到OrderGoodsList
            MarketOrderGoodsExample marketOrderGoodsExample2 = new MarketOrderGoodsExample();
            MarketOrderGoodsExample.Criteria criteria2 = marketOrderGoodsExample2.createCriteria();
            //只有删除订单后，order的goods的deleted=1，所以下面不用加条件“没有被删除”
            criteria2.andOrderIdEqualTo(order.getId());
            //数据库中specifications是String字符串，此处需使用typehandler将其转换为String数组
            List<MarketOrderGoods> orderGoods = marketOrderGoodsMapper.selectByExample(marketOrderGoodsExample2);
            //4.第二个foreach循环 orderGoods赋值给GoodsOforder，
            // （GoodsOfOrder数据在OrderGoods中都有，也可不进行第二次foreach循环，直接使用OrderGoods）
            List<GoodsOfOrder> goodsOfOrderList = new LinkedList<>();
            for (MarketOrderGoods orderGood : orderGoods) {
                GoodsOfOrder goodsOfOrder = new GoodsOfOrder(orderGood.getId(), orderGood.getNumber(), orderGood.getGoodsName(), orderGood.getPicUrl(), orderGood.getPrice(), orderGood.getSpecifications());
                goodsOfOrderList.add(goodsOfOrder);
            }
            //5.由orderStatus查得handleOption，赋值给Order成员变量
            HandleOption handleOption = null;
            if (order.getOrderStatus() == 401 || order.getOrderStatus() == 402) {
                //如果已收货
                if (order.getComments() == 1) {
                    //如果已评价
                    handleOption = Constant.handleOptionMap.get((short) 403);
                } else if (order.getComments() == 0) {
                    //如果未评价
                    handleOption = Constant.handleOptionMap.get(order.getOrderStatus());
                }
            } else {
                //TODO  如果状态码是101 未付款，则从map获取不到 为什么
                handleOption = Constant.handleOptionMap.get(order.getOrderStatus());
            }
            //生成orderOfList，并赋值
            OrderOfList orderOfList = new OrderOfList(order.getActualPrice(), order.getAftersaleStatus(), goodsOfOrderList, handleOption, order.getId(), false, order.getOrderSn(), Constant.orderStatusTextMap.get(order.getOrderStatus()));
            orderOfLists.add(orderOfList);
        }
        //6.第一个foreach循环结束，List<OrderOfList> 赋值给WxOrderListPo成员变量
        return new WxOrderListPo(total, Integer.valueOf((int) pages), limit.longValue(), page, orderOfLists);
    }

    @Override
    public WxOrderDetailPo detail(Integer id) {
        //获取orderGoods
        MarketOrderGoodsExample marketOrderGoodsExample1 = new MarketOrderGoodsExample();
        MarketOrderGoodsExample.Criteria criteria1 = marketOrderGoodsExample1.createCriteria();
        criteria1.andOrderIdEqualTo(id);
        List<MarketOrderGoods> orderGoods = marketOrderGoodsMapper.selectByExample(marketOrderGoodsExample1);
        //获取orderInfo
        //先获取order详细信息，再给orderInfo赋值
        MarketOrder marketOrder = marketOrderMapper.selectByPrimaryKey(id);
        //依靠工具类Constant和expCode得到expName
        //先判断订单是否有发货信息
        String expName = null;
        if (marketOrder.getShipChannel() != null) {
            for (MarketChannel channel : Constant.channelList) {
                if (marketOrder.getShipChannel().equals(channel.getCode())) {
                    expName = channel.getName();
                }
            }
        }
        //依靠工具类Constant获取handleOption
        HandleOption handleOption = Constant.handleOptionMap.get(marketOrder.getOrderStatus());
        //依靠工具类Constant获取orderStatusText
        String orderStatusText = Constant.orderStatusTextMap.get(marketOrder.getOrderStatus());
        OrderOfDetail orderOfDetail = new OrderOfDetail(marketOrder.getActualPrice(), marketOrder.getAddTime(), marketOrder.getAddress(),
                marketOrder.getAftersaleStatus(), marketOrder.getConsignee(), marketOrder.getCouponPrice(),
                marketOrder.getShipChannel(), expName, marketOrder.getShipSn(), marketOrder.getFreightPrice(),
                marketOrder.getGoodsPrice(), handleOption, marketOrder.getId(), marketOrder.getMessage(),
                marketOrder.getMobile(), marketOrder.getOrderSn(), orderStatusText);
        return new WxOrderDetailPo("", orderGoods, orderOfDetail);
    }

    @Override
    public void refund(Integer id) {
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setId(id);
        marketOrder.setOrderStatus((short) 202);
        marketOrder.setUpdateTime(new Date());
        marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
    }

    @Override
    public void comment(WxOrderCommentBo wxOrderCommentBo) {
        //TODO wxOrderCommentBo应该有orderGoodsId，但没有，导致评价插入失败。
        // orderGoodsId为orderGoods表id，根据其查询得到orderId，进而得到userId
        MarketComment marketComment = new MarketComment();
        marketComment.setValueId(wxOrderCommentBo.getOrderGoodsId());
        //TODO comment表中字段type和userId不知道如何获取
        marketComment.setType((byte) 0);
        marketComment.setContent(marketComment.getAdminContent());
        marketComment.setUserId(1);
        marketComment.setHasPicture(wxOrderCommentBo.isHasPicture());
        marketComment.setPicUrls(wxOrderCommentBo.getPicUrls());
        marketComment.setStar(wxOrderCommentBo.getStar().shortValue());
        Date addTime = new Date();
        marketComment.setAddTime(addTime);
        marketComment.setUpdateTime(addTime);
        marketCommentMapper.insertSelective(marketComment);
        //TODO 评价完成后将订单商品的handleoption改一下，即修改order表，orderGoods表 comment为1
    }

    @Override
    public List<MarketOrderGoods> goods(Integer orderId, Integer goodsId) {
        //TODO 我和参考页面对比了，返回对象和参考页面一致，但没有显示图片及图片右侧文字
        MarketOrderGoodsExample marketOrderGoodsExample = new MarketOrderGoodsExample();
        MarketOrderGoodsExample.Criteria criteria = marketOrderGoodsExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId)
                .andGoodsIdEqualTo(goodsId);
        List<MarketOrderGoods> marketOrderGoods = marketOrderGoodsMapper.selectByExample(marketOrderGoodsExample);
        return marketOrderGoods;
    }

    @Override
    public void delete(Integer id) {
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setId(id);
        marketOrder.setDeleted(true);
        marketOrder.setUpdateTime(new Date());
        //先获取该订单的状态，如果为 102: '用户取消', 103: '系统取消', 203: '已退款'，则可以删除订单
        Short orderStatus = marketOrderMapper.selectByPrimaryKey(id).getOrderStatus();
        if ((short) 102 == orderStatus || (short) 103 == orderStatus || (short) 203 == orderStatus) {
            marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
        }

    }

    @Override
    public void confirm(Integer id) {
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setId(id);
        //TODO 系统收货（402）该怎么做呢
        marketOrder.setOrderStatus((short) 401);
        Date confirmTime = new Date();
        marketOrder.setConfirmTime(confirmTime);
        marketOrder.setUpdateTime(confirmTime);
        //先获取该订单的状态，如果为 301: '已发货'，则可以收货
        Short orderStatus = marketOrderMapper.selectByPrimaryKey(id).getOrderStatus();
        if ((short) 301 == orderStatus) {
            marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
        }
    }
}
