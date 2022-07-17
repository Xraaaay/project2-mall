package com.cskaoyan.service.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.*;

/**
 * 系统管理模块：管理员管理
 * @author Xrw
 * @date 2022/7/16 14:10
 */
public interface AdminService {

    /**
     * 根据模糊查询显示管理员列表
     * @param info 分页信息
     * @param name 管理员名称
     * @return 分页信息和管理员列表
     * @author Xrw
     * @date 2022/7/16 14:29
     */
    CommonData<MarketAdminListVo> list(BasePageInfo info, String name);

    /**
     * 添加一条管理员信息
     * @exception com.cskaoyan.exception.system.InvalidParamException 用户名已存在
     * @author Xrw
     * @date 2022/7/16 20:53
     */
    MarketAdminCreateVo create(MarketAdmin admin);

    /**
     * 修改一条管理员信息
     * @author Xrw
     * @date 2022/7/16 22:05
     */
    MarketAdminUpdateVo update(MarketAdmin admin);

    /**
     * 删除一条管理员信息
     * @author Xrw
     * @date 2022/7/16 22:55
     */
    void delete(MarketAdmin admin);
}
