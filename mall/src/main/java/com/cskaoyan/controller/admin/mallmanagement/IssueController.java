package com.cskaoyan.controller.admin.mallmanagement;
import com.cskaoyan.bean.common.MarketIssue;
import com.cskaoyan.bean.admin.mallmanagement.BaseParam;
import com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.service.admin.mallmanagement.IssueService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
     * 获取issue列表
     * @param baseParam
     * @return com.cskaoyan.bean.BaseRespVo
     * @author shn
     * @date 2022/7/16 15:38
     */
    @RequiresPermissions("admin:issue:list")
    @GetMapping("list")
    public BaseRespSuccessVo list(BaseParam baseParam,String question){
        IssueAndKeywordListVo issueList =issueService.issueList(baseParam,question);
        if (issueList==null) {
            return BaseRespSuccessVo.failed(null);
        }
        return BaseRespSuccessVo.success(issueList);
    }

    /**
     * 通用问题：编辑
     * @param marketIssue
     * @return com.cskaoyan.bean.BaseRespVo
     * @author shn
     * @date 2022/7/16 16:10
     */
    @RequiresPermissions("admin:issue:update")
    @PostMapping("update")
    public BaseRespSuccessVo update(@RequestBody MarketIssue marketIssue){
        MarketIssue issue=issueService.updateIssue(marketIssue);
        if (issue==null) {
            return BaseRespSuccessVo.failed(null);
        }
        return BaseRespSuccessVo.success(issue);
    }

    /**
     * 逻辑删除 通用问题
     * @param marketIssue
     * @return com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/16 21:21
     */
    @RequiresPermissions("admin:issue:delete")
    @PostMapping("delete")
    public BaseRespSuccessVo delete(@RequestBody MarketIssue marketIssue){
        issueService.deleteIssue(marketIssue);
        return BaseRespSuccessVo.success(null);
    }

    /**
     * 添加 通用问题
     * @param marketIssue
     * @return com.cskaoyan.bean.admin.mallmanagement.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/17 16:05
     */
    @RequiresPermissions("admin:issue:create")
    @PostMapping("create")
    public BaseRespSuccessVo create(@RequestBody MarketIssue marketIssue) {
        MarketIssue addIssue=issueService.addIssue(marketIssue);
        return BaseRespSuccessVo.success(addIssue);
    }
}
