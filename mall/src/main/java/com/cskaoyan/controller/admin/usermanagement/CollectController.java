package com.cskaoyan.controller.admin.usermanagement;

import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.service.admin.usermanagement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理之会员收藏
 *
 * @author Zah
 * @date 2022/07/18 20:00
 */
@RestController
@RequestMapping("admin/collect")
public class CollectController {

    @Autowired
    UserService userService;

    @RequestMapping("list")
    public BaseRespVo list(BaseParam page,Integer userId,Integer valueId){

        UserListVo collectList = userService.getCollectList(page, userId, valueId);

        return BaseRespVo.ok(collectList);
    }
}
