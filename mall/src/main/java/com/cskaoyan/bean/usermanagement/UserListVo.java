package com.cskaoyan.bean.usermanagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户管理模块的6个表该对象
 * 会员管理、收货地址、会员收藏、会员足迹、搜索历史、意见反馈
 * @author Zah
 * @date 2022/07/18 17:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListVo<T> {

    Long total;

    Integer pages;

    Integer limit;

    Integer page;

    List<T> list;
}
