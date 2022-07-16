package com.cskaoyan.service.system;

import com.cskaoyan.anno.SystemPage;
import com.cskaoyan.bean.common.BasePageInfo;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.system.MarketLog;
import com.cskaoyan.bean.system.MarketLogExample;
import com.cskaoyan.mapper.system.MarketLogMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xrw
 * @date 2022/7/16 16:16
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    MarketLogMapper logMapper;

    // @SystemPage
    @Override
    public CommonData<MarketLog> list(BasePageInfo info, String name) {
        PageHelper.startPage(info.getPage(), info.getLimit());

        MarketLogExample example = new MarketLogExample();
        example.setOrderByClause(info.getSort() + " " + info.getOrder());
        MarketLogExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null) {
            criteria.andAdminLike("%" + name + "%");
        }

        List<MarketLog> marketLogs = logMapper.selectByExample(example);

        PageInfo<MarketLog> pageInfo = new PageInfo<>(marketLogs);
        return CommonData.data(pageInfo);
    }
}
