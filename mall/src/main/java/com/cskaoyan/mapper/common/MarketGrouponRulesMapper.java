package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.MarketGrouponRules;
import com.cskaoyan.bean.common.MarketGrouponRulesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketGrouponRulesMapper {
    long countByExample(MarketGrouponRulesExample example);

    int deleteByExample(MarketGrouponRulesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketGrouponRules record);

    int insertSelective(MarketGrouponRules record);

    List<MarketGrouponRules> selectByExample(MarketGrouponRulesExample example);

    MarketGrouponRules selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketGrouponRules record, @Param("example") MarketGrouponRulesExample example);

    int updateByExample(@Param("record") MarketGrouponRules record, @Param("example") MarketGrouponRulesExample example);

    int updateByPrimaryKeySelective(MarketGrouponRules record);

    int updateByPrimaryKey(MarketGrouponRules record);
}