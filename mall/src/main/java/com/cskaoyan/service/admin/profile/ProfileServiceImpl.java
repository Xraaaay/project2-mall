package com.cskaoyan.service.admin.profile;

import com.cskaoyan.bean.admin.profile.PasswordVo;
import com.cskaoyan.bean.admin.system.MarketAdmin;
import com.cskaoyan.bean.admin.system.MarketAdminExample;
import com.cskaoyan.bean.admin.usermanagement.UserListVo;
import com.cskaoyan.bean.common.BaseParam;
import com.cskaoyan.bean.common.MarketNotice;
import com.cskaoyan.bean.common.MarketNoticeAdmin;
import com.cskaoyan.bean.common.MarketNoticeAdminExample;
import com.cskaoyan.mapper.common.MarketNoticeAdminMapper;
import com.cskaoyan.mapper.common.MarketNoticeMapper;
import com.cskaoyan.mapper.system.MarketAdminMapper;
import com.cskaoyan.util.Md5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台通知中心
 *
 * @author Zah
 * @date 2022/07/20 20:06
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    MarketNoticeAdminMapper marketNoticeAdminMapper;

    @Autowired
    MarketNoticeMapper marketNoticeMapper;

    @Autowired
    MarketAdminMapper marketAdminMapper;

    /**
     * 根据title做模糊查询，根据type:(unread、read、all)
     *
     * @param page
     * @param title
     * @param type
     * @return com.cskaoyan.bean.admin.usermanagement.UserListVo
     * @author Zah
     * @date 2022/07/20 21:19
     */
    @Override
    public UserListVo isNotice(BaseParam page, String title, String type) {

        PageHelper.startPage(page.getPage(), page.getLimit());

        Subject subject = SecurityUtils.getSubject();

        if (subject.getPrincipals() == null) {
            subject.logout();
            return null;
        }

        MarketAdmin principal = (MarketAdmin) subject.getPrincipal();

        MarketNoticeAdminExample noticeAdminExample = new MarketNoticeAdminExample();

        MarketNoticeAdminExample.Criteria criteria = noticeAdminExample.createCriteria();
        criteria.andAdminIdEqualTo(principal.getId());

        // 无论是什么通知状态的信息，都可以做关于title的模糊查询
        if (title != null && !"".equals(title)) {
            criteria.andNoticeTitleLike("%" + title + "%");
        }

        if ("unread".equals(type)) {
            // 返回未读信息=readTime:null
            criteria.andReadTimeIsNull();
        } else if ("read".equals(type)) {
            // 返回已读信息=readtime:有时间
            criteria.andReadTimeIsNotNull();
        }

        // 返回全部信息
        List<MarketNoticeAdmin> marketNoticeAdmins = marketNoticeAdminMapper.selectByExample(noticeAdminExample);

        PageInfo<MarketNoticeAdmin> marketNoticeAdminPageInfo = new PageInfo<>(marketNoticeAdmins);

        UserListVo<MarketNoticeAdmin> marketNoticeAdminUserListVo = new UserListVo<>(marketNoticeAdminPageInfo.getTotal(), marketNoticeAdminPageInfo.getPages(),
                page.getLimit(), page.getPage(), marketNoticeAdmins);

        return marketNoticeAdminUserListVo;
    }

    /**
     * 修改后台系统管理员密码
     * @param passwords
     * @return void
     * @author Zah
     * @date 2022/07/21 14:52
     */
    @Override
    public void fixPassword(PasswordVo passwords) {

        Subject subject = SecurityUtils.getSubject();
        MarketAdmin principal = (MarketAdmin) subject.getPrincipal();

        // 对新旧密码进行md5的加密
        String newPassword = null;
        String oldPassword = null;
        try {

            newPassword = Md5Utils.getMd5(passwords.getNewPassword());
            oldPassword = Md5Utils.getMd5(passwords.getOldPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 对旧密码进行判断查看是否为当前管理员的旧密码
        MarketAdminExample adminExample = new MarketAdminExample();
        adminExample.createCriteria().andPasswordEqualTo(oldPassword);
        List<MarketAdmin> marketAdmins = marketAdminMapper.selectByExample(adminExample);

        // 如果marketAdmins为空则表示不是当前管理员的旧密码
        if (marketAdmins.size() == 0) {
           throw new RuntimeException();
        }


        MarketAdmin marketAdmin = new MarketAdmin();
        marketAdmin.setId(principal.getId());
        marketAdmin.setPassword(newPassword);

        // 将新密码更新到数据库中
        marketAdminMapper.updateByPrimaryKeySelective(marketAdmin);
    }
}
