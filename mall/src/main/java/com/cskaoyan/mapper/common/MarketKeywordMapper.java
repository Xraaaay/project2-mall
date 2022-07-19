package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.MarketKeyword;
import com.cskaoyan.bean.common.MarketKeywordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketKeywordMapper {
    long countByExample(MarketKeywordExample example);

    int deleteByExample(MarketKeywordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketKeyword record);

    int insertSelective(MarketKeyword record);

    List<MarketKeyword> selectByExample(MarketKeywordExample example);

    MarketKeyword selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketKeyword record, @Param("example") MarketKeywordExample example);

    int updateByExample(@Param("record") MarketKeyword record, @Param("example") MarketKeywordExample example);

    int updateByPrimaryKeySelective(MarketKeyword record);

    int updateByPrimaryKey(MarketKeyword record);
}