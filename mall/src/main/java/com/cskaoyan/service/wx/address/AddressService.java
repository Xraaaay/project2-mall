package com.cskaoyan.service.wx.address;

import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.MarketAddress;

public interface AddressService {

    UserListVo getAddressList();

    MarketAddress getAddressDetail(Integer id);

    Integer postAddressSave(MarketAddress address);

    void postAddressDelete(Integer id);
}
