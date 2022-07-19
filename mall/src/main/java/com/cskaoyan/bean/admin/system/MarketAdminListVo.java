package com.cskaoyan.bean.admin.system;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Xrw
 * @date 2022/7/16 14:55
 */
@NoArgsConstructor
@Data
public class MarketAdminListVo {
    private Integer id;
    private String username;
    private String avatar;
    private Integer[] roleIds;
}
