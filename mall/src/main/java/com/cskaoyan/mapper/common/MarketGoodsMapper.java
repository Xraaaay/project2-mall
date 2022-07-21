package com.cskaoyan.mapper.common;

import com.cskaoyan.bean.common.GoodsListVoExample;
import com.cskaoyan.bean.common.MarketGoods;
import com.cskaoyan.bean.common.MarketGoodsExample;
import com.cskaoyan.bean.admin.goods.vo.MarketGoodsVo;
import com.cskaoyan.bean.wx.goods.GoodsListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketGoodsMapper {
    long countByExample(MarketGoodsExample example);

    int deleteByExample(MarketGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MarketGoods record);

    int insertSelective(MarketGoods record);

    int insertSelectiveVo(MarketGoodsVo record);

    List<MarketGoods> selectByExampleWithBLOBs(MarketGoodsExample example);

    List<MarketGoods> selectByExample(MarketGoodsExample example);

    List<MarketGoodsVo> selectByExampleVo(MarketGoodsExample example);

    // List<GoodsListVo> selectByExampleWxVo(GoodsListVoExample example);

    MarketGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MarketGoods record, @Param("example") MarketGoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") MarketGoods record, @Param("example") MarketGoodsExample example);

    int updateByExample(@Param("record") MarketGoods record, @Param("example") MarketGoodsExample example);

    int updateByPrimaryKeySelective(MarketGoods record);

    int updateByPrimaryKeySelectiveVo(MarketGoodsVo record);

    int updateByPrimaryKeyWithBLOBs(MarketGoods record);

    int updateByPrimaryKey(MarketGoods record);
}