package com.cskaoyan.service.wx.aftersale;

import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.aftersale.WxAftersaleDetailPo;
import com.cskaoyan.bean.wx.aftersale.WxAftersaleSubmitBo;
import com.cskaoyan.mapper.common.MarketAftersaleMapper;
import com.cskaoyan.mapper.common.MarketOrderGoodsMapper;
import com.cskaoyan.mapper.common.MarketOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author changyong
 * @since 2022/07/20 20:28
 */
@Transactional
@Component
public class AftersaleServiceImpl implements AftersaleService {

    @Autowired
    MarketAftersaleMapper marketAftersaleMapper;

    @Autowired
    MarketOrderMapper marketOrderMapper;

    @Autowired
    MarketOrderGoodsMapper marketOrderGoodsMapper;

    /**
     * 点击“申请售后”，将售后信息提交给后台
     *
     * @param wxAftersaleSubmitBo
     * @return void
     * @author changyong
     * @since 2022/07/20 22:36
     */
    @Override
    public void submit(WxAftersaleSubmitBo wxAftersaleSubmitBo) {
        MarketAftersale marketAftersale = new MarketAftersale();
        marketAftersale.setOrderId(wxAftersaleSubmitBo.getOrderId());
        //通过orderId查询order表得到userId
        Integer userId = marketOrderMapper.selectByPrimaryKey(wxAftersaleSubmitBo.getOrderId()).getUserId();
        marketAftersale.setUserId(userId);
        marketAftersale.setType(wxAftersaleSubmitBo.getType());
        marketAftersale.setReason(wxAftersaleSubmitBo.getReason());
        marketAftersale.setAmount(wxAftersaleSubmitBo.getAmount());
        marketAftersale.setPictures(wxAftersaleSubmitBo.getPictures());
        //生成aftersaleSn：年月日+6位随机码
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        StringBuffer stringBuffer = new StringBuffer(format);
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10);
            stringBuffer.append(num);
        }
        String aftersaleSn = stringBuffer.toString();
        marketAftersale.setAftersaleSn(aftersaleSn);
        //用户在客户端填写了退款说明，数据库中也含有comment字段，但POST请求的Json字符串中无该变量
        //marketAftersale.setComment("");
        //'售后状态，0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消'
        marketAftersale.setStatus((short) 1);
        Date addTime = new Date();
        marketAftersale.setAddTime(addTime);
        marketAftersale.setUpdateTime(addTime);
        marketAftersale.setDeleted(false);
        marketAftersaleMapper.insertSelective(marketAftersale);
        //修改order表中该订单aftersaleStatus为1；
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setId(userId);
        marketOrder.setUpdateTime(new Date());
        marketOrder.setAftersaleStatus((short)1);
        marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
    }

    /**
     * 提交申请售后信息后，再次点击“申请售后”，返回售后信息详情
     *
     * @param orderId
     * @return com.cskaoyan.bean.wx.aftersale.WxAftersaleDetailPo
     * @author changyong
     * @since 2022/07/20 22:37
     */
    @Override
    public WxAftersaleDetailPo detail(Integer orderId) {
        //TODO 为什么参考中 已申请售后的订单再点击“申请售后”，发送wx/aftersale/detail请求，显示售后详情，
        // 但我的小程序中 已申请售后的订单再点击“申请售后”，发送wx/order/detail请求，要我再次填写申请售后
        //先根据orderId查询aftersale表得到对象
        MarketAftersaleExample marketAftersaleExample = new MarketAftersaleExample();
        MarketAftersaleExample.Criteria criteria = marketAftersaleExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId)
                .andDeletedEqualTo(false);
        //该系统是否存在一个订单多个aftersale的情况呢？如果有，那只凭借一个orderId不能得到相应的aftersale。所以我认为没有
        List<MarketAftersale> marketAftersales = marketAftersaleMapper.selectByExample(marketAftersaleExample);
        MarketAftersale marketAftersale = marketAftersales.get(0);
        //再根据orderId修改order表aftersaleStatus=1，再返回order对象
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setId(orderId);
        marketOrder.setAftersaleStatus((short) 1);
        marketOrder.setUpdateTime(new Date());
        marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
        MarketOrder marketOrder1 = marketOrderMapper.selectByPrimaryKey(orderId);
        //后根据orderId查询orderGoods表得到orderGoods对象
        MarketOrderGoodsExample marketOrderGoodsExample = new MarketOrderGoodsExample();
        MarketOrderGoodsExample.Criteria criteria1 = marketOrderGoodsExample.createCriteria();
        criteria1.andOrderIdEqualTo(orderId)
                .andDeletedEqualTo(false);
        List<MarketOrderGoods> marketOrderGoods = marketOrderGoodsMapper.selectByExample(marketOrderGoodsExample);
        return new WxAftersaleDetailPo(marketAftersale, marketOrder1, marketOrderGoods);
    }
}
