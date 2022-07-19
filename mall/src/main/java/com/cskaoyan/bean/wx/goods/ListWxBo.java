package com.cskaoyan.bean.wx.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pqk
 * @since 2022/07/19 21:01
 *  list?categoryId=1005010&page=1&limit=10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListWxBo {
    Integer categoryId;
    Integer page;
    Integer limit;
}
