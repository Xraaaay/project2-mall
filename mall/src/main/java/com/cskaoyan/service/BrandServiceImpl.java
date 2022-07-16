package com.cskaoyan.service;

import com.cskaoyan.bean.MarketBrand;
import com.cskaoyan.bean.MarketBrandExample;
import com.cskaoyan.bean.po.MarketBrandListPo;
import com.cskaoyan.mapper.MarketBrandMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author changyong
 * @since 2022/07/16 16:59
 */
@Component
public class BrandServiceImpl implements BrandService {

    @Autowired
    MarketBrandMapper marketBrandMapper;

    /**
     * 返回品牌商列表，根据id和name查询
     *
     * @param page
     * @param limit
     * @param id
     * @param name
     * @param sort
     * @param order
     * @return com.cskaoyan.bean.po.MarketBrandListPo
     * @author changyong
     * @since 2022/07/16 18:15
     */
    @Override
    public MarketBrandListPo list(Integer page, Integer limit, Integer id, String name, String sort, String order) {
        //TODO sort=add_time&order=descc  按添加时间逆序排列
        MarketBrandExample marketBrandExample1 = new MarketBrandExample();
        MarketBrandExample.Criteria criteria1 = marketBrandExample1.createCriteria();
        marketBrandExample1.setOrderByClause(order);
        if (id != null&& ! "".equals(id)) {
            criteria1.andIdEqualTo(id);
        }
        if (name != null&& ! "".equals(name)) {
            criteria1.andNameLike("%" + name + "%");
        }
        long total = marketBrandMapper.countByExample(marketBrandExample1);
        Integer pages = Math.toIntExact((total / limit) + 1);
        //分页之后查询
        PageHelper.startPage(page, limit);
        List<MarketBrand> marketBrands = marketBrandMapper.selectByExample(marketBrandExample1);
        return new MarketBrandListPo(total, pages, new Long(limit), page, marketBrands);
    }

    /**
     * 编辑修改品牌商
     *
     * @param marketBrand
     * @return com.cskaoyan.bean.MarketBrand
     * @author changyong
     * @since 2022/07/16 20:40
     */
    @Override
    public MarketBrand update(MarketBrand marketBrand) {
        marketBrand.setUpdateTime(new Date());
        marketBrandMapper.updateByPrimaryKeySelective(marketBrand);
        return marketBrand;
    }

    /**
     * 删除品牌商
     *
     * @param marketBrand
     * @return void
     * @author changyong
     * @since 2022/07/16 20:45
     */
    @Override
    public void delete(MarketBrand marketBrand) {
        marketBrandMapper.deleteByPrimaryKey(marketBrand.getId());
    }
}
