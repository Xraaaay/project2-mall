package com.cskaoyan.bean.wx.goods;

import com.cskaoyan.bean.common.MarketCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/19 20:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWxVo {

    private MarketCategory currentCategory;
    private MarketCategory parentCategory;
    private List<MarketCategory> brotherCategory;

}
