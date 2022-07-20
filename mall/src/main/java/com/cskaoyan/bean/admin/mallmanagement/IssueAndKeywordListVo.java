package com.cskaoyan.bean.admin.mallmanagement;

import lombok.Data;

import java.util.List;

/**
 * 响应issue、keyword的list请求
 *
 * @author shn
 * @date 2022/07/16 14:37
 */
@Data
public class IssueAndKeywordListVo<T> {
    private long total;
    private int pages;
    private int limit;
    private int page;
    private List<T> list;

    public static <T> IssueAndKeywordListVo listVo(long total, int pages, int limit, int page, List<T> list) {
        IssueAndKeywordListVo keywordListVo = new IssueAndKeywordListVo();
        keywordListVo.setTotal(total);
        keywordListVo.setPages(pages);
        keywordListVo.setLimit(limit);
        keywordListVo.setPage(page);
        keywordListVo.setList(list);
        return keywordListVo;
    }
}
