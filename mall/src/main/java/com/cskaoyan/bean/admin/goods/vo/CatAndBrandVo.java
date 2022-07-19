package com.cskaoyan.bean.admin.goods.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/17 18:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatAndBrandVo {
    private List<CategoryListVo> categoryList;
    private List<BrandListVo> brandList;

}
