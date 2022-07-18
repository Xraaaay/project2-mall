package com.cskaoyan.controller.goods;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.service.goods.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.server.InactiveGroupException;

import java.util.Map;

/**
 * @author pqk
 * @since 2022/07/17 22:48
 */
@RestController
@RequestMapping("admin/comment")
public class CommentController {

    @Autowired
    CommentService commentService;


    /**
     * page=1&limit=20&sort=add_time&order=desc
     * @description 评论模块
     * @author pqk
     * @date 2022/07/17 23:19
     */
    @RequestMapping("list")
    public BaseRespVo list(Integer page,Integer limit,String sort,String order){
        CommonData commonData = commentService.list(page,limit,sort,order);
        return BaseRespVo.ok(commonData);
    }

    /**
     * @description 删除评论（逻辑）
     * @author pqk
     * @date 2022/07/18 0:10
     */
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Map map){
        commentService.delete(map);
        return BaseRespVo.ok(null);
    }

}
