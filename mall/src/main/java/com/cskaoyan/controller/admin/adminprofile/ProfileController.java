package com.cskaoyan.controller.admin.adminprofile;

import com.cskaoyan.bean.admin.profile.PasswordVo;
import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.controller.admin.adminauth.AuthController;
import com.cskaoyan.service.admin.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 后台通知中心的通知列表
     * @param page
     * @param title
     * @param type
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author Zah
     * @date 2022/07/21 15:36
     */
    @GetMapping("lsnotice")
    public BaseRespVo LsNotice(BaseParam page,String title,String type){
        UserListVo lsNotice = profileService.isNotice(page, title, type);

        if (lsNotice == null){
            return AuthController.unAuthc();
        }

        return BaseRespVo.ok(lsNotice);
    }

    /**
     * 后台修改当前管理员密码
     * @param passwordVo
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author Zah
     * @date 2022/07/21 15:36
     */
    @PostMapping("password")
    public BaseRespVo password(@RequestBody PasswordVo passwordVo){

        try {
            profileService.fixPassword(passwordVo);
        } catch (Exception e) {
            return new BaseRespVo<>(null,605,"账户密码不对");
        }

        return BaseRespVo.ok(null);
    }

    /**
     * 以下Handler方法没找到测试的地方
     * @return com.cskaoyan.bean.common.BaseRespVo
     * @author Zah
     * @date 2022/07/21 15:33
     */
    @RequestMapping("nnotice")
    public BaseRespVo nnotice(){
        return BaseRespVo.ok(0);
    }
}
