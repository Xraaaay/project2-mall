package com.cskaoyan.bean.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Xrw
 * @date 2022/7/16 22:04
 */
@NoArgsConstructor
@Data
public class MarketAdminUpdateVo {
    private Integer id;
    private String username;
    private String avatar;
    private Date addTime;
    private Date updateTime;
    private Integer[] roleIds;
}
