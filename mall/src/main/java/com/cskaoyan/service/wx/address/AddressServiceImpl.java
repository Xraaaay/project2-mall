package com.cskaoyan.service.wx.address;

import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.MarketAddress;
import com.cskaoyan.bean.common.MarketAddressExample;
import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.mapper.common.MarketAddressMapper;
import com.cskaoyan.util.CurTimeUtil;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址
 *
 * @author Zah
 * @date 2022/07/19 16:09
 */
@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    MarketAddressMapper marketAddressMapper;

    /**
     * 收货地址信息列表
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/19 16:18
     */
    @Override
    public UserListVo getAddressList() {

        // 获取操作主体subject，就可以获得当前登录用户的所有信息
        Subject subject = SecurityUtils.getSubject();

        if (subject.getPrincipals()== null){
            subject.logout();
            return null;
        }

        MarketUser primaryPrincipal = (MarketUser) subject.getPrincipals().getPrimaryPrincipal();

        MarketAddressExample addressExample = new MarketAddressExample();

        MarketAddressExample.Criteria criteria = addressExample.createCriteria();

        criteria.andDeletedEqualTo(false);

        criteria.andUserIdEqualTo(primaryPrincipal.getId());

        List<MarketAddress> marketAddresses = marketAddressMapper.selectByExample(addressExample);

        // 将查询结果作为有参构造方法的形参传入，可以获得PageInfo
        PageInfo<MarketAddress> marketAddressPageInfo = new PageInfo<>(marketAddresses);

        UserListVo<MarketAddress> marketAddressUserListVo = new UserListVo<>(marketAddressPageInfo.getTotal(), marketAddressPageInfo.getPages(),
                marketAddressPageInfo.getPageNum(),marketAddressPageInfo.getPageSize(), marketAddresses);

        return marketAddressUserListVo;
    }

    /**
     * 收货地址详细信息
     * @param id
     * @return com.cskaoyan.bean.common.MarketUser
     * @author Zah
     * @date 2022/07/19 16:38
     */
    @Override
    public MarketAddress getAddressDetail(Integer id) {

        MarketAddress marketAddress = marketAddressMapper.selectByPrimaryKey(id);

        return marketAddress;
    }

    /**
     * 保存修改好的收货地址（修改了MarketAddress的AddTime和UpdateTime的类型）
     * @param address
     * @return java.lang.Integer
     * @author Zah
     * @date 2022/07/19 16:52
     */
    @Override
    public Integer postAddressSave(MarketAddress address) {

        Subject subject = SecurityUtils.getSubject();
        MarketUser primaryPrincipal = (MarketUser) subject.getPrincipals().getPrimaryPrincipal();

        if (subject.getPrincipals() == null){
            subject.logout();
            return null;
        }

        // 需要把用户id一起填上
        address.setUserId(primaryPrincipal.getId());

        MarketAddress marketAddress = marketAddressMapper.selectByPrimaryKey(address.getId());

        MarketAddressExample addressExample = new MarketAddressExample();

        if (marketAddress == null){
            // 如果id不存在数据库中，就执行插入操作
            address.setAddTime(CurTimeUtil.getCurTime());
            address.setUpdateTime(CurTimeUtil.getCurTime());
            marketAddressMapper.insertSelective(address);

            // 如果以上的插入设为默认地址1，则就要把其他的设为非默认地址0
            if (address.getIsDefault() == true){

                addressExample.createCriteria().andIdNotEqualTo(address.getId());

                marketAddressMapper.updateByExampleSelective(new MarketAddress(false),addressExample);
            }

            return address.getId();
        }

        // 如果id存在在数据库中，就执行更新操作
        address.setUpdateTime(CurTimeUtil.getCurTime());
        marketAddressMapper.updateByPrimaryKeySelective(address);

        // 如果以上的插入设为默认地址1，则就要把其他的设为非默认地址0
        if (address.getIsDefault() == true){

            addressExample.createCriteria().andIdNotEqualTo(address.getId());

            marketAddressMapper.updateByExampleSelective(new MarketAddress(false),addressExample);
        }

        return address.getId();
    }

    /**
     * 逻辑上删除收货地址，把delete从0（false）标记为1(true)
     * @param id
     * @return void
     * @author Zah
     * @date 2022/07/19 21:15
     */
    @Override
    public void postAddressDelete(Integer id) {

        MarketAddressExample addressExample = new MarketAddressExample();

        addressExample.createCriteria().andIdEqualTo(id);

        marketAddressMapper.updateByExampleSelective(new MarketAddress(id,true),addressExample);

    }


}
