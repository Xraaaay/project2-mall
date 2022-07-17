package com.cskaoyan.service;

import com.cskaoyan.bean.MarketCategory;
import com.cskaoyan.bean.po.MarktCategoryListPo;

/**
 * @author changyong
 * @since 2022/07/16 20:56
 */

public interface CategoryService {
    /**
     * 返回商品类目列表
     *
     * @return com.cskaoyan.bean.po.MarktCategoryListPo
     * @author changyong
     * @since 2022/07/16 22:33
     */
    MarktCategoryListPo list();

    /**
     * 修改商品类目
     *
     * @param marketCategory
     * @return void
     * @author changyong
     * @since 2022/07/16 22:32
     */
    void update(MarketCategory marketCategory);

    /**
     * 删除商品类目
     *
     * @param marketCategory
     * @return void
     * @author changyong
     * @since 2022/07/16 22:31
     */
    void delete(MarketCategory marketCategory);

    /**
     * 添加商品类目
     *
     * @param marketCategory
     * @return com.cskaoyan.bean.MarketCategory
     * @author changyong
     * @since 2022/07/17 19:26
     */
    MarketCategory create(MarketCategory marketCategory);
}
