package com.cskaoyan.controller.wx.user;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.mallmanagement.po.WxUserIndexOrderPo;
import com.cskaoyan.service.wx.user.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**用户部分
 * @author changyong
 * @since 2022/07/18 16:36
 */
@RestController
@RequestMapping("wx/user")
public class WxUserController {

    @Autowired
    WxUserService wxUserService;

    @RequestMapping("index")
    public BaseRespVo index(){
        WxUserIndexOrderPo wxUserIndexOrderPo = wxUserService.index();
        return BaseRespVo.ok(wxUserIndexOrderPo);
    }
}
