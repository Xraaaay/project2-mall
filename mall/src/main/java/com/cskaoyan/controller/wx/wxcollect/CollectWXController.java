package com.cskaoyan.controller.wx.wxcollect;

import com.cskaoyan.bean.admin.mallmanagement.BaseParam;
import com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.wx.wxcollect.InnerListOfWXCollectVo;
import com.cskaoyan.service.wx.wxcollect.CollectWXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 小程序 收藏模块
 *
 * @author shn
 * @date 2022/07/19 21:08
 */
@RestController
@RequestMapping("wx/collect")
public class CollectWXController {
    @Autowired
    CollectWXService collectWXService;
    /**
     *  小程序 收藏列表
     * @param type
     * @param param
     * @return com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/19 21:34
     */
    @GetMapping("list")
    public BaseRespSuccessVo collectList(String type, BaseParam param) {
         IssueAndKeywordListVo collectVos =collectWXService.collectList(type,param);
        return BaseRespSuccessVo.success(collectVos);
    }

    /**
     * 小程序 添加收藏
     * @param map
     * @return com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/19 22:43
     */
    @PostMapping("addordelete")
    public BaseRespSuccessVo addCollect(@RequestBody Map map) {
        Integer addNum = collectWXService.addCollect(map);
        if (addNum!=1) {
            return BaseRespSuccessVo.failed(null);
        }
        return BaseRespSuccessVo.success(null);
    }
}
