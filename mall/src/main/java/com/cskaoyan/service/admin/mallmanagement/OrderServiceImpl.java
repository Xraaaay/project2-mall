package com.cskaoyan.service.admin.mallmanagement;

import com.cskaoyan.bean.admin.comment.bo.CommentBo;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderDetailPo;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderListPo;
import com.cskaoyan.bean.admin.mallmanagement.bo.MarketOrderListBo;
import com.cskaoyan.bean.common.*;
import com.cskaoyan.mapper.common.MarketCommentMapper;
import com.cskaoyan.mapper.common.MarketOrderGoodsMapper;
import com.cskaoyan.mapper.common.MarketOrderMapper;
import com.cskaoyan.mapper.common.MarketUserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    MarketOrderMapper marketOrderMapper;

    @Autowired
    MarketUserMapper marketUserMapper;

    @Autowired
    MarketOrderGoodsMapper marketOrderGoodsMapper;

    @Autowired
    MarketCommentMapper marketCommentMapper;

    /**
     * 返回订单列表并根据条件查询
     *
     * @param marketOrderListBo
     * @return com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderListPo
     * @author changyong
     * @since 2022/07/17 18:17
     */
    @Override
    public MarketOrderListPo list(MarketOrderListBo marketOrderListBo) {
        //创建marketOrderExample
        MarketOrderExample marketOrderExample1 = new MarketOrderExample();
        MarketOrderExample.Criteria criteria1 = marketOrderExample1.createCriteria();
        marketOrderExample1.setOrderByClause(marketOrderListBo.getSort() + "  " + marketOrderListBo.getOrder());
        //赋值
        String[] timeArray = marketOrderListBo.getTimeArray();
        if (timeArray.length != 0 && timeArray[0] != null && timeArray[1] != null) {
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
            if (s != null) {
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
        criteria1.andDeletedEqualTo(false);
        //分页之后查询
        PageHelper.startPage(marketOrderListBo.getPage(), marketOrderListBo.getLimit());
        List<MarketOrder> marketBrands = marketOrderMapper.selectByExample(marketOrderExample1);
        //获取total及pages
        long total = marketOrderMapper.countByExample(marketOrderExample1);
        Integer pages = Math.toIntExact((total / marketOrderListBo.getLimit()) + 1);
        return new MarketOrderListPo(total, pages, new Long(marketOrderListBo.getLimit()), marketOrderListBo.getPage(), marketBrands);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return com.cskaoyan.bean.admin.mallmanagement.po.MarketOrderDetailPo
     * @author changyong
     * @since 2022/07/17 18:16
     */
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

    /**
     * 订单发货
     *
     * @param orderId
     * @param shipChannel
     * @param shipSn
     * @return void
     * @author changyong
     * @since 2022/07/17 18:14
     */
    @Override
    public void ship(Integer orderId, String shipChannel, String shipSn) {
        //获取order，再获取orderStatus，为201（即未发货状态），可以发货
        MarketOrder marketOrder = marketOrderMapper.selectByPrimaryKey(orderId);
        if((short)201==marketOrder.getOrderStatus()){
            //赋值
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

    /**
     * 处理用户申请退款订单
     *
     * @param id
     * @param refundMoney
     * @return void
     * @author changyong
     * @since 2022/07/17 18:14
     */
    @Override
    public void refund(Integer id, Double refundMoney) {
        //获取order，再获取orderStatus，为202（即申请退款状态），可以退款
        MarketOrder marketOrder = marketOrderMapper.selectByPrimaryKey(id);
        if((short)202==marketOrder.getOrderStatus()){
            marketOrder.setOrderStatus((short) 203);
            marketOrder.setRefundAmount(new BigDecimal(Double.valueOf(refundMoney)));
            marketOrder.setRefundType("微信退款接口");
            Date refundTime = new Date();
            marketOrder.setRefundTime(refundTime);
            marketOrder.setUpdateTime(refundTime);
            marketOrder.setEndTime(refundTime);
            //update
            marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
        }

    }

    /**
     * 删除订单
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/17 20:57
     */
    @Override
    public void delete(Integer id) {
        //获取order，再获取orderStatus，为102（即用户取消状态），103（即系统取消状态），401（即用户取消状态），402（即系统取消状态），可以删除
        MarketOrder marketOrder = marketOrderMapper.selectByPrimaryKey(id);
        if((short)102==marketOrder.getOrderStatus()||(short)103==marketOrder.getOrderStatus()||(short)203==marketOrder.getOrderStatus()||(short)401==marketOrder.getOrderStatus()||(short)402==marketOrder.getOrderStatus()){
            //创建marketOrder并赋值
            marketOrder.setDeleted(true);
            marketOrder.setUpdateTime(new Date());
            //update
            marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
        }

    }
    /**
    * @description 回复评论
    * @author pqk
    * @date 2022/07/20 0:28
     */
    @Override
    public void reply(CommentBo commentBo) {
        MarketComment marketComment = new MarketComment();
        marketComment.setAdminContent(commentBo.getContent());
        marketComment.setDeleted(true);

        MarketCommentExample marketCommentExample = new MarketCommentExample();
        MarketCommentExample.Criteria criteria = marketCommentExample.createCriteria();
        criteria.andIdEqualTo(commentBo.getCommentId());
        marketCommentMapper.updateByExampleSelective(marketComment,marketCommentExample);
    }
}
