package com.cskaoyan.bean.wx.aftersale;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author changyong
 * @since 2022/07/20 20:31
 */
@Data
public class WxAftersaleSubmitBo {
    BigDecimal amount;
    Integer orderId;
    String[] pictures;
    String reason;
    Short type;
    String typeDesc;
}
