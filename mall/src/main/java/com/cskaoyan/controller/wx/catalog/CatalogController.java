package com.cskaoyan.controller.wx.catalog;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.mallmanagement.po.WxCatalogIndexPo;
import com.cskaoyan.service.wx.catalog.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changyong
 * @since 2022/07/18 19:54
 */
@RestController
@RequestMapping("wx/catalog")
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    /**
     * 返回类目首页
     *
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:51
     */
    @RequestMapping("index")
    public BaseRespVo index() {
        WxCatalogIndexPo wxCatalogIndexPo = catalogService.index();
        return BaseRespVo.ok(wxCatalogIndexPo);
    }

    /**
     * 返回当前一级类目包含的二级类目
     *
     * @param id
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author changyong
     * @since 2022/07/20 11:53
     */
    @RequestMapping("current")
    public BaseRespVo current(Integer id) {
        WxCatalogIndexPo wxCatalogIndexPo = catalogService.current(id);
        return BaseRespVo.ok(wxCatalogIndexPo);
    }
}
