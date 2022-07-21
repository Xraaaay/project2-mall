package com.cskaoyan.service.admin.mallmanagement;

import com.cskaoyan.bean.admin.mallmanagement.po.AdminCategoryL1Po;
import com.cskaoyan.bean.common.MarketCategory;
import com.cskaoyan.bean.admin.mallmanagement.po.MarktCategoryListPo;

/**
 * @author changyong
 * @since 2022/07/16 20:56
 */

public interface CategoryService {
    /**
     * 返回商品类目列表
     *
     * @return com.cskaoyan.bean.admin.mallmanagement.po.MarktCategoryListPo
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
     * @return com.cskaoyan.bean.common.MarketCategory
     * @author changyong
     * @since 2022/07/17 19:26
     */
    MarketCategory create(MarketCategory marketCategory);

    /**
     * 返回一级类目
     *
     * @return com.cskaoyan.bean.admin.mallmanagement.po.AdminCategoryL1Po
     * @author changyong
     * @since 2022/07/21 16:51
     */
    AdminCategoryL1Po l1();
}
