package com.cskaoyan.bean.admin.mallmanagement.po;

import lombok.Data;

/** wx/order/list 返回对象所需
 * @author changyong
 * @since 2022/07/19 09:29
 */
@Data
public class HandleOption {
    boolean aftersale;
    boolean cancel;
    boolean comment;
    boolean confirm;
    boolean delete;
    boolean pay;
    boolean rebuy;
    boolean refund;
}
