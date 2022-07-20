package com.cskaoyan.service.wx.auth;

import com.aliyuncs.CommonResponse;
import com.cskaoyan.bean.common.MarketUser;
import com.cskaoyan.bean.common.MarketUserExample;
import com.cskaoyan.config.aliyun.AliyunComponent;
import com.cskaoyan.exception.InvalidDataException;
import com.cskaoyan.mapper.common.MarketUserMapper;
import com.cskaoyan.util.Md5Utils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用户注册与账号密码重置
 * @date 2022/07/19 20:30
 * @author fanxing056
 */

@Service
public class AccountServiceImpl implements AccountService {

    private static final int REG_CAPTCHA_DELAY = 5;
    private static final int REG_CAPTCHA_YET = 5;
    private static final String AVATAR = "https://cskaoyan.oss-cn-beijing.aliyuncs.com/8ad9d831136c4166b182e4cc74df70bd.jpg";
    private static final String AVATAR_SPEC = "?imageView&quality=90&thumbnail=64x64";

    @Autowired
    AliyunComponent aliyunComponent;

    @Autowired
    MarketUserMapper userMapper;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public int regCaptcha(String mobile) {

        // 首先判断当前手机号在redis中是否有验证码
        RBucket<Object> bucket = redissonClient.getBucket(mobile);
        String regCaptcha = (String) bucket.get();
        if (regCaptcha != null) {
            return REG_CAPTCHA_YET;
        }

        // 生成六位验证码并发送
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        CommonResponse commonResponse = aliyunComponent.sendMsg(mobile, code + "");
        if (commonResponse.getHttpStatus() == 200) {
            // 将手机号绑定验证码存入redis数据库
            // 验证码作为value, 保存时间REG_CAPTCHA_DELAY
            bucket.set(code + "", REG_CAPTCHA_DELAY, TimeUnit.MINUTES);
        }

        return commonResponse.getHttpStatus();
    }

    @Transactional
    @Override
    public int register(Map<String, String> map) throws Exception {

        // 判断验证码是否正确
        RBucket<Object> bucket = redissonClient.getBucket(map.get("mobile"));
        String regCaptcha = (String) bucket.get();
        if (regCaptcha == null) {
            // 验证码失效
            throw new InvalidDataException("验证码失效");
        }
        if (!regCaptcha.equals(map.get("code"))) {
            // 验证码错误
            throw new InvalidDataException("验证码错误");
        }

        // 判断当前手机号是否已经注册过
        MarketUserExample userExample = new MarketUserExample();
        MarketUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andDeletedEqualTo(false).andMobileEqualTo(map.get("mobile"));
        List<MarketUser> userList = userMapper.selectByExample(userExample);

        // 已经注册过
        if (userList.size() > 0) {
            throw new InvalidDataException("该手机号已经注册过");
        }

        // 注册
        MarketUser user = new MarketUser();
        user.setUsername(map.get("username"));
        user.setPassword(Md5Utils.getMd5(map.get("password")));
        user.setGender((byte) 0);
        user.setLastLoginTime(new Date());

        // 通过request获取ip
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String ip = request.getRemoteHost();

        user.setLastLoginIp(ip);
        user.setUserLevel((byte) 0);
        user.setNickname(map.get("username"));
        user.setMobile(map.get("mobile"));
        user.setAvatar(AVATAR + AVATAR_SPEC);
        user.setWeixinOpenid(map.get("wxCode"));
        user.setStatus((byte) 0);
        user.setAddTime(new Date());
        user.setUpdateTime(new Date());
        user.setDeleted(false);
        int affect = userMapper.insertSelective(user);

        // 销毁验证码
        bucket.set(null);
        return 0;
    }

    @Override
    public int reset(Map<String, String> map) throws Exception {

        // 判断验证码是否正确
        RBucket<Object> bucket = redissonClient.getBucket(map.get("mobile"));
        String regCaptcha = (String) bucket.get();
        if (regCaptcha == null) {
            // 验证码失效
            throw new InvalidDataException("验证码失效");
        }
        if (!regCaptcha.equals(map.get("code"))) {
            // 验证码错误
            throw new InvalidDataException("验证码错误");
        }

        // 更改密码
        MarketUserExample userExample = new MarketUserExample();
        MarketUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andDeletedEqualTo(false).andMobileEqualTo(map.get("mobile"));

        MarketUser user = new MarketUser();
        user.setMobile(map.get("mobile"));
        user.setPassword(Md5Utils.getMd5(map.get("password")));
        int affect = userMapper.updateByExampleSelective(user, userExample);

        // 销毁验证码
        bucket.set(null);
        return affect;
    }
}
