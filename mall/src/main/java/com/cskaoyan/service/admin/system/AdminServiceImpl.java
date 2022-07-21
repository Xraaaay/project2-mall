package com.cskaoyan.service.admin.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.admin.system.*;
import com.cskaoyan.exception.InvalidParamException;
import com.cskaoyan.mapper.system.MarketAdminMapper;
import com.cskaoyan.util.Md5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Xrw
 * @date 2022/7/16 14:11
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    MarketAdminMapper adminMapper;

    // @SystemPage
    @Override
    public CommonData<MarketAdminListVo> list(BasePageInfo info, String name) {
        PageHelper.startPage(info.getPage(), info.getLimit());

        MarketAdminExample example = new MarketAdminExample();
        example.setOrderByClause(info.getSort() + " " + info.getOrder());
        MarketAdminExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null) {
            criteria.andUsernameLike("%" + name + "%");
        }

        List<MarketAdminListVo> admins = adminMapper.selectListByExample(example);

        PageInfo<MarketAdminListVo> pageInfo = new PageInfo<>(admins);
        return CommonData.data(pageInfo);
    }

    @Transactional
    @Override
    public MarketAdminCreateVo create(MarketAdmin admin) throws Exception {
        // 用户名重复
        checkName(admin);

        admin.setAddTime(new Date());
        admin.setUpdateTime(new Date());
        String password = admin.getPassword();
        String md5 = Md5Utils.getMd5(password);
        admin.setPassword(md5);
        adminMapper.insertSelective(admin);

        // xrw 赋值工具类
        MarketAdmin marketAdmin1 = adminMapper.selectByPrimaryKey(admin.getId());
        MarketAdminCreateVo createVo = new MarketAdminCreateVo();
        createVo.setId(marketAdmin1.getId());
        createVo.setUsername(marketAdmin1.getUsername());
        createVo.setPassword("");
        createVo.setAvatar(marketAdmin1.getAvatar());
        createVo.setAddTime(marketAdmin1.getAddTime());
        createVo.setUpdateTime(marketAdmin1.getUpdateTime());
        createVo.setRoleIds(marketAdmin1.getRoleIds());
        return createVo;
    }

    @Transactional
    @Override
    public MarketAdminUpdateVo update(MarketAdmin admin) throws Exception {
        checkName(admin);

        admin.setUpdateTime(new Date());
        String password = admin.getPassword();
        String md5 = Md5Utils.getMd5(password);
        admin.setPassword(md5);
        adminMapper.updateByPrimaryKeySelective(admin);

        MarketAdmin marketAdmin = adminMapper.selectByPrimaryKey(admin.getId());
        MarketAdminUpdateVo updateVo = new MarketAdminUpdateVo();
        updateVo.setId(marketAdmin.getId());
        updateVo.setUsername(marketAdmin.getUsername());
        updateVo.setAddTime(marketAdmin.getAddTime());
        updateVo.setUpdateTime(marketAdmin.getUpdateTime());
        updateVo.setRoleIds(marketAdmin.getRoleIds());
        return updateVo;
    }

    @Transactional
    @Override
    public void delete(MarketAdmin admin) {
        MarketAdmin marketAdmin = new MarketAdmin();
        marketAdmin.setId(admin.getId());
        marketAdmin.setUpdateTime(new Date());
        marketAdmin.setDeleted(true);
        adminMapper.updateByPrimaryKeySelective(marketAdmin);
    }

    private void checkName(MarketAdmin admin) {
        MarketAdminExample example = new MarketAdminExample();
        MarketAdminExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(admin.getUsername());
        criteria.andDeletedEqualTo(false);

        List<MarketAdmin> marketAdmins = adminMapper.selectByExample(example);
        if (marketAdmins.size() > 0 && !marketAdmins.get(0).getId().equals(admin.getId())) {
            throw new InvalidParamException("用户名已存在");
        }
    }
}
