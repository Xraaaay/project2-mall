package com.cskaoyan.service.wx.catalog;

import com.cskaoyan.bean.admin.mallmanagement.po.WxCatalogIndexPo;

public interface CatalogService {
    /**
     * 返回类目首页
     *
     * @return com.cskaoyan.bean.admin.mallmanagement.po.WxCatalogIndexPo
     * @author changyong
     * @since 2022/07/20 11:51
     */
    WxCatalogIndexPo index();

    /**
     * 返回当前一级类目包含的二级类目
     *
     * @param id
     * @return com.cskaoyan.bean.admin.mallmanagement.po.WxCatalogIndexPo
     * @author changyong
     * @since 2022/07/20 11:52
     */
    WxCatalogIndexPo current(Integer id);
}
