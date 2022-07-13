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

    /***
     * @auther shaohuina
     * @param map
     * @return:返回用户名、密码
     */
    @RequestMapping("login")
    public BaseRespVO login(@RequestBody Map map){
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        return BaseRespVO.ok("hello, "+username + "--" + password);
    }

    /**
     * @param username
     * @return
     * @Auther fanxing056
     */
    @RequestMapping("login10010")
    public BaseRespVO login10010(String username) {
        return BaseRespVO.ok(username);
    }

    /**
     * xrw
     */
    @RequestMapping("login666")
    public BaseRespVO login666(String username) {
        return BaseRespVO.ok(username);
    }

    /**
    * @Description: zah
    * @Date: 2022/7/13
    **/
    @RequestMapping("login10000")
    public BaseRespVO info(String username,String password,Integer age){
        return BaseRespVO.ok(username + ";" + password + ";" + age);
    }
    /**
     * @param username
     * @param password
     * @param age
     * @return BaseRespVO
     * @author changyong
     */
    @RequestMapping("login1111")
    public BaseRespVO login1111(String username,String password,Integer age){
        return BaseRespVO.ok(username + "--" + password + "--" + age);
    }
}















