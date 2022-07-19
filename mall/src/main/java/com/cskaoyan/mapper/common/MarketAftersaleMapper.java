package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.MarketAftersale;
import com.cskaoyan.bean.common.MarketAftersaleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketAftersaleMapper {
    long countByExample(MarketAftersaleExample example);

    int deleteByExample(MarketAftersaleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketAftersale record);

    int insertSelective(MarketAftersale record);

    List<MarketAftersale> selectByExample(MarketAftersaleExample example);

    MarketAftersale selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketAftersale record, @Param("example") MarketAftersaleExample example);

    int updateByExample(@Param("record") MarketAftersale record, @Param("example") MarketAftersaleExample example);

    int updateByPrimaryKeySelective(MarketAftersale record);

    int updateByPrimaryKey(MarketAftersale record);
}