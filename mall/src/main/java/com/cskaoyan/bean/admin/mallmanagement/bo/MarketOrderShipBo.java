package com.cskaoyan.bean.admin.mallmanagement.bo;

import lombok.Data;

/**
 * @author changyong
 * @since 2022/07/17 09:38
 */
@Data
public class MarketOrderShipBo {
    Integer orderId;
    String shipChannel;
    String shipSn;
}
