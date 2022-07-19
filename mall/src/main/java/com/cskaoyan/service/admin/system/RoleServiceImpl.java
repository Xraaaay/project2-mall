package com.cskaoyan.service.admin.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.admin.system.*;
import com.cskaoyan.exception.InvalidParamException;
import com.cskaoyan.mapper.system.MarketPermissionMapper;
import com.cskaoyan.mapper.system.MarketRoleMapper;
import com.cskaoyan.mapper.system.MarketRolePermissionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Xrw
 * @date 2022/7/16 15:55
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    MarketRoleMapper roleMapper;

    @Autowired
    MarketRolePermissionMapper systemPermissionMapper;

    @Autowired
    MarketPermissionMapper assignedPermissionMapper;

    // @SystemPage
    @Override
    public CommonData<MarketRole> list(BasePageInfo info, String name) {
        PageHelper.startPage(info.getPage(), info.getLimit());

        MarketRoleExample example = new MarketRoleExample();
        example.setOrderByClause(info.getSort() + " " + info.getOrder());
        MarketRoleExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null) {
            criteria.andNameLike("%" + name + "%");
        }

        List<MarketRole> roles = roleMapper.selectByExample(example);

        PageInfo<MarketRole> pageInfo = new PageInfo<>(roles);
        return CommonData.data(pageInfo);
    }

    @Override
    public CommonData<MarketRoleOptionsVo> options() {

        MarketRoleExample example = new MarketRoleExample();
        MarketRoleExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<MarketRoleOptionsVo> roles = roleMapper.selectOptionsByExample(example);

        PageHelper.startPage(1, roles.size());
        PageInfo<MarketRoleOptionsVo> pageInfo = new PageInfo<>(roles);
        return CommonData.data(pageInfo);
    }

    @Transactional
    @Override
    public MarketRoleCreateVo create(MarketRole role) {
        checkName(role);

        role.setAddTime(new Date());
        role.setUpdateTime(new Date());
        roleMapper.insertSelective(role);

        MarketRoleCreateVo createVo = new MarketRoleCreateVo();
        createVo.setId(role.getId());
        createVo.setName(role.getName());
        createVo.setDesc(role.getDesc());
        createVo.setAddTime(role.getAddTime());
        createVo.setUpdateTime(role.getUpdateTime());
        return createVo;
    }

    @Transactional
    @Override
    public void update(MarketRole role) {
        checkName(role);
        role.setUpdateTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Transactional
    @Override
    public void delete(MarketRole role) {
        // 逻辑删除角色
        MarketRole marketRole = new MarketRole();
        marketRole.setId(role.getId());
        marketRole.setUpdateTime(new Date());
        marketRole.setDeleted(true);
        roleMapper.updateByPrimaryKeySelective(marketRole);

        // 删除角色对应的权限
        MarketPermissionExample example = new MarketPermissionExample();
        MarketPermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(role.getId());
        assignedPermissionMapper.deleteByExample(example);
    }

    @Transactional
    @Override
    public void permissions(SystemPermissions systemPermissions) {
        for (SystemPermissions.First first : systemPermissions.getSystemPermissions()) {

            MarketRolePermission firstPermission = new MarketRolePermission();
            firstPermission.setId(first.getId());
            firstPermission.setLabel(first.getLabel());
            systemPermissionMapper.insertSelective(firstPermission);

            for (SystemPermissions.First.Second second : first.getChildren()) {

                MarketRolePermission secondPermission = new MarketRolePermission();
                secondPermission.setId(second.getId());
                secondPermission.setLabel(second.getLabel());
                secondPermission.setPid(firstPermission.getPrimaryKey());
                systemPermissionMapper.insertSelective(secondPermission);

                for (SystemPermissions.First.Second.Third third : second.getChildren()) {

                    MarketRolePermission thirdPermission = new MarketRolePermission();
                    thirdPermission.setId(third.getId());
                    thirdPermission.setLabel(third.getLabel());
                    thirdPermission.setApi(third.getApi());
                    thirdPermission.setPid(secondPermission.getPrimaryKey());
                    systemPermissionMapper.insertSelective(thirdPermission);
                }
            }
        }
    }

    @Override
    public Map<String, Object> getPermissions(Integer roleId) {
        // 全部权限
        List<MarketRolePermission> allPermissions = systemPermissionMapper.selectByExample(null);

        // xrw 将数据库数据转换成json对象，简化
        List<SystemPermissions.First> systemPermissions = new ArrayList<>();

        for (int k = 0; k < allPermissions.size(); k++) {
            MarketRolePermission first = allPermissions.get(k);
            if (first.getPid() == null) {
                // 是一级权限
                SystemPermissions.First f = new SystemPermissions.First();
                f.setId(first.getId());
                f.setLabel(first.getLabel());
                f.setChildren(new ArrayList<>());
                systemPermissions.add(f);

                for (int i = 0; i < allPermissions.size(); i++) {
                    MarketRolePermission second = allPermissions.get(i);
                    if (first.getPrimaryKey().equals(second.getPid())) {
                        // 是二级权限
                        SystemPermissions.First.Second s = new SystemPermissions.First.Second();
                        s.setId(second.getId());
                        s.setLabel(second.getLabel());
                        s.setChildren(new ArrayList<>());
                        f.getChildren().add(s);

                        for (int j = 0; j < allPermissions.size(); j++) {
                            MarketRolePermission third = allPermissions.get(j);
                            // 是三级权限
                            if (second.getPrimaryKey().equals(third.getPid())) {
                                SystemPermissions.First.Second.Third t = new SystemPermissions.First.Second.Third();
                                t.setId(third.getId());
                                t.setLabel(third.getLabel());
                                t.setApi(third.getApi());
                                s.getChildren().add(t);
                            }
                        }
                    }
                }
            }
        }

        // 当前角色已有权限
        MarketPermissionExample example = new MarketPermissionExample();
        MarketPermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andDeletedEqualTo(false);
        List<String> assignedPermissions =
                assignedPermissionMapper.selectPermissionsByExample(example);

        Map<String, Object> map = new HashMap<>();
        map.put("systemPermissions", systemPermissions);
        map.put("assignedPermissions", assignedPermissions);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setPermissions(Integer roleId, List<String> permissions) {
        // xrw 优化，考虑使用逻辑删除
        // 删除所有权限
        MarketPermissionExample example = new MarketPermissionExample();
        MarketPermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        assignedPermissionMapper.deleteByExample(example);

        // 新增权限
        List<MarketPermission> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            MarketPermission marketPermission = new MarketPermission();
            marketPermission.setPermission(permission);
            marketPermission.setRoleId(roleId);
            marketPermission.setAddTime(new Date());
            marketPermission.setUpdateTime(new Date());
            permissionList.add(marketPermission);
        }
        assignedPermissionMapper.insertListSelective(permissionList);
    }

    private void checkName(MarketRole role) {
        MarketRoleExample example = new MarketRoleExample();
        MarketRoleExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(role.getName());
        criteria.andDeletedEqualTo(false);

        List<MarketRole> roles = roleMapper.selectByExample(example);
        if (roles.size() > 0 && !roles.get(0).getId().equals(role.getId())) {
            throw new InvalidParamException("角色名已存在！");
        }
    }
}
