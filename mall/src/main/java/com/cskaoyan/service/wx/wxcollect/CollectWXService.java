package com.cskaoyan.service.wx.wxcollect;

import com.cskaoyan.bean.admin.mallmanagement.BaseParam;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.wx.wxcollect.InnerListOfWXCollectVo;

import java.util.List;
import java.util.Map;

/**
 * @author: shn
 * @date:2022/7/19 21:21
 */
public interface CollectWXService {
    /*小程序 收藏列表*/
    IssueAndKeywordListVo collectList(String type, BaseParam param);
    /*小程序 添加收藏*/
    Integer addCollect(Map map);
}
