package com.cskaoyan.service.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketRole;
import com.cskaoyan.bean.system.MarketRoleOptionsVo;

/**
 * 系统管理模块：角色管理
 * @author Xrw
 * @date 2022/7/16 15:54
 */
public interface RoleService {
    /**
     * 显示管理员角色列表
     * @param info 分页信息
     * @param name 角色名称
     * @return 分页信息和角色列表
     * @author Xrw
     * @date 2022/7/16 15:57
     */
    CommonData<MarketRole> list(BasePageInfo info, String name);

    /**
     * 在管理员管理模块，显示管理员角色列表
     * @return 管理员角色列表
     * @author Xrw
     * @date 2022/7/16 18:03
     */
    CommonData<MarketRoleOptionsVo> options();
}
