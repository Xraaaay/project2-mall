package com.cskaoyan.controller.goods;

import com.cskaoyan.bean.commentVo.CommentBo;
import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pqk
 * @since 2022/07/18 11:04
 */
@RestController
@RequestMapping("admin/order")
public class OrderControllerr {

    @Autowired
    OrderService orderService;   //共用一个接口

    /**
     * {"commentId":272,"content":"n牛皮🐂"}
     * @description 回复评论 todo 可能和changyong共用一个接口 计划表没有暂定
     * @author pqk
     * @date 2022/07/18 11:07
     */
    // @RequestMapping("reply")
    // public BaseRespVo reply(@RequestBody CommentBo commentBo){
    //     orderService.reply(commentBo);
    //     return BaseRespVo.ok(null);
    // }
}
