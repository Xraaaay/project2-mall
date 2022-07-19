package com.cskaoyan.bean.admin.mallmanagement.po;

import com.cskaoyan.bean.common.MarketCategory;
import lombok.Data;

import java.util.List;

/**
 * @author changyong
 * @since 2022/07/18 20:08
 */
@Data
public class WxCatalogIndexPo {
    List<MarketCategory> categoryList;
    MarketCategory currentCategory;
    List<MarketCategory> currentSubCategory;

    public WxCatalogIndexPo() {
    }

    public WxCatalogIndexPo(List<MarketCategory> categoryList, MarketCategory currentCategory, List<MarketCategory> currentSubCategory) {
        this.categoryList = categoryList;
        this.currentCategory = currentCategory;
        this.currentSubCategory = currentSubCategory;
    }
}
