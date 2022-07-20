package com.cskaoyan.bean.wx.goods;

import com.cskaoyan.bean.common.CommonData;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/19 21:08
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageInfoDataVo<T,Z> {

    private Long total;
    private Integer pages;
    private Integer limit;
    private Integer page;
    private List<T> list;
    private List<Z> filterCategoryList;

    // public static PageInfoDataVo data(com.github.pagehelper.PageInfo pageInfo) {
    //     PageInfo data = new PageInfo();
    //     data.setTotal((int)pageInfo.getTotal());
    //     data.setPages(pageInfo.getPages());
    //     data.setPage(pageInfo.getPageNum());
    //     data.setLimit(pageInfo.getPageSize());
    //     data.setList(pageInfo.getList());
    //     return data;
    // }
}
