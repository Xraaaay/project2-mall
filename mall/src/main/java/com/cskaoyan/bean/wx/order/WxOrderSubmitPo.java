package com.cskaoyan.bean.wx.order;

import lombok.Data;

/**
 * @author changyong
 * @since 2022/07/20 11:27
 */
@Data
public class WxOrderSubmitPo {
    Integer grouponLinkId;
    Integer orderId;

    public WxOrderSubmitPo() {
    }

    public WxOrderSubmitPo(Integer grouponLinkId, Integer orderId) {
        this.grouponLinkId = grouponLinkId;
        this.orderId = orderId;
    }
}
