package com.cskaoyan.bean.admin.marketconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 
 * @since 2022/07/18 17:00
 * @author lyx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatGoodsVO {
    private List<String> columns;
    private List<StatGoodsRowsVO> rows;
}
