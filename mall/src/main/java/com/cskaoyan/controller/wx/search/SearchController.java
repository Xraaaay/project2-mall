package com.cskaoyan.controller.wx.search;

import com.cskaoyan.bean.common.BaseRespVo;
import com.cskaoyan.bean.wx.WxSearchIndexVO;
import com.cskaoyan.service.wx.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Service;
import java.util.List;

/**
 * 微信搜索模块
 * @since 2022/07/18 21:29
 * @author lyx
 */

@RestController
@RequestMapping("wx/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping("index")
    public BaseRespVo index() {
      WxSearchIndexVO wxSearchIndexVO = searchService.index();

        return BaseRespVo.ok(wxSearchIndexVO);
    }

    @RequestMapping("helper")
    public BaseRespVo helper( String keyword) {
       List<String> helKey =  searchService.helper(keyword);

        return BaseRespVo.ok(helKey);
    }

    @RequestMapping("clearhistory")
    public BaseRespVo clearhistory() {
       searchService.clearhistory();

        return BaseRespVo.ok(null);
    }

}
