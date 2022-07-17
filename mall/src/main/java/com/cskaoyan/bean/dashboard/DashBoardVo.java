package com.cskaoyan.bean.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台系统所需响应体
 *
 * @author Zah
 * @date 2022/07/18 00:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardVo {


    /**
     * goodsTotal : 245
     * userTotal : 5
     * productTotal : 250
     * orderTotal : 51
     */

    private Long goodsTotal;
    private Long userTotal;
    private Long productTotal;
    private Long orderTotal;
}
