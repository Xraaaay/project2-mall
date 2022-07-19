package com.cskaoyan.bean.admin.mallmanagement.po;

import lombok.Data;

/**
 * @author changyong
 * @since 2022/07/18 17:20
 */
@Data
public class OrderPo {
    Long uncomment;
    Long unpaid;
    Long unrecv;
    Long unship;

    public OrderPo() {
    }

    public OrderPo(Long uncomment, Long unpaid, Long unrecv, Long unship) {
        this.uncomment = uncomment;
        this.unpaid = unpaid;
        this.unrecv = unrecv;
        this.unship = unship;
    }
}
