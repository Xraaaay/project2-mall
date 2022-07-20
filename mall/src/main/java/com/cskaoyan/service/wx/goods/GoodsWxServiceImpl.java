package com.cskaoyan.service.wx.goods;


import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.goods.CategoryWxVo;
import com.cskaoyan.bean.wx.goods.GoodsListVo;
import com.cskaoyan.bean.wx.goods.ListWxBo;
import com.cskaoyan.bean.wx.goods.PageInfoDataVo;
import com.cskaoyan.mapper.common.MarketCategoryMapper;
import com.cskaoyan.mapper.common.MarketGoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/19 20:14
 */
@Component
public class GoodsWxServiceImpl implements GoodsWxService {
    @Autowired
    MarketCategoryMapper marketCategoryMapper;
    @Autowired
    MarketGoodsMapper marketGoodsMapper;

    @Override
    public CategoryWxVo category(String id) {
        int cid  = Integer.parseInt(id);
        MarketCategory currentCategory = marketCategoryMapper.selectByPrimaryKey(cid);

        Integer pid = currentCategory.getPid();

        MarketCategoryExample marketCategoryExample = new MarketCategoryExample();
        MarketCategoryExample.Criteria criteria = marketCategoryExample.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MarketCategory> brotherCategory = marketCategoryMapper.selectByExample(marketCategoryExample);

        MarketCategory parentCategory = marketCategoryMapper.selectByPrimaryKey(pid);

        CategoryWxVo categoryWxVo = new CategoryWxVo(currentCategory, parentCategory, brotherCategory);

        return categoryWxVo;
    }

    @Override
    public PageInfoDataVo list(ListWxBo listWxBo) {
        Integer categoryId = listWxBo.getCategoryId();
        Integer limit = listWxBo.getLimit();
        Integer page = listWxBo.getPage();

        PageHelper.startPage(page,limit);

        //三级类目  注释是备选方法 封装json数据可以但是懒得匹配映射
        // GoodsListVoExample goodsListVoExample = new GoodsListVoExample();
        // GoodsListVoExample.Criteria criteria = goodsListVoExample.createCriteria();
        // criteria.andCategoryIdEqualTo(categoryId);
        // List<GoodsListVo> list = marketGoodsMapper.selectByExample(goodsListVoExample);

        //根据类目id找到属于这个品类下商品
        MarketGoodsExample marketGoodsExample = new MarketGoodsExample();
        MarketGoodsExample.Criteria criteria = marketGoodsExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<MarketGoods> list = marketGoodsMapper.selectByExample(marketGoodsExample);


        //二级类目
        MarketCategoryExample marketCategoryExample = new MarketCategoryExample();
        MarketCategoryExample.Criteria marketCategoryExampleCriteria = marketCategoryExample.createCriteria();
        marketCategoryExampleCriteria.andLevelEqualTo("L2");
        List<MarketCategory> filterCategoryList = marketCategoryMapper.selectByExample(marketCategoryExample);


        PageInfo<MarketGoods> PageInfo = new PageInfo<>(list);

        //封装
        PageInfoDataVo pageInfoDataVo = new PageInfoDataVo(PageInfo.getTotal(),PageInfo.getPages(),PageInfo.getSize(),
                PageInfo.getPageNum(),list,filterCategoryList);

        return pageInfoDataVo;
    }

    @Override
    public Long count() {
        long count = marketGoodsMapper.countByExample(null);
        return count;
    }
}
