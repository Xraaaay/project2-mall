package com.project.controller;

import com.project.bean.BaseRespVO;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    // TODO HandlerMethod

    /***
     * shaohuina
     * @param map
     * @return
     */
    @RequestMapping("login")
    public BaseRespVO login(@RequestBody Map map){
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        return BaseRespVO.ok(username+"--"+password);
    }

    @RequestMapping("login10010")
    public BaseRespVO login10010(String username) {
        return BaseRespVO.ok(username);
    }

    @RequestMapping("login10086")
    public BaseRespVO login10086(String username) {
        return BaseRespVO.ok(username);
    }
}















