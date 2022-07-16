package com.cskaoyan.bean.common;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用数据
 *
 * @author fanxing056
 * @date 2022/07/16 14:26
 */

@NoArgsConstructor
@Data
public class CommonData<T> {

    private Integer total;
    private Integer pages;
    private Integer limit;
    private Integer page;
    private List<T> list;

    public static CommonData data(PageInfo pageInfo) {
        CommonData data = new CommonData();
        data.setTotal((int) pageInfo.getTotal());
        data.setPages(pageInfo.getPages());
        data.setPage(pageInfo.getPageNum());
        data.setLimit(pageInfo.getPageSize());
        data.setList(pageInfo.getList());
        return data;
    }
}
