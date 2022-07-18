package com.cskaoyan.service.adminusermanagement;

import com.cskaoyan.bean.MarketUser;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.usermanagement.UserListVo;

public interface UserService {

    UserListVo getUserList(BaseParam page,String username,String mobile);

    MarketUser getUserDetail(Integer id);

    UserListVo getAddressList(BaseParam page,String name,Integer userId);

    UserListVo getCollectList(BaseParam page,Integer userId,Integer valueId);

    UserListVo getFootprintList(BaseParam page,Integer userId,Integer goodsId);

    UserListVo getSearchHistoryList(BaseParam page,Integer userId,String keyword);

    UserListVo getFeedbackList(BaseParam page,String username,Integer id);


}
