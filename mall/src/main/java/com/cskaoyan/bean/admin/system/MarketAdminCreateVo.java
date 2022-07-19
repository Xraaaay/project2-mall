package com.cskaoyan.bean.admin.system;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private Integer[] roleIds;
}
