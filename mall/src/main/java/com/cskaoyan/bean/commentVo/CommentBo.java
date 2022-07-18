package com.cskaoyan.bean.commentVo;

/**
 * @author pqk
 * @since 2022/07/18 11:02
 */

public class CommentBo {

    /**
     * commentId : 260
     * content : niu皮
     */
    private Integer commentId;
    private String content;

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }
}
