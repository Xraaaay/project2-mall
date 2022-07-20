package com.cskaoyan.service.admin.usermanagement;

import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.admin.usermanagement.UserListVo;

public interface UserService {

    /**
     * 会员管理列表信息
     * @param page 
     * @param username 
     * @param mobile 
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 10:53 
     */
    UserListVo getUserList(BaseParam page,String username,String mobile);

    /**
     * 会员管理中列表的单个用户详细信息
     * @param id
     * @return com.cskaoyan.bean.common.MarketUser
     * @author Zah
     * @date 2022/07/20 10:54
     */
    MarketUser getUserDetail(Integer id);

    /**
     * 收货地址列表
     * @param page
     * @param name
     * @param userId
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 10:55
     */
    UserListVo getAddressList(BaseParam page,String name,Integer userId);

    /**
     * 会员收藏列表
     * @param page
     * @param userId
     * @param valueId
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 10:55
     */
    UserListVo getCollectList(BaseParam page,Integer userId,Integer valueId);

    /**
     * 会员足迹列表
     * @param page
     * @param userId
     * @param goodsId
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 10:55
     */
    UserListVo getFootprintList(BaseParam page,Integer userId,Integer goodsId);

    /**
     * 会员搜索历史列表
     * @param page
     * @param userId
     * @param keyword
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 10:56
     */
    UserListVo getSearchHistoryList(BaseParam page,Integer userId,String keyword);

    /**
     * 意见反馈列表
     * @param page
     * @param username
     * @param id
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 10:56
     */
    UserListVo getFeedbackList(BaseParam page,String username,Integer id);


}
