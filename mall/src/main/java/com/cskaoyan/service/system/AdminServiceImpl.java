package com.cskaoyan.service.system;

import com.cskaoyan.anno.SystemPage;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketAdminExample;
import com.cskaoyan.bean.system.MarketAdminListVo;
import com.cskaoyan.mapper.system.MarketAdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        MarketAdminExample.Criteria criteria = example.createCriteria();
        if (name != null) {
            criteria.andUsernameLike("%" + name + "%");
        }

        List<MarketAdminListVo> admins = adminMapper.selectListByExample(example);

        PageInfo<MarketAdminListVo> pageInfo = new PageInfo<>(admins);
        return CommonData.data(pageInfo);
    }
}
