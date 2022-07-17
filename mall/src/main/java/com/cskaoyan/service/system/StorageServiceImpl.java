package com.cskaoyan.service.system;

import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketAdminExample;
import com.cskaoyan.bean.system.MarketAdminListVo;
import com.cskaoyan.bean.system.MarketStorageListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @since 2022/07/17 14:29
 * @author lyx
 */

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired


    @Override
    public CommonData<MarketStorageListVo> list(BasePageInfo info, String name) {
        PageHelper.startPage(info.getPage(), info.getLimit());

        MarketAdminExample example = new MarketAdminExample();
        example.setOrderByClause(info.getSort() + " " + info.getOrder());
        MarketAdminExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null) {
            criteria.andUsernameLike("%" + name + "%");
        }

        // List<MarketAdminListVo> admins = adminMapper.selectListByExample(example);

        // PageInfo<MarketAdminListVo> pageInfo = new PageInfo<>(admins);
        // return CommonData.data(pageInfo);


        return null;
    }
}
