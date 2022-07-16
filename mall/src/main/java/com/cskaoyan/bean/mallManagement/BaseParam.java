package com.cskaoyan.bean.mallManagement;

import lombok.Data;

/**
 * 分页
 * @author shn
 * @date 2022/07/16 16:37
 */
@Data
public class BaseParam {

    Integer page;
    Integer limit;
    String sort;
    String order;
}
