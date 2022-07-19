package com.cskaoyan.controller.admin.usermanagement;

import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.service.admin.usermanagement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理之意见反馈
 *
 * @author Zah
 * @date 2022/07/18 21:23
 */
@RestController
@RequestMapping("admin/feedback")
public class FeedbackController {

    @Autowired
    UserService userService;

    @RequestMapping("list")
    public BaseRespVo list(BaseParam page,String username, Integer id){

        UserListVo feedbackList = userService.getFeedbackList(page, username, id);

        return BaseRespVo.ok(feedbackList);
    }
}
