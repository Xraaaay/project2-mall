package com.cskaoyan.service.wx.catalog;

import com.cskaoyan.bean.admin.mallmanagement.po.WxCatalogIndexPo;

public interface CatalogService {
    WxCatalogIndexPo index();

    WxCatalogIndexPo current(Integer id);
}
