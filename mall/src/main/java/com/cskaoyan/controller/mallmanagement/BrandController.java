package com.cskaoyan.controller.mallmanagement;

import com.cskaoyan.bean.MarketBrand;
import com.cskaoyan.bean.bo.MarketBrandCreateBo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.po.MarketBrandListPo;
import com.cskaoyan.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商场管理的品牌制造商部分
 *
 * @author changyong
 * @since 2022/07/15 22:12
 */
@RestController
@RequestMapping("admin/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    /**
     * 返回品牌商列表，根据id和name查询
     *
     * @param page
     * @param limit
     * @param id
     * @param name
     * @param sort
     * @param order
     * @return com.cskaoyan.bean.BaseRespVo<com.cskaoyan.bean.po.MarketBrandListPo>
     * @author changyong
     * @since 2022/07/16 18:16
     */
    @RequestMapping("list")
    public BaseRespVo<MarketBrandListPo> list(Integer page, Integer limit, Integer id, String name, String sort, String order) {
        MarketBrandListPo marketBrandListPo = brandService.list(page, limit, id, name, sort, order);
        return BaseRespVo.ok(marketBrandListPo);
    }

    /**
     * 编辑修改品牌商
     *
     * @param marketBrand
     * @return com.cskaoyan.bean.BaseRespVo<com.cskaoyan.bean.MarketBrand>
     * @author changyong
     * @since 2022/07/16 20:41
     */
    @RequestMapping("update")
    public BaseRespVo<MarketBrand> update(@RequestBody MarketBrand marketBrand) {
        MarketBrand update = brandService.update(marketBrand);
        return BaseRespVo.ok(update);
    }

    /**
     * 删除品牌商信息
     *
     * @param marketBrand
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/17 18:22
     */
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody MarketBrand marketBrand) {
        brandService.delete(marketBrand);
        return BaseRespVo.ok(null);
    }

    /**
     * 添加品牌商信息
     *
     * @param marketBrandCreateBo
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/17 19:09
     */
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody MarketBrandCreateBo marketBrandCreateBo) {
        MarketBrand marketBrand = brandService.create(marketBrandCreateBo);
        return BaseRespVo.ok(marketBrand);
    }
}
