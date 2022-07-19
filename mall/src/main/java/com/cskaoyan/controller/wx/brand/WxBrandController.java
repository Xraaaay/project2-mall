package com.cskaoyan.controller.wx.brand;

import com.cskaoyan.bean.common.MarketBrand;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.mallmanagement.po.MarketBrandListPo;
import com.cskaoyan.service.admin.mallmanagement.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changyong
 * @since 2022/07/18 21:23
 */
@RestController
@RequestMapping("wx/brand")
public class WxBrandController {

    @Autowired
    BrandService brandService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page,Integer limit){
        MarketBrandListPo list = brandService.list(page, limit, null, null, null, null);
        return BaseRespVo.ok(list);
    }

    @RequestMapping("detail")
    public BaseRespVo detail(Integer id){
        MarketBrand marketBrand = brandService.detail(id);
        return BaseRespVo.ok(marketBrand);
    }
}
