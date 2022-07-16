package com.cskaoyan.bean.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Xrw
 * @date 2022/7/16 20:51
 */
@NoArgsConstructor
@Data
public class MarketAdminCreateVo {
    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private Date addTime;
    private Date updateTime;
    private Integer[] roleIds;
}
