package com.cskaoyan.bean.admin.mallmanagement.po;

import lombok.Data;

/**
 * @author changyong
 * @since 2022/07/18 16:50
 */
@Data
public class WxUserIndexOrderPo {

    OrderPo order;

    public WxUserIndexOrderPo() {
    }

    public WxUserIndexOrderPo(OrderPo order) {
        this.order = order;
    }
}
