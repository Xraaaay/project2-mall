package com.cskaoyan.controller.usermanagement;

import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.usermanagement.UserListVo;
import com.cskaoyan.service.adminusermanagement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理之搜索历史
 *
 * @author Zah
 * @date 2022/07/18 20:53
 */
@RestController
@RequestMapping("admin/history")
public class SearchHistoryController {

    @Autowired
    UserService userService;

    @RequestMapping("list")
    public BaseRespVo list(BaseParam page,Integer userId, String keyword){

        UserListVo searchHistoryList = userService.getSearchHistoryList(page, userId, keyword);

        return BaseRespVo.ok(searchHistoryList);

    }
}
