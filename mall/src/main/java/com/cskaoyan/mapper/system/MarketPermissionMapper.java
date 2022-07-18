package com.cskaoyan.mapper.system;

import com.cskaoyan.bean.system.MarketPermission;
import com.cskaoyan.bean.system.MarketPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketPermissionMapper {
    long countByExample(MarketPermissionExample example);

    int deleteByExample(MarketPermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketPermission record);

    int insertSelective(MarketPermission record);

    List<MarketPermission> selectByExample(MarketPermissionExample example);

    List<String > selectPermissionsByExample(MarketPermissionExample example);

    MarketPermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketPermission record, @Param("example") MarketPermissionExample example);

    int updateByExample(@Param("record") MarketPermission record, @Param("example") MarketPermissionExample example);

    int updateByPrimaryKeySelective(MarketPermission record);

    int updateByPrimaryKey(MarketPermission record);
}