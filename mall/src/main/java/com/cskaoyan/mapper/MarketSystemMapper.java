package com.cskaoyan.mapper;

import com.cskaoyan.bean.MarketSystem;
import com.cskaoyan.bean.MarketSystemExample;
import com.cskaoyan.bean.marketConfig.MarketExpreessVO;
import com.cskaoyan.bean.marketConfig.MarketOrderVO;
import com.cskaoyan.bean.marketConfig.MarketSystemVO;
import com.cskaoyan.bean.marketConfig.MarketWxVO;
import org.apache.ibatis.annotations.Param;

import java.security.Key;
import java.util.List;
import java.util.Map;

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



    void UpdateLongitudeConfig(@Param("k") String key, @Param("v")String value);

    List<MarketExpreessVO> selectExpressConfig();

    void UpdateExpressConfig(@Param("k") String key,@Param("v") String value);

    void UpdateOrderConfig(@Param("k")  String key,@Param("v") String value);

    List<MarketOrderVO> selectOrderConfig();


    List<MarketWxVO> selectWxConfig();


    void UpdateWxConfig1(@Param("k") String key,@Param("v") String value);
}