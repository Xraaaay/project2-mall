package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.MarketNoticeAdmin;
import com.cskaoyan.bean.common.MarketNoticeAdminExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketNoticeAdminMapper {
    long countByExample(MarketNoticeAdminExample example);

    int deleteByExample(MarketNoticeAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketNoticeAdmin record);

    int insertSelective(MarketNoticeAdmin record);

    List<MarketNoticeAdmin> selectByExample(MarketNoticeAdminExample example);

    MarketNoticeAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketNoticeAdmin record, @Param("example") MarketNoticeAdminExample example);

    int updateByExample(@Param("record") MarketNoticeAdmin record, @Param("example") MarketNoticeAdminExample example);

    int updateByPrimaryKeySelective(MarketNoticeAdmin record);

    int updateByPrimaryKey(MarketNoticeAdmin record);
}