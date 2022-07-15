package com.cskaoyan.mapper;

import com.cskaoyan.bean.MarketGoodsSpecification;
import com.cskaoyan.bean.MarketGoodsSpecificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketGoodsSpecificationMapper {
    long countByExample(MarketGoodsSpecificationExample example);

    int deleteByExample(MarketGoodsSpecificationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketGoodsSpecification record);

    int insertSelective(MarketGoodsSpecification record);

    List<MarketGoodsSpecification> selectByExample(MarketGoodsSpecificationExample example);

    MarketGoodsSpecification selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketGoodsSpecification record, @Param("example") MarketGoodsSpecificationExample example);

    int updateByExample(@Param("record") MarketGoodsSpecification record, @Param("example") MarketGoodsSpecificationExample example);

    int updateByPrimaryKeySelective(MarketGoodsSpecification record);

    int updateByPrimaryKey(MarketGoodsSpecification record);
}