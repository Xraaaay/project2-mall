package com.cskaoyan.service.goods;

import com.cskaoyan.bean.MarketGoods;
import com.cskaoyan.bean.MarketGoodsExample;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.mapper.MarketGoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品信息模块
 * @author pqk
 * @since 2022/07/16 21:14
 */
@Component
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    MarketGoodsMapper marketGoodsMapper;

    @Override
    public CommonData list(Integer page, Integer limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        MarketGoodsExample example = new MarketGoodsExample();
        MarketGoodsExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(sort + " " + order);
        //根据条件查询到 商品列表集合
        List<MarketGoods> marketGoods = marketGoodsMapper.selectByExample(example);
        //创建2级VO
        CommonData<MarketGoods> marketGoodsCommonData = new CommonData<>();

        PageInfo<MarketGoods> marketGoodsPageInfo = new PageInfo<>(marketGoods);

        marketGoodsCommonData.setTotal(marketGoodsPageInfo.getSize());
        marketGoodsCommonData.setLimit(marketGoodsPageInfo.getPageSize());
        marketGoodsCommonData.setList(marketGoods);
        marketGoodsCommonData.setPage(marketGoodsPageInfo.getPageNum());
        marketGoodsCommonData.setPages(marketGoodsPageInfo.getPages());

        return marketGoodsCommonData;
    }

    @Override
    public void delete(Integer id) {
        marketGoodsMapper.deleteByPrimaryKey(id);
        return;
    }
}
