package com.cskaoyan.bean.wx.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收货地址详细信息所要保存的信息
 *
 * @author Zah
 * @date 2022/07/19 16:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressSaveBo {

    /**
     * id : 25
     * name : 李四
     * tel : 18829038888
     * province : 江苏省
     * city : 扬州市
     * county : 高邮市
     * areaCode : 321084
     * addressDetail : 南京路118号
     * isDefault : false
     */

    private Integer id;
    private String name;
    private String tel;
    private String province;
    private String city;
    private String county;
    private String areaCode;
    private String addressDetail;
    private Boolean isDefault;

}
