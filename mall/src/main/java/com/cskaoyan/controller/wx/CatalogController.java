package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.po.WxCatalogIndexPo;
import com.cskaoyan.service.CatalogService;
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

    @RequestMapping("index")
    public BaseRespVo index(){
        WxCatalogIndexPo wxCatalogIndexPo = catalogService.index();
        return BaseRespVo.ok(wxCatalogIndexPo);
    }

    @RequestMapping("current")
    public BaseRespVo current(Integer id){
        WxCatalogIndexPo wxCatalogIndexPo = catalogService.current(id);
        return BaseRespVo.ok(wxCatalogIndexPo);
    }
}
