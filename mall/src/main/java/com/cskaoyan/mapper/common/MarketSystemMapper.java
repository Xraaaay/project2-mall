package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.MarketSystem;
import com.cskaoyan.bean.common.MarketSystemExample;
import com.cskaoyan.bean.admin.marketconfig.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketSystemMapper {
    long countByExample(MarketSystemExample example);

    int deleteByExample(MarketSystemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketSystem record);

    int insertSelective(MarketSystem record);

    List<MarketSystem> selectByExample(MarketSystemExample example);

    MarketSystem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketSystem record, @Param("example") MarketSystemExample example);

    int updateByExample(@Param("record") MarketSystem record, @Param("example") MarketSystemExample example);

    int updateByPrimaryKeySelective(MarketSystem record);

    int updateByPrimaryKey(MarketSystem record);

    List<MarketSystemVO> selectLongitudeConfig();


    void UpdateLongitudeConfig(@Param("k") String key, @Param("v") String value);

    List<MarketExpreessVO> selectExpressConfig();

    void UpdateExpressConfig(@Param("k") String key, @Param("v") String value);

    void UpdateOrderConfig(@Param("k") String key, @Param("v") String value);

    List<MarketOrderVO> selectOrderConfig();


    List<MarketWxVO> selectWxConfig();


    void UpdateWxConfig1(@Param("k") String key, @Param("v") String value);

    List<StatRowsVO> selectStatUser();

    List<StatOrderRowsVO> selectStatOrder();

    List<StatGoodsRowsVO> selectStatGoods();
}


