package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.MarketGoodsAttribute;
import com.cskaoyan.bean.common.MarketGoodsAttributeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketGoodsAttributeMapper {
    long countByExample(MarketGoodsAttributeExample example);

    int deleteByExample(MarketGoodsAttributeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketGoodsAttribute record);

    int insertSelective(MarketGoodsAttribute record);

    List<MarketGoodsAttribute> selectByExample(MarketGoodsAttributeExample example);

    List<MarketGoodsAttribute> selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketGoodsAttribute record, @Param("example") MarketGoodsAttributeExample example);

    int updateByExample(@Param("record") MarketGoodsAttribute record, @Param("example") MarketGoodsAttributeExample example);

    int updateByPrimaryKeySelective(MarketGoodsAttribute record);

    int updateByPrimaryKey(MarketGoodsAttribute record);
}