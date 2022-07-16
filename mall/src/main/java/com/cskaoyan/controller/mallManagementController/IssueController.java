package com.cskaoyan.controller.mallManagementController;
import com.cskaoyan.bean.MarketIssue;
import com.cskaoyan.bean.mallManagement.BaseParam;
import com.cskaoyan.bean.mallManagement.BaseRespSuccessVo;
import com.cskaoyan.bean.mallManagement.IssueAndKeywordListVo;

import com.cskaoyan.mallManagementService.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 通用问题模块
 * @author shn
 * @date 2022/07/16 14:02
 */
@RestController
@RequestMapping("admin/issue")
public class IssueController {
    @Autowired
    IssueService issueService;

    /**
     * 查询全部issue
     * @param baseParam
     * @return com.cskaoyan.bean.BaseRespVo
     * @author shn
     * @date 2022/7/16 15:38
     */
    @GetMapping("list")
    public BaseRespSuccessVo list(BaseParam baseParam){
        IssueAndKeywordListVo issueList =issueService.issueList(baseParam);
        return BaseRespSuccessVo.success(issueList);
    }

    /**
     * 通用问题：编辑
     * @param marketIssue
     * @return com.cskaoyan.bean.BaseRespVo
     * @author shn
     * @date 2022/7/16 16:10
     */
    @PostMapping("update")
    public BaseRespSuccessVo update(@RequestBody MarketIssue marketIssue){
        MarketIssue issue=issueService.updateIssue(marketIssue);
        return BaseRespSuccessVo.success(issue);
    }

    /**
     * 逻辑删除问题
     * @param marketIssue
     * @return com.cskaoyan.bean.mallManagement.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/16 21:21
     */
    @PostMapping("delete")
    public BaseRespSuccessVo delete(@RequestBody MarketIssue marketIssue){
        issueService.deleteIssue(marketIssue);
        return BaseRespSuccessVo.success(null);
    }

    //@PostMapping("create")
}