package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.controller.wx.auth.WxAuthController;
import com.cskaoyan.service.wx.wxfootprint.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 浏览足迹
 *
 * @author Zah
 * @date 2022/07/20 10:18
 */
@RestController
@RequestMapping("wx/footprint")
public class FootprintController {

    @Autowired
    FootprintService footprintService;

    @GetMapping("list")
    public BaseRespVo list(BaseParam page){

        UserListVo footprint = footprintService.getFootprintList(page);

        if (footprint == null){
            return WxAuthController.unAuthc();
        }

        return BaseRespVo.ok(footprint);
    }

    @PostMapping("delete")
    public BaseRespVo delete(@RequestBody Map map){

        Integer id = (Integer) map.get("id");

        footprintService.postFootprintDelete(id);

        return BaseRespVo.ok(null);
    }
}
