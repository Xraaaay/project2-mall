package com.cskaoyan.mapper;

import com.cskaoyan.bean.MarketRolePermission;
import com.cskaoyan.bean.MarketRolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketRolePermissionMapper {
    long countByExample(MarketRolePermissionExample example);

    int deleteByExample(MarketRolePermissionExample example);

    int deleteByPrimaryKey(Integer primaryKey);

    int insert(MarketRolePermission record);

    int insertSelective(MarketRolePermission record);

    List<MarketRolePermission> selectByExample(MarketRolePermissionExample example);

    MarketRolePermission selectByPrimaryKey(Integer primaryKey);

    int updateByExampleSelective(@Param("record") MarketRolePermission record, @Param("example") MarketRolePermissionExample example);

    int updateByExample(@Param("record") MarketRolePermission record, @Param("example") MarketRolePermissionExample example);

    int updateByPrimaryKeySelective(MarketRolePermission record);

    int updateByPrimaryKey(MarketRolePermission record);
}