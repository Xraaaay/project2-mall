package com.cskaoyan.mapper.system;

import com.cskaoyan.bean.admin.system.MarketAdmin;
import com.cskaoyan.bean.admin.system.MarketAdminExample;
import com.cskaoyan.bean.admin.system.MarketAdminListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketAdminMapper {
    long countByExample(MarketAdminExample example);

    int deleteByExample(MarketAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketAdmin record);

    int insertSelective(MarketAdmin record);

    List<MarketAdmin> selectByExample(MarketAdminExample example);

    List<MarketAdminListVo> selectListByExample(MarketAdminExample example);

    MarketAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketAdmin record, @Param("example") MarketAdminExample example);

    int updateByExample(@Param("record") MarketAdmin record, @Param("example") MarketAdminExample example);

    int updateByPrimaryKeySelective(MarketAdmin record);

    int updateByPrimaryKey(MarketAdmin record);
}