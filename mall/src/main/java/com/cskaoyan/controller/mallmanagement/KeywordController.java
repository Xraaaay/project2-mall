package com.cskaoyan.controller.mallmanagement;
import com.cskaoyan.bean.MarketKeyword;
import com.cskaoyan.bean.mallManagement.BaseParam;
import com.cskaoyan.bean.mallManagement.BaseRespSuccessVo;
import com.cskaoyan.bean.mallManagement.IssueAndKeywordListVo;
import com.cskaoyan.service.mallmanagement.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 关键词模块
 * @author shn
 * @date 2022/07/16 14:02
 */
@RestController
@RequestMapping("admin/keyword")
public class KeywordController {
    @Autowired
    KeywordService keywordService;

    /**
     *  获取关键词列表
     * @param param
     * @return com.cskaoyan.bean.BaseRespVo
     * @author shn
     * @date 2022/7/16 15:48
     */
    @GetMapping("list")
    public BaseRespSuccessVo list(BaseParam param,String keyword,String url){
        IssueAndKeywordListVo keywordList=keywordService.keywordlist(param,keyword,url);
        return BaseRespSuccessVo.success(keywordList);
    }

    /**
     *  关键词 修改
     * @param marketKeyword
     * @return com.cskaoyan.bean.mallManagement.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/16 20:00
     */
    @PostMapping("update")
    public BaseRespSuccessVo update(@RequestBody MarketKeyword marketKeyword){
        MarketKeyword keyword=keywordService.updateKeyword(marketKeyword);
        return BaseRespSuccessVo.success(keyword);
    }
    /**
     * 逻辑删除 关键词
     * @param marketKeyword
     * @return com.cskaoyan.bean.mallManagement.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/16 21:29
     */
    @PostMapping("delete")
    public BaseRespSuccessVo delete(@RequestBody MarketKeyword marketKeyword){
        keywordService.deleteKeyword(marketKeyword);
        return BaseRespSuccessVo.success(null);
    }

    /**
     * 关键词 添加
     * @param marketKeyword
     * @return com.cskaoyan.bean.mallManagement.BaseRespSuccessVo
     * @author shn
     * @date 2022/7/17 16:51
     */
    @PostMapping("create")
    public BaseRespSuccessVo create(@RequestBody MarketKeyword marketKeyword) {
        MarketKeyword keyword=keywordService.addKeyword(marketKeyword);
        return BaseRespSuccessVo.success(keyword);
    }

}
