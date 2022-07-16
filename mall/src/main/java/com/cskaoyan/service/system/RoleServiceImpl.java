package com.cskaoyan.service.system;

import com.cskaoyan.anno.SystemPage;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketRole;
import com.cskaoyan.bean.system.MarketRoleExample;
import com.cskaoyan.bean.system.MarketRoleOptionsVo;
import com.cskaoyan.mapper.system.MarketRoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
