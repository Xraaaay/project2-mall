package com.cskaoyan.bean.wx.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 个人中心优惠券列表VO
 *
 * @author fanxing056
 * @date 2022/07/19 17:27
 */

@Data
public class MyCouponListVO {
    Integer id;
    Integer cid;
    String name;
    String desc;
    String tag;
    BigDecimal min;
    BigDecimal discount;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date endTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date startTime;
    boolean available = true;
}
