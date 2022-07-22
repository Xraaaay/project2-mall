package com.cskaoyan.service.wx.goods.goodsdetail;

import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.goods.detail.goodsdetail.Comment;
import com.cskaoyan.bean.wx.goods.detail.goodsdetail.GoodsDetailVO;
import com.cskaoyan.bean.wx.goods.detail.goodsdetail.MarketCommentVo;
import com.cskaoyan.bean.wx.goods.detail.goodsdetail.Specification;
import com.cskaoyan.mapper.common.*;
import com.cskaoyan.service.wx.auth.AccountServiceImpl;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * @author fanxing056
 * @date 2022/07/21 09:46
 */

@Service
public class GoodsDetailWXServiceImpl implements GoodsDetailWXService {

    @Autowired
    MarketGoodsSpecificationMapper goodsSpecificationMapper;

    @Autowired
    MarketCommentMapper commentMapper;

    @Autowired
    MarketUserMapper userMapper;

    @Autowired
    MarketGrouponRulesMapper grouponRulesMapper;

    @Autowired
    MarketIssueMapper issueMapper;

    @Autowired
    MarketGoodsAttributeMapper goodsAttributeMapper;

    @Autowired
    MarketGoodsProductMapper goodsProductMapper;

    @Autowired
    MarketGoodsMapper goodsMapper;

    @Autowired
    MarketBrandMapper brandMapper;

    @Autowired
    MarketCollectMapper collectMapper;

    @Autowired
    MarketFootprintMapper footprintMapper;

    @Override
    public GoodsDetailVO detail(Integer goodsId) {
        // attribute
        MarketGoodsAttributeExample marketGoodsAttributeExample = new MarketGoodsAttributeExample();
        marketGoodsAttributeExample.createCriteria().andDeletedEqualTo(false).andGoodsIdEqualTo(goodsId);
        List<MarketGoodsAttribute> attribute = goodsAttributeMapper.selectByExample(marketGoodsAttributeExample);

        //comment
        // 根据商品id查询评论
        // 分页
        PageHelper.startPage(1, 2, "add_time desc");
        Comment comment = new Comment();
        List<MarketCommentVo> marketCommentVos = new ArrayList<>();
        MarketCommentExample marketCommentExample = new MarketCommentExample();
        marketCommentExample.createCriteria().
                andDeletedEqualTo(false).
                andValueIdEqualTo(goodsId).
                andTypeEqualTo((byte) 0);
        List<MarketComment> commentList = commentMapper.selectByExample(marketCommentExample);
        // 封装MarketCommentVO
        if (commentList.size() > 0) {
            for (MarketComment m : commentList) {
                // 查询用户昵称
                String username = userMapper.selectByPrimaryKey(m.getUserId()).getUsername();
                MarketCommentVo marketCommentVo = new MarketCommentVo();
                marketCommentVo.setAddTime(m.getAddTime());
                marketCommentVo.setAdminContent(m.getAdminContent());
                marketCommentVo.setAvatar(AccountServiceImpl.AVATAR);
                marketCommentVo.setContent(m.getContent());
                marketCommentVo.setId(m.getId());
                marketCommentVo.setNickname(username);
                marketCommentVo.setPicList(m.getPicUrls());
                marketCommentVos.add(marketCommentVo);
            }
        }
        MarketCommentExample example = new MarketCommentExample();
        example.createCriteria().andValueIdEqualTo(goodsId);
        long count = commentMapper.countByExample(example);
        comment.setData(marketCommentVos);
        comment.setCount((int) count);

        // groupon
        MarketGrouponRulesExample marketGrouponRulesExample = new MarketGrouponRulesExample();
        marketGrouponRulesExample.createCriteria().andDeletedEqualTo(false).andGoodsIdEqualTo(goodsId);
        List<MarketGrouponRules> groupon = grouponRulesMapper.selectByExample(marketGrouponRulesExample);

        // info
        MarketGoodsExample infoExample = new MarketGoodsExample();
        infoExample.createCriteria().andDeletedEqualTo(false).andIdEqualTo(goodsId);
        MarketGoods info = goodsMapper.selectByExample(infoExample).get(0);

        // issue
        List<MarketIssue> issue = issueMapper.selectByExample(null);

        // productList
        MarketGoodsProductExample marketGoodsProductExample = new MarketGoodsProductExample();
        marketGoodsProductExample.createCriteria().andDeletedEqualTo(false).andGoodsIdEqualTo(goodsId);
        List<MarketGoodsProduct> productList = goodsProductMapper.selectByExample(marketGoodsProductExample);

        // share
        boolean share = true;
        // shareImage
        String shareImage = "";

        //specificationList
        // 根据goodsId查出所有规格
        List<Specification> specificationList = new ArrayList<>();
        MarketGoodsSpecificationExample marketGoodsSpecificationExample = new MarketGoodsSpecificationExample();
        marketGoodsSpecificationExample.createCriteria().andDeletedEqualTo(false).andGoodsIdEqualTo(goodsId);
        List<MarketGoodsSpecification> specifications = goodsSpecificationMapper.selectByExample(marketGoodsSpecificationExample);
        // 去重
        HashSet<String> names = new HashSet<>();
        List<MarketGoodsSpecification> marketGoodsSpecifications = new ArrayList<>();
        for (MarketGoodsSpecification specification : specifications) {
            if (!names.contains(specification.getSpecification())) {
                names.add(specification.getSpecification());
                marketGoodsSpecifications.add(specification);
            }
        }
        // 封装specificationList
        for (MarketGoodsSpecification specification : marketGoodsSpecifications) {
            Specification specification1 = new Specification();
            String name = specification.getSpecification();
            specification1.setName(name);
            MarketGoodsSpecificationExample goodsSpecificationExample = new MarketGoodsSpecificationExample();
            goodsSpecificationExample.createCriteria().andSpecificationEqualTo(name).andGoodsIdEqualTo(goodsId);
            specification1.setValueList(goodsSpecificationMapper.selectByExample(goodsSpecificationExample));
            specificationList.add(specification1);
        }

        // brand
        MarketBrand brand = brandMapper.selectByPrimaryKey(info.getBrandId());

        // userHasCollect
        int userHasCollect = 0;
        Subject subject = SecurityUtils.getSubject();
        MarketUser user = null;
        PrincipalCollection principals = subject.getPrincipals();
        if (principals != null) {
            user = (MarketUser) principals.getPrimaryPrincipal();
            // 加入足迹
            // 判断是否已经存在该足迹
            MarketFootprintExample footprintExample = new MarketFootprintExample();
            MarketFootprintExample.Criteria criteria = footprintExample.createCriteria();
            criteria.andDeletedEqualTo(false).andGoodsIdEqualTo(goodsId);
            List<MarketFootprint> marketFootprintList = footprintMapper.selectByExample(footprintExample);
            MarketFootprint marketFootprint = new MarketFootprint(null, user.getId(), goodsId, null, new Date(), false);
            if (marketFootprintList.size() == 0) {
                marketFootprint.setAddTime(new Date());
                footprintMapper.insertSelective(marketFootprint);
            } else {
                criteria.andUserIdEqualTo(user.getId());
                footprintMapper.updateByExampleSelective(marketFootprint, footprintExample);
            }

            // userHasCollect
            MarketCollectExample marketCollectExample = new MarketCollectExample();
            marketCollectExample.createCriteria().
                    andDeletedEqualTo(false).
                    andUserIdEqualTo(user.getId()).
                    andValueIdEqualTo(goodsId).
                    andTypeEqualTo((byte) 0);
            List<MarketCollect> marketCollects = collectMapper.selectByExample(marketCollectExample);
            if (marketCollects.size() > 0) {
                userHasCollect = 1;
            }
        }

        GoodsDetailVO vo = new GoodsDetailVO(specificationList, groupon, issue, userHasCollect, shareImage,
                comment, share, attribute, brand, productList, info);
        return vo;
    }
}
