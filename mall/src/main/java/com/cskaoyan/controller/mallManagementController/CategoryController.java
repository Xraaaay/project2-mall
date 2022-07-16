package com.cskaoyan.controller.mallManagementController;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.MarketCategory;
import com.cskaoyan.bean.po.MarktCategoryListPo;
import com.cskaoyan.service.CategoryService;
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
     * @return com.cskaoyan.bean.BaseRespVo<com.cskaoyan.bean.po.MarktCategoryListPo>
     * @author changyong
     * @since 2022/07/16 22:33
     */
    @RequestMapping("list")
    public BaseRespVo<MarktCategoryListPo> list() {
        MarktCategoryListPo marktCategoryListPo = categoryService.list();
        return BaseRespVo.ok(marktCategoryListPo);
    }

    /**
     * 修改商品类目
     *
     * @param marketCategory
     * @return void
     * @author changyong
     * @since 2022/07/16 22:32
     */
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
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody MarketCategory marketCategory) {
        categoryService.delete(marketCategory);
        return BaseRespVo.ok(null);
    }
}
