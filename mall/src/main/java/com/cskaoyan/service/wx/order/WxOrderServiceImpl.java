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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author changyong
 * @since 2022/07/19 10:44
 */
@Component
@Transactional
public class WxOrderServiceImpl implements WxOrderService {

    @Autowired
    MarketOrderMapper marketOrderMapper;

    @Autowired
    MarketOrderGoodsMapper marketOrderGoodsMapper;

    @Autowired
    MarketCommentMapper marketCommentMapper;

    /**
     * 返回订单列表
     *
     * @param showType
     * @param page
     * @param limit
     * @return com.cskaoyan.bean.wx.order.WxOrderListPo
     * @author changyong
     * @since 2022/07/20 11:36
     */
    @Override
    public WxOrderListPo list(Integer showType, Integer page, Integer limit) {
        MarketOrderExample marketOrderExample1 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria1 = marketOrderExample1.createCriteria();
        //获取当前登录的userId
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        if (principals == null) {
            subject.logout();
        }
        MarketUser primaryPrincipal = (MarketUser) principals.getPrimaryPrincipal();
        criteria1.andUserIdEqualTo(primaryPrincipal.getId());
        //利用工具类Constant，由showType得到相应状态码
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
        //先查询order表，得到orderList
        List<MarketOrder> orderList = marketOrderMapper.selectByExample(marketOrderExample1);
        //foreach循环 order赋值给orderOfList(orderOfList有成员变量isGroupin，order表中无该数据，不能直接使用orderList)
        List<OrderOfList> orderOfLists = new LinkedList<>();
        for (MarketOrder order : orderList) {
            //循环里面，由orderId查询OrderGoods表，得到OrderGoodsList
            MarketOrderGoodsExample marketOrderGoodsExample2 = new MarketOrderGoodsExample();
            MarketOrderGoodsExample.Criteria criteria2 = marketOrderGoodsExample2.createCriteria();
            //只有删除订单后，order的goods的deleted=1，所以下面不用加条件“没有被删除”
            criteria2.andOrderIdEqualTo(order.getId());
            //数据库中specifications是String字符串，此处需使用typehandler将其转换为String数组
            List<MarketOrderGoods> orderGoods = marketOrderGoodsMapper.selectByExample(marketOrderGoodsExample2);
            //第二个foreach循环 orderGoods赋值给GoodsOforder，
            // （GoodsOfOrder数据在OrderGoods中都有，也可不进行第二次foreach循环，直接使用OrderGoods）
            List<GoodsOfOrder> goodsOfOrderList = new LinkedList<>();
            for (MarketOrderGoods orderGood : orderGoods) {
                GoodsOfOrder goodsOfOrder = new GoodsOfOrder(orderGood.getId(), orderGood.getNumber(), orderGood.getGoodsName(), orderGood.getPicUrl(), orderGood.getPrice(), orderGood.getSpecifications());
                goodsOfOrderList.add(goodsOfOrder);
            }
            //由orderStatus查得handleOption，赋值给Order成员变量
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
                handleOption = Constant.handleOptionMap.get(order.getOrderStatus());
            }
            //生成orderOfList，并赋值
            OrderOfList orderOfList = new OrderOfList(order.getActualPrice(), order.getAftersaleStatus(), goodsOfOrderList, handleOption, order.getId(), false, order.getOrderSn(), Constant.orderStatusTextMap.get(order.getOrderStatus()));
            orderOfLists.add(orderOfList);
        }
        //第一个foreach循环结束，List<OrderOfList> 赋值给WxOrderListPo成员变量
        return new WxOrderListPo(total, Integer.valueOf((int) pages), limit.longValue(), page, orderOfLists);
    }

    /**
     * 由orderId返回订单详情
     *
     * @param id
     * @return com.cskaoyan.bean.wx.order.WxOrderDetailPo
     * @author changyong
     * @since 2022/07/20 11:36
     */
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

    /**
     * 用户申请退款
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/20 11:38
     */
    @Override
    public void refund(Integer id) {
        //获取order，再获取orderStatus，为201（即未发货状态），可以申请退款
        MarketOrder marketOrder = marketOrderMapper.selectByPrimaryKey(id);
        if ((short) 201 == marketOrder.getOrderStatus()) {
            marketOrder.setOrderStatus((short) 202);
            marketOrder.setUpdateTime(new Date());
            marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
        }

    }

    /**
     * 商品评论
     *
     * @param wxOrderCommentBo
     * @return void
     * @author changyong
     * @since 2022/07/20 11:38
     */
    @Override
    public void comment(WxOrderCommentBo wxOrderCommentBo) {
        //TODO order表中“comments”字段表示该订单中未评价商品数量，
        // 如果订单中还有未评价商品，则状态码仍为401或402，handleOption中comment为true

        //TODO 如果一个订单有多个品类商品，那就有多个“待评价”选项，评价其中一个之后，
        // order表comments改为1，则该订单的其他品类商品不可再评价，
        // 怎么破

        // orderGoodsId为orderGoods表id，根据其查询得到goodsId，orderId，进而得到userId
        MarketOrderGoods marketOrderGoods1 = marketOrderGoodsMapper.selectByPrimaryKey(wxOrderCommentBo.getOrderGoodsId());
        MarketOrder marketOrder1 = marketOrderMapper.selectByPrimaryKey(marketOrderGoods1.getOrderId());
        Short orderStatus = marketOrder1.getOrderStatus();
        //如果订单状态码为401,或402，则可以进行评价
        if ((short) 401 == orderStatus || (short) 402 == orderStatus) {
            MarketComment marketComment = new MarketComment();
            marketComment.setValueId(marketOrderGoods1.getGoodsId());
            marketComment.setUserId(marketOrder1.getUserId());
            //此处为商品评论，直接将type设为0
            marketComment.setType((byte) 0);
            marketComment.setContent(wxOrderCommentBo.getContent());
            //管理员尚未回复，设为空
            marketComment.setAdminContent("");
            marketComment.setHasPicture(wxOrderCommentBo.isHasPicture());
            marketComment.setPicUrls(wxOrderCommentBo.getPicUrls());
            marketComment.setStar(wxOrderCommentBo.getStar().shortValue());
            Date addTime = new Date();
            marketComment.setAddTime(addTime);
            marketComment.setUpdateTime(addTime);
            int affectedRows = marketCommentMapper.insertSelective(marketComment);
            if (affectedRows == 1) {
                //评价完成后将修改order表，orderGoods表
                //修改orderGoods表 '订单商品评论，如果是-1，则超期不能评价；如果是0，则可以评价；如果其他值，则是comment表里面的评论ID
                MarketOrderGoods marketOrderGoods2 = new MarketOrderGoods();
                marketOrderGoods2.setId(wxOrderCommentBo.getOrderGoodsId());
                marketOrderGoods2.setComment(marketComment.getId());
                marketOrderGoods2.setUpdateTime(new Date());
                marketOrderGoodsMapper.updateByPrimaryKeySelective(marketOrderGoods2);
                MarketOrder marketOrder2 = new MarketOrder();
                marketOrder2.setId(marketOrderGoods1.getOrderId());
                //order表中“comments”字段表示该订单中未评价商品数量，应该将comment-1
                marketOrder2.setComments((short) (marketOrder1.getOrderStatus()-1));
                marketOrder2.setUpdateTime(new Date());
                marketOrderMapper.updateByPrimaryKeySelective(marketOrder2);
            }
        }

    }

    /**
     * 评价前商品数据回显
     *
     * @param orderId
     * @param goodsId
     * @return com.cskaoyan.bean.common.MarketOrderGoods
     * @author changyong
     * @since 2022/07/20 11:40
     */
    @Override
    public MarketOrderGoods goods(Integer orderId, Integer goodsId) {
        MarketOrderGoodsExample marketOrderGoodsExample = new MarketOrderGoodsExample();
        MarketOrderGoodsExample.Criteria criteria = marketOrderGoodsExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId)
                .andGoodsIdEqualTo(goodsId);
        List<MarketOrderGoods> marketOrderGoods = marketOrderGoodsMapper.selectByExample(marketOrderGoodsExample);
        MarketOrderGoods marketOrderGoods1 = marketOrderGoods.get(0);
        return marketOrderGoods1;
    }

    /**
     * 逻辑删除订单
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/20 11:40
     */
    @Override
    public void delete(Integer id) {
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setId(id);
        marketOrder.setDeleted(true);
        marketOrder.setUpdateTime(new Date());
        //先获取该订单的状态，如果为 102: '用户取消', 103: '系统取消', 203: '已退款'，401：'用户收货'，402：'系统收货'，则可以删除订单
        Short orderStatus = marketOrderMapper.selectByPrimaryKey(id).getOrderStatus();
        if ((short) 102 == orderStatus || (short) 103 == orderStatus || (short) 203 == orderStatus|| (short) 401 == orderStatus|| (short) 402 == orderStatus) {
            marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
        }
    }

    /**
     * 用户确认收货
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/20 11:42
     */
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

    /**
     * 未付款订单取消订单
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/20 11:42
     */
    @Override
    public void cancel(Integer id) {
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setId(id);
        //TODO 系统取消（103）该怎么做呢
        marketOrder.setOrderStatus((short) 102);
        marketOrder.setUpdateTime(new Date());
        //先获取该订单的状态，如果为 101: '未付款',则可以取消订单
        Short orderStatus = marketOrderMapper.selectByPrimaryKey(id).getOrderStatus();
        if ((short) 101 == orderStatus) {
            marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
        }
    }

    /**
     * 提交订单
     *
     * @param wxOrderSubmitBo
     * @return com.cskaoyan.bean.wx.order.WxOrderSubmitPo
     * @author changyong
     * @since 2022/07/20 11:44
     */
    @Override
    public WxOrderSubmitPo submit(WxOrderSubmitBo wxOrderSubmitBo) {
        //TODO 等待wx/goods/detail,wx/cart/goodscount,wx/cart/fastadd,wx/cart/checkout
        // 需要修改购物车、商品库存、优惠券数量
        return null;
    }
}
