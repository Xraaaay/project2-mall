package com.cskaoyan.controller.wx.wxissue;

import com.cskaoyan.bean.admin.mallmanagement.BaseParam;
import com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.service.wx.wxIssue.IssueWXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序 帮助中心模块
 *
 * @author shn
 * @date 2022/07/20 10:00
 */
@RestController
@RequestMapping("wx/issue")
public class IssueWXController {
    @Autowired
    IssueWXService issueWXService;
    @GetMapping("list")
    public BaseRespSuccessVo helpCenter(BaseParam param) {
        IssueAndKeywordListVo listVo=issueWXService.issueList(param);
        return BaseRespSuccessVo.success(listVo);
    }

}
