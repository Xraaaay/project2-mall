package com.cskaoyan.mapper;

import com.cskaoyan.bean.MarketSearchHistory;
import com.cskaoyan.bean.MarketSearchHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketSearchHistoryMapper {
    long countByExample(MarketSearchHistoryExample example);

    int deleteByExample(MarketSearchHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketSearchHistory record);

    int insertSelective(MarketSearchHistory record);

    List<MarketSearchHistory> selectByExample(MarketSearchHistoryExample example);

    MarketSearchHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketSearchHistory record, @Param("example") MarketSearchHistoryExample example);

    int updateByExample(@Param("record") MarketSearchHistory record, @Param("example") MarketSearchHistoryExample example);

    int updateByPrimaryKeySelective(MarketSearchHistory record);

    int updateByPrimaryKey(MarketSearchHistory record);
}