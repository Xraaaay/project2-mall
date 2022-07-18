package com.cskaoyan.bean.common;

import lombok.Data;

/**
 * 已过时，使用com.cskaoyan.bean.common.BasePageInfo
 * @author stone
 * @date 2022/01/06 16:37
 */
@Deprecated
@Data
public class BaseParam {

    Integer page;
    Integer limit;
    String sort;
    String order;
}
