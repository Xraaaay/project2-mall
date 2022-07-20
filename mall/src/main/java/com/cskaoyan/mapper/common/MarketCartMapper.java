package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.cart.CartTotalEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketCartMapper {
    long countByExample(MarketCartExample example);

    int deleteByExample(MarketCartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketCart record);

    int insertSelective(MarketCart record);

    List<MarketCart> selectByExample(MarketCartExample example);

    MarketCart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketCart record, @Param("example") MarketCartExample example);

    int updateByExample(@Param("record") MarketCart record, @Param("example") MarketCartExample example);

    int updateByPrimaryKeySelective(MarketCart record);

    int updateByPrimaryKey(MarketCart record);

    CartTotalEntity selectCartTotal();

    Integer selectGoodscount(Integer userId);


    // void insertCart(@Param("pro") MarketGoodsProduct marketGoodsProduct, @Param("goods") MarketGoods marketGoods,@Param("user") Integer id);
}