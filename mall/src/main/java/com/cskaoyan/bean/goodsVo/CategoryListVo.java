package com.cskaoyan.bean.goodsVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/17 18:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListVo {
    private Integer value;
    private String label;
    private List<Children> children;
}
