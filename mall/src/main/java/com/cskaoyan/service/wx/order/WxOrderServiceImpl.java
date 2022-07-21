package com.cskaoyan.service.wx.order;

import com.cskaoyan.bean.admin.mallmanagement.po.HandleOption;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketChannel;
import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.order.*;
import com.cskaoyan.mapper.common.*;
import com.cskaoyan.util.Constant;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

    @Autowired
    MarketCartMapper marketCartMapper;

    @Autowired
    MarketCouponMapper marketCouponMapper;

    @Autowired
    MarketCouponUserMapper marketCouponUserMapper;

    @Autowired
    MarketAddressMapper marketAddressMapper;

    @Autowired
    MarketGoodsProductMapper marketGoodsProductMapper;

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
            criteria1.andOrderStatusBetween(orderStatus.shortValue(), (short) (orderStatus + 1));
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
                if (order.getComments() != 0) {
                    // order表中“comments”字段表示该订单中未评价商品数量，
                    // 如果订单中还有未评价商品，则状态码仍为401或402，handleOption中comment为true
                    //如果还有商品未评价
                    handleOption = Constant.handleOptionMap.get(order.getOrderStatus());
                } else if (order.getComments() == 0) {
                    //如果没有商品未评价
                    handleOption = Constant.handleOptionMap.get((short) 403);
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
            Date updateTime = new Date();
            marketOrder.setUpdateTime(updateTime);
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
                //order表中“comments”字段表示该订单中未评价商品数量，应该将comments-1
                marketOrder2.setComments((short) (marketOrder1.getComments() - 1));
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
        if ((short) 102 == orderStatus || (short) 103 == orderStatus || (short) 203 == orderStatus || (short) 401 == orderStatus || (short) 402 == orderStatus) {
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
        //等待wx/goods/detail,wx/cart/goodscount,wx/cart/fastadd,wx/cart/checkout
        //从购物车中取出商品，取出之后逻辑删除
        //如果cartId=0，表示是从购物车中选择之后提交，如果cartId!=0，表示是从商品详情页面直接提交，
        //已经判断库存是否足够，此处不赘述
        MarketCartExample marketCartExample = new MarketCartExample();
        MarketCartExample.Criteria criteria = marketCartExample.createCriteria();
        //获取当前登录的userId
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        if (principals == null) {
            subject.logout();
        }
        MarketUser primaryPrincipal = (MarketUser) principals.getPrimaryPrincipal();
        criteria.andUserIdEqualTo(primaryPrincipal.getId());
        if (wxOrderSubmitBo.getCartId() == 0) {
            //如果cartId=0，表示此时cart表中全部fastadd生成的数据都应该已经逻辑删除
            criteria.andCheckedEqualTo(true)
                    .andDeletedEqualTo(false);
        } else {
            //如果cartId!=0，fastadd增加一条数据到cart表，同时该商品加入到页面购物车中
            //如果cartId!=0，表示cartId为cart表主键id，也可根据主键查询得到数据
            criteria.andIdEqualTo(wxOrderSubmitBo.getCartId())
                    .andCheckedEqualTo(true)
                    .andDeletedEqualTo(false);
        }
        List<MarketCart> marketCarts = marketCartMapper.selectByExample(marketCartExample);

        //根据商品价格、数量、优惠券及运费，得出总价
        //'实付费用， = order_price - integral_price',
        BigDecimal actualPrice = new BigDecimal(0);
        //用户积分减免
        BigDecimal integralPrice = new BigDecimal(0);
        //'订单费用， = goods_price + freight_price - coupon_price',
        BigDecimal orderPrice = new BigDecimal(0);
        //商品总费用
        BigDecimal goodsPrice = new BigDecimal(0);
        //优惠券费用
        BigDecimal couponPrice = new BigDecimal(0);
        //配送费用
        BigDecimal freightPrice = new BigDecimal(0);

        //团购优惠价减免，数据库中运算不包含该项，order表中该字段没有默认值
        BigDecimal grouponPrice = new BigDecimal(0);
        // 先for循环得到商品价格之和
        for (int i = 0; i < marketCarts.size(); i++) {
            MarketCart marketCart = marketCarts.get(i);
            BigDecimal price = marketCart.getPrice();
            Short number = marketCart.getNumber();
            goodsPrice = goodsPrice.add(price.multiply(new BigDecimal(number)));
        }
        //得到couponPrice优惠券减免，根据couponId得到discount值
        if (wxOrderSubmitBo.getCouponId() != 0) {
            couponPrice = marketCouponMapper.selectByPrimaryKey(wxOrderSubmitBo.getCouponId()).getDiscount();
        }
        orderPrice = goodsPrice.add(freightPrice).subtract(couponPrice);
        //如果orderPrice小于88元，加10元运费
        if (orderPrice.compareTo(new BigDecimal(88)) == -1) {
            freightPrice = freightPrice.add(new BigDecimal(10));
            orderPrice = goodsPrice.add(freightPrice).subtract(couponPrice);
        }
        actualPrice = orderPrice.subtract(integralPrice);

        //根据addressId获取地址
        MarketAddress marketAddress = marketAddressMapper.selectByPrimaryKey(wxOrderSubmitBo.getAddressId());
        StringBuffer stringBuffer1 = new StringBuffer(marketAddress.getProvince());
        String address = stringBuffer1.append(marketAddress.getCity())
                .append(marketAddress.getCounty())
                .append(marketAddress
                        .getAddressDetail())
                .toString();
        //生成orderSn：年月日+6位随机码
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        StringBuffer stringBuffer = new StringBuffer(format);
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10);
            stringBuffer.append(num);
        }
        String orderSn = stringBuffer.toString();
        //设置数据
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setUserId(primaryPrincipal.getId());
        marketOrder.setOrderSn(orderSn);
        marketOrder.setOrderStatus((short) 101);
        marketOrder.setAftersaleStatus((short) 0);
        marketOrder.setConsignee(marketAddress.getName());
        marketOrder.setMobile(marketAddress.getTel());
        marketOrder.setAddress(address);
        marketOrder.setMessage("");
        marketOrder.setGoodsPrice(goodsPrice);
        marketOrder.setFreightPrice(freightPrice);
        marketOrder.setCouponPrice(couponPrice);
        marketOrder.setIntegralPrice(integralPrice);
        marketOrder.setOrderPrice(orderPrice);
        marketOrder.setActualPrice(actualPrice);
        marketOrder.setGrouponPrice(grouponPrice);
        marketOrder.setComments((short) marketCarts.size());
        Date addTime = new Date();
        marketOrder.setAddTime(addTime);
        marketOrder.setUpdateTime(addTime);
        marketOrder.setDeleted(false);
        //插入数据
        int affectedRows1 = marketOrderMapper.insertSelective(marketOrder);

        // 插入order_goods表
        for (int i = 0; i < marketCarts.size(); i++) {
            MarketCart marketCart = marketCarts.get(i);
            //赋值
            MarketOrderGoods marketOrderGoods = new MarketOrderGoods();
            //订单insert订单表时，SQL语句设置了主键返回
            marketOrderGoods.setOrderId(marketOrder.getId());
            marketOrderGoods.setGoodsId(marketCart.getGoodsId());
            marketOrderGoods.setGoodsName(marketCart.getGoodsName());
            marketOrderGoods.setGoodsSn(marketCart.getGoodsSn());
            marketOrderGoods.setProductId(marketCart.getProductId());
            marketOrderGoods.setNumber(marketCart.getNumber());
            marketOrderGoods.setPrice(marketCart.getPrice());
            marketOrderGoods.setSpecifications(marketCart.getSpecifications());
            marketOrderGoods.setPicUrl(marketCart.getPicUrl());
            //'订单商品评论，如果是-1，则超期不能评价；如果是0，则可以评价；如果其他值，则是comment表里面的评论ID。
            marketOrderGoods.setComment(0);
            Date addTime1 = new Date();
            marketOrderGoods.setAddTime(addTime1);
            marketOrderGoods.setUpdateTime(addTime1);
            marketOrderGoods.setDeleted(false);
            //插入数据
            marketOrderGoodsMapper.insertSelective(marketOrderGoods);

        }

        if (affectedRows1 == 1) {
            //  需要修改购物车、商品库存、优惠券数量
            //  生成订单后，所使用的优惠券状态改为1（已使用），购物车商品逻辑删除，商品库存减少，
            //将cart表中已下单商品逻辑删除
            for (int i = 0; i < marketCarts.size(); i++) {
                MarketCart marketCart = marketCarts.get(i);
                marketCart.setDeleted(true);
                marketCart.setUpdateTime(new Date());
                marketCartMapper.updateByPrimaryKeySelective(marketCart);
            }
            //所使用的优惠券状态改为1（已使用）
            //TODO 如果后台没有对个人用户能够领取同一张优惠券的数量，该用户持有多张同一优惠券。
            // 那应该是把哪一张优惠券更改状态
            MarketCouponUser marketCouponUser = new MarketCouponUser();
            marketCouponUser.setId(wxOrderSubmitBo.getUserCouponId());
            //'使用状态, 如果是0则未使用；如果是1则已使用；如果是2则已过期；如果是3则已经下架；
            marketCouponUser.setStatus((short) 1);
            marketCouponUser.setUpdateTime(new Date());
            marketCouponUserMapper.updateByPrimaryKeySelective(marketCouponUser);
            //商品库存减少
            //根据market_cart表中“product_id”修改market_goods_product表中数量number
            for (int i = 0; i < marketCarts.size(); i++) {
                MarketCart marketCart = marketCarts.get(i);
                MarketGoodsProduct marketGoodsProduct = new MarketGoodsProduct();
                marketGoodsProduct.setId(marketCart.getProductId());
                //先获取库存，再减(之前已经对库存是否足够做了判断，此处不赘述)
                Integer number = marketGoodsProductMapper.selectByPrimaryKey(marketCart.getProductId()).getNumber();
                marketGoodsProduct.setNumber(number - marketCart.getNumber());
                marketGoodsProduct.setUpdateTime(new Date());
                marketGoodsProductMapper.updateByPrimaryKeySelective(marketGoodsProduct);
            }
        }
        return new WxOrderSubmitPo(0, marketOrder.getId());
    }

    /**
     * 付款
     *
     * @param id
     * @return void
     * @author changyong
     * @since 2022/07/21 17:33
     */
    @Override
    public void prepay(Integer id) {
        //生成payId：年月日+6位随机码
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        StringBuffer stringBuffer = new StringBuffer(format);
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10);
            stringBuffer.append(num);
        }
        String payId = stringBuffer.toString();
        //赋值
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setId(id);
        marketOrder.setPayId(payId);
        marketOrder.setOrderStatus((short)201);
        Date payTime = new Date();
        marketOrder.setPayTime(payTime);
        marketOrder.setUpdateTime(payTime);
        //update
        marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
    }
}
