package com.cskaoyan.mapper;

import com.cskaoyan.bean.MarketRegion;
import com.cskaoyan.bean.MarketRegionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketRegionMapper {
    long countByExample(MarketRegionExample example);

    int deleteByExample(MarketRegionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketRegion record);

    int insertSelective(MarketRegion record);

    List<MarketRegion> selectByExample(MarketRegionExample example);

    MarketRegion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketRegion record, @Param("example") MarketRegionExample example);

    int updateByExample(@Param("record") MarketRegion record, @Param("example") MarketRegionExample example);

    int updateByPrimaryKeySelective(MarketRegion record);

    int updateByPrimaryKey(MarketRegion record);
}