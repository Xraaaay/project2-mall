package com.cskaoyan.controller.admin.mallmanagement;

import com.cskaoyan.bean.common.MarketCategory;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.mallmanagement.po.MarktCategoryListPo;
import com.cskaoyan.service.admin.mallmanagement.CategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changyong
 * @since 2022/07/15 22:22
 */
@RestController
@RequestMapping("admin/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 返回商品类目列表
     *
     * @return com.cskaoyan.bean.BaseRespVo<com.cskaoyan.bean.admin.mallmanagement.po.MarktCategoryListPo>
     * @author changyong
     * @since 2022/07/16 22:33
     */
    @RequiresPermissions("admin:category:list")
    @RequestMapping("list")
    public BaseRespVo<MarktCategoryListPo> list() {
        MarktCategoryListPo marktCategoryListPo = categoryService.list();
        return BaseRespVo.ok(marktCategoryListPo);
    }

    /**
     * 修改商品类目
     *
     * @param marketCategory
     * @return com.cskaoyan.bean.BaseRespVo
     * @author changyong
     * @since 2022/07/16 22:32
     */
    @RequiresPermissions("admin:category:update")
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody MarketCategory marketCategory) {
        categoryService.update(marketCategory);
        return BaseRespVo.ok(null);
    }

    /**
     * 删除商品类目
     *
     * @param marketCategory
     * @return com.cskaoyan.bean.BaseRespVo
     * @author changyong
     * @since 2022/07/16 22:31
     */
    @RequiresPermissions("admin:category:delete")
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody MarketCategory marketCategory) {
        categoryService.delete(marketCategory);
        return BaseRespVo.ok(null);
    }

    /**
     * 添加商品类目
     *
     * @param marketCategory
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/17 19:25
     */
    @RequiresPermissions("admin:category:create")
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody MarketCategory marketCategory) {
        MarketCategory marketCategory1 = categoryService.create(marketCategory);
        return BaseRespVo.ok(marketCategory1);
    }
}
