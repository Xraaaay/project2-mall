package com.cskaoyan.service.wx.catalog;

import com.cskaoyan.bean.common.MarketCategory;
import com.cskaoyan.bean.common.MarketCategoryExample;
import com.cskaoyan.bean.admin.mallmanagement.po.WxCatalogIndexPo;
import com.cskaoyan.mapper.common.MarketCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/18 19:58
 */
@Component
@Transactional
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    MarketCategoryMapper marketCategoryMapper;

    /**
     * 返回类目首页
     *
     * @return com.cskaoyan.bean.admin.mallmanagement.po.WxCatalogIndexPo
     * @author changyong
     * @since 2022/07/20 11:51
     */
    @Override
    public WxCatalogIndexPo index() {
        //获取categoryList
        MarketCategoryExample marketCategoryExample1 = new MarketCategoryExample();
        MarketCategoryExample.Criteria criteria1 = marketCategoryExample1.createCriteria();
        criteria1.andPidEqualTo(0);
        List<MarketCategory> marketCategories = marketCategoryMapper.selectByExample(marketCategoryExample1);
        //LinkedList<MarketCategory> marketCategories = (LinkedList<MarketCategory>) marketCategoryMapper.selectByExample(marketCategoryExample1);
        //获取currentCategory
        MarketCategory marketCategory = marketCategories.get(0);
        //获取currentSubCategory
        MarketCategoryExample marketCategoryExample2 = new MarketCategoryExample();
        MarketCategoryExample.Criteria criteria2 = marketCategoryExample2.createCriteria();
        criteria2.andPidEqualTo(marketCategory.getId());
        List<MarketCategory> marketCategories2 = marketCategoryMapper.selectByExample(marketCategoryExample2);
        //LinkedList<MarketCategory> marketCategories2 = (LinkedList<MarketCategory>) marketCategoryMapper.selectByExample(marketCategoryExample2);

        return new WxCatalogIndexPo(marketCategories, marketCategory, marketCategories2);
    }

    /**
     * 返回当前一级类目包含的二级类目
     *
     * @param id
     * @return com.cskaoyan.bean.admin.mallmanagement.po.WxCatalogIndexPo
     * @author changyong
     * @since 2022/07/20 11:52
     */
    @Override
    public WxCatalogIndexPo current(Integer id) {
        //获取currentCategory
        MarketCategory marketCategory = marketCategoryMapper.selectByPrimaryKey(id);
        //获取currentSubCategory
        MarketCategoryExample marketCategoryExample2 = new MarketCategoryExample();
        MarketCategoryExample.Criteria criteria2 = marketCategoryExample2.createCriteria();
        criteria2.andPidEqualTo(id).andDeletedEqualTo(false);
        List<MarketCategory> marketCategories2 = marketCategoryMapper.selectByExample(marketCategoryExample2);

        return new WxCatalogIndexPo(null, marketCategory, marketCategories2);
    }
}
