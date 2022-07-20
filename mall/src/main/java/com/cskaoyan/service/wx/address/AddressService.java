package com.cskaoyan.service.wx.address;

import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.MarketAddress;

public interface AddressService {

    /**
     * 小程序收货地址列表
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 10:57
     */
    UserListVo getAddressList();

    /**
     * 小程序收货地址列表单个地址详细信息
     * @param id
     * @return com.cskaoyan.bean.common.MarketAddress
     * @author Zah
     * @date 2022/07/20 10:57
     */
    MarketAddress getAddressDetail(Integer id);

    /**
     * 保存新建的地址信息或者修改过的地址信息
     * @param address
     * @return java.lang.Integer
     * @author Zah
     * @date 2022/07/20 10:58
     */
    Integer postAddressSave(MarketAddress address);

    /**
     * 逻辑删除收货地址信息
     * @param id
     * @return void
     * @author Zah
     * @date 2022/07/20 10:58
     */
    void postAddressDelete(Integer id);
}
