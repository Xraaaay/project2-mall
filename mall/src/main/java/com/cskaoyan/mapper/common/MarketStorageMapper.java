package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.MarketStorage;
import com.cskaoyan.bean.admin.system.MarketStorageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketStorageMapper {
    long countByExample(MarketStorageExample example);

    int deleteByExample(MarketStorageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketStorage record);

    int insertSelective(MarketStorage record);

    List<MarketStorage> selectByExample(MarketStorageExample example);

    MarketStorage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketStorage record, @Param("example") MarketStorageExample example);

    int updateByExample(@Param("record") MarketStorage record, @Param("example") MarketStorageExample example);

    int updateByPrimaryKeySelective(MarketStorage record);

    int updateByPrimaryKey(MarketStorage record);



}