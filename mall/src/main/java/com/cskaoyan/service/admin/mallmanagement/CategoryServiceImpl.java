package com.cskaoyan.service.admin.mallmanagement;

import com.cskaoyan.bean.common.MarketCategory;
import com.cskaoyan.bean.common.MarketCategoryExample;
import com.cskaoyan.bean.admin.mallmanagement.po.MarktCategoryListPo;
import com.cskaoyan.mapper.common.MarketCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author changyong
 * @since 2022/07/16 20:57
 */
@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    MarketCategoryMapper marketCategoryMapper;

    /**
     * 返回商品类目列表
     *
     * @return com.cskaoyan.bean.admin.mallmanagement.po.MarktCategoryListPo
     * @author changyong
     * @since 2022/07/16 22:33
     */
    @Override
    public MarktCategoryListPo list() {
        //获取list
        MarketCategoryExample marketCategoryExample1 = new MarketCategoryExample();
        MarketCategoryExample.Criteria criteria1 = marketCategoryExample1.createCriteria();
        criteria1.andPidEqualTo(0)
                .andDeletedEqualTo(false);
        List<MarketCategory> marketCategories1 = marketCategoryMapper.selectByExample(marketCategoryExample1);
        for (MarketCategory marketCategory : marketCategories1) {
            MarketCategoryExample marketCategoryExample2 = new MarketCategoryExample();
            MarketCategoryExample.Criteria criteria2 = marketCategoryExample2.createCriteria();
            criteria2.andPidEqualTo(marketCategory.getId())
                    .andDeletedEqualTo(false);
            List<MarketCategory> marketCategories2 = marketCategoryMapper.selectByExample(marketCategoryExample2);
            marketCategory.setChildren(marketCategories2);
        }
        //获取total
        long total = marketCategoryMapper.countByExample(marketCategoryExample1);

        return new MarktCategoryListPo(total, 1, Long.valueOf(9), 1, marketCategories1);
    }

    /**
     * 修改商品类目
     *
     * @param marketCategory
     * @return void
     * @author changyong
     * @since 2022/07/16 22:32
     */
    @Override
    public void update(MarketCategory marketCategory) {
        marketCategory.setUpdateTime(new Date());
        marketCategoryMapper.updateByPrimaryKeySelective(marketCategory);
    }

    /**
     * 删除商品类目
     *
     * @param marketCategory
     * @return void
     * @author changyong
     * @since 2022/07/16 22:31
     */
    @Override
    public void delete(MarketCategory marketCategory) {
        //创建marketCategory并赋值
        MarketCategory marketCategory1 = new MarketCategory();
        marketCategory1.setId(marketCategory.getId());
        marketCategory1.setDeleted(true);
        marketCategory1.setUpdateTime(new Date());
        //update
        marketCategoryMapper.updateByPrimaryKeySelective(marketCategory1);
    }

    /**
     * 添加商品类目
     *
     * @param marketCategory
     * @return com.cskaoyan.bean.common.MarketCategory
     * @author changyong
     * @since 2022/07/17 19:26
     */
    @Override
    public MarketCategory create(MarketCategory marketCategory) {
        Date addTime = new Date();
        marketCategory.setAddTime(addTime);
        marketCategory.setUpdateTime(addTime);
        marketCategoryMapper.insertSelective(marketCategory);
        return marketCategory;
    }
}
