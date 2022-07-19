package com.cskaoyan.mapper.system;

import com.cskaoyan.bean.admin.system.MarketRole;
import com.cskaoyan.bean.admin.system.MarketRoleExample;
import com.cskaoyan.bean.admin.system.MarketRoleOptionsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketRoleMapper {
    long countByExample(MarketRoleExample example);

    int deleteByExample(MarketRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketRole record);

    int insertSelective(MarketRole record);

    List<MarketRole> selectByExample(MarketRoleExample example);

    List<MarketRoleOptionsVo> selectOptionsByExample(MarketRoleExample example);

    MarketRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketRole record, @Param("example") MarketRoleExample example);

    int updateByExample(@Param("record") MarketRole record, @Param("example") MarketRoleExample example);

    int updateByPrimaryKeySelective(MarketRole record);

    int updateByPrimaryKey(MarketRole record);
}