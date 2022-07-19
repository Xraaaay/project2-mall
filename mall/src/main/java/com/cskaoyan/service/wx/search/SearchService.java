package com.cskaoyan.service.wx.search;

import com.cskaoyan.bean.wx.search.WxSearchIndexVO;

import java.util.List;

public interface SearchService {

    WxSearchIndexVO index();

    List<String> helper(String keyword);

    void clearhistory();
}
