package com.cskaoyan.controller.admin.adminprofile;

import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.controller.admin.adminauth.AuthController;
import com.cskaoyan.service.admin.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这里是系统通知！
 *
 * @author Zah
 * @date 2022/07/18 17:19
 */
@RestController
@RequestMapping("admin/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("lsnotice")
    public BaseRespVo LsNotice(BaseParam page,String title,String type){
        UserListVo lsNotice = profileService.isNotice(page, title, type);

        if (lsNotice == null){
            return AuthController.unAuthc();
        }

        return BaseRespVo.ok(lsNotice);
    }

    @RequestMapping("nnotice")
    public BaseRespVo nnotice(){
        return BaseRespVo.ok(0);
    }
}
