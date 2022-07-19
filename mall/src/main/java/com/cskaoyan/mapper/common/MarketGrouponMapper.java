package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.MarketGroupon;
import com.cskaoyan.bean.common.MarketGrouponExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketGrouponMapper {
    long countByExample(MarketGrouponExample example);

    int deleteByExample(MarketGrouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketGroupon record);

    int insertSelective(MarketGroupon record);

    List<MarketGroupon> selectByExample(MarketGrouponExample example);

    MarketGroupon selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketGroupon record, @Param("example") MarketGrouponExample example);

    int updateByExample(@Param("record") MarketGroupon record, @Param("example") MarketGrouponExample example);

    int updateByPrimaryKeySelective(MarketGroupon record);

    int updateByPrimaryKey(MarketGroupon record);
}