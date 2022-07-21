package com.cskaoyan.bean.wx.goods.detail;

import com.cskaoyan.bean.common.MarketComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/20 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    List<MarketComment> data;
    Integer count;
}
