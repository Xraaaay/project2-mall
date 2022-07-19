package com.cskaoyan.controller.wx.address;

import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.MarketAddress;
import com.cskaoyan.controller.wx.auth.WxAuthController;
import com.cskaoyan.service.wx.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 收货地址
 *
 * @author Zah
 * @date 2022/07/19 16:21
 */
@RestController
@RequestMapping("wx/address")
public class WXAddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("list")
    public BaseRespVo list(){

        UserListVo addressList = addressService.getAddressList();

        if (addressList == null){
            return WxAuthController.unAuthc();
        }

        return BaseRespVo.ok(addressList);
    }

    @GetMapping("detail")
    public BaseRespVo detail(Integer id){

        MarketAddress addressDetail = addressService.getAddressDetail(id);

        return BaseRespVo.ok(addressDetail);
    }

    @PostMapping("save")
    public BaseRespVo save(@RequestBody MarketAddress address){

        Integer id = addressService.postAddressSave(address);

        if (id == null){
            return WxAuthController.unAuthc();
        }

        return BaseRespVo.ok(id);
    }

    @PostMapping("delete")
    public BaseRespVo delete(@RequestBody Map map){

        Integer id = (Integer) map.get("id");

        addressService.postAddressDelete(id);

        return BaseRespVo.ok(null);
    }
}
