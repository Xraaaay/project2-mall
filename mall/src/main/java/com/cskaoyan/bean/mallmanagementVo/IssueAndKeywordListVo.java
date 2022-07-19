package com.cskaoyan.bean.mallmanagementVo;

import java.util.List;

/**
 * 响应issue、keyword的list请求
 *
 * @author shn
 * @date 2022/07/16 14:37
 */
public class IssueAndKeywordListVo<T> {

    /**
     * total : 7
     * pages : 1
     * limit : 20
     * page : 1
     * list : [{"isDefault":false,"deleted":false,"addTime":"2018-02-01 00:00:00","sortOrder":100,"updateTime":"2022-07-15 21:45:07","id":1,"keyword":"母亲节","url":"","isHot":false}]
     */
    private long total;
    private int pages;
    private int limit;
    private int page;
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public int getLimit() {
        return limit;
    }

    public int getPage() {
        return page;
    }

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
