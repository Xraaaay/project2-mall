package com.cskaoyan.service.admin.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.admin.system.MarketRole;
import com.cskaoyan.bean.admin.system.MarketRoleCreateVo;
import com.cskaoyan.bean.admin.system.MarketRoleOptionsVo;
import com.cskaoyan.bean.admin.system.SystemPermissions;
import com.cskaoyan.exception.InvalidParamException;

import java.util.List;
import java.util.Map;

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

    /**
     * 新增一条角色信息
     * @throws InvalidParamException 角色名已存在
     * @author Xrw
     * @date 2022/7/17 16:16
     */
    MarketRoleCreateVo create(MarketRole role);

    /**
     * 修改一条角色信息
     * @throws InvalidParamException 角色名已存在
     * @author Xrw
     * @date 2022/7/17 16:45
     */
    void update(MarketRole role);

    /**
     * 逻辑删除一条角色信息
     * @author Xrw
     * @date 2022/7/17 17:13
     */
    void delete(MarketRole role);

    /**
     * 通过Json请求，构造权限表
     * @author Xrw
     * @date 2022/7/17 21:46
     */
    void permissions(SystemPermissions systemPermissions);

    /**
     * 获取全部权限和当前角色已有权限
     * @author Xrw
     * @date 2022/7/17 23:07
     */
    Map<String, Object> getPermissions(Integer roleId);

    /**
     * 修改当前角色权限
     * @author Xrw
     * @date 2022/7/18 9:28
     */
    void setPermissions(Integer roleId, List<String> permissions);
}
