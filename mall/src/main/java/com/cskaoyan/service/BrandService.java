package com.cskaoyan.service;

import com.cskaoyan.bean.MarketBrand;
import com.cskaoyan.bean.po.MarketBrandListPo;

public interface BrandService {
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
    MarketBrandListPo list(Integer page, Integer limit,Integer id,String name, String sort, String order);
    /**
     * 编辑修改品牌商
     *
     * @param marketBrand
     * @return com.cskaoyan.bean.MarketBrand
     * @author changyong
     * @since 2022/07/16 20:40
     */
    MarketBrand update(MarketBrand marketBrand);
    /**
     * 删除品牌商
     *
     * @param marketBrand
     * @return void
     * @author changyong
     * @since 2022/07/16 20:45
     */
    void delete(MarketBrand marketBrand);
}
