package com.cskaoyan.service;

import com.cskaoyan.bean.po.WxCatalogIndexPo;

public interface CatalogService {
    WxCatalogIndexPo index();

    WxCatalogIndexPo current(Integer id);
}
