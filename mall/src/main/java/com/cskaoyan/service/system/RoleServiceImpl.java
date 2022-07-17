package com.cskaoyan.service.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketRole;
import com.cskaoyan.bean.system.MarketRoleCreateVo;
import com.cskaoyan.bean.system.MarketRoleExample;
import com.cskaoyan.bean.system.MarketRoleOptionsVo;
import com.cskaoyan.exception.system.InvalidParamException;
import com.cskaoyan.mapper.system.MarketRoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Xrw
 * @date 2022/7/16 15:55
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    MarketRoleMapper roleMapper;

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

        List<MarketRoleOptionsVo> roles = roleMapper.selectOptionsByExample(null);

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

    @Override
    public void delete(MarketRole role) {
        MarketRole marketRole = new MarketRole();
        marketRole.setId(role.getId());
        marketRole.setUpdateTime(new Date());
        marketRole.setDeleted(true);
        roleMapper.updateByPrimaryKeySelective(marketRole);
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
