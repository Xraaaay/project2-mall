package com.cskaoyan.service.wx.goods;


import com.cskaoyan.bean.admin.goods.vo.MarketGoodsVo;
import com.cskaoyan.bean.admin.system.MarketAdmin;
import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.wx.goods.*;
import com.cskaoyan.bean.wx.goods.detail.CommentVo;
import com.cskaoyan.bean.wx.goods.detail.DetailWxVo;
import com.cskaoyan.bean.wx.goods.detail.SpecificationList;
import com.cskaoyan.mapper.common.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author pqk
 * @since 2022/07/19 20:14
 */
@Component
public class GoodsWxServiceImpl implements GoodsWxService {
    @Autowired
    MarketCategoryMapper marketCategoryMapper;
    @Autowired
    MarketGoodsMapper marketGoodsMapper;
    @Autowired
    MarketGoodsSpecificationMapper marketGoodsSpecificationMapper;
    @Autowired
    MarketIssueMapper marketIssueMapper;
    @Autowired
    MarketGoodsProductMapper marketGoodsProductMapper;
    @Autowired
    MarketCommentMapper marketCommentMapper;
    @Autowired
    MarketGoodsAttributeMapper marketGoodsAttributeMapper;
    @Autowired
    MarketBrandMapper marketBrandMapper;
    @Autowired
    MarketCollectMapper marketCollectMapper;
    @Autowired
    MarketFootprintMapper marketFootprintMapper;
    @Autowired
    MarketSearchHistoryMapper marketSearchHistoryMapper;


    @Override
    public CategoryWxVo category(String id) {
        // 判断类目级别
        MarketCategory marketCategory = marketCategoryMapper.selectByPrimaryKey(Integer.parseInt(id));
        Integer pid = marketCategory.getPid();
        Integer cid = null;
        // l1类目
        if (pid == 0) {
            MarketCategoryExample categoryExample = new MarketCategoryExample();
            categoryExample.createCriteria().andDeletedEqualTo(false).andPidEqualTo(Integer.parseInt(id));
            List<MarketCategory> marketCategories = marketCategoryMapper.selectByExample(categoryExample);
            MarketCategory category = marketCategories.get(0);
            if (category != null) {
                cid = category.getId();
            }
            return categoryl(cid);
        } else {
            // l2类目
            return categoryl(Integer.parseInt(id));
        }
    }
    private CategoryWxVo categoryl(Integer cid) {
        MarketCategory currentCategory = marketCategoryMapper.selectByPrimaryKey(cid);

        Integer pid = currentCategory.getPid();

        MarketCategoryExample marketCategoryExample = new MarketCategoryExample();
        MarketCategoryExample.Criteria criteria = marketCategoryExample.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MarketCategory> brotherCategory = marketCategoryMapper.selectByExample(marketCategoryExample);

        MarketCategory parentCategory = marketCategoryMapper.selectByPrimaryKey(pid);

        CategoryWxVo categoryWxVo = new CategoryWxVo(currentCategory, parentCategory, brotherCategory);

        return categoryWxVo;
    }

    @Override
    @Transactional
    public PageInfoDataVo list(ListWxBo listWxBo, String keyword, String sort, String order,Integer brandId,Boolean isNew,Boolean isHot) {
        //如果没有关键词输入就不加入
        if (keyword!=null){
            //将用户输入关键词加入历史表中
            Subject subject = SecurityUtils.getSubject();
            PrincipalCollection principals = subject.getPrincipals();
            if (principals!=null){

                // 判断是否已经搜索过当前keyword
                MarketSearchHistoryExample searchHistoryExample = new MarketSearchHistoryExample();
                searchHistoryExample.createCriteria().andDeletedEqualTo(false).andKeywordEqualTo(keyword);
                List<MarketSearchHistory> marketSearchHistories = marketSearchHistoryMapper.selectByExample(searchHistoryExample);
                MarketUser primaryPrincipal = (MarketUser) principals.getPrimaryPrincipal();
                MarketSearchHistory marketSearchHistory = new MarketSearchHistory();
                marketSearchHistory.setFrom("wx");
                marketSearchHistory.setKeyword(keyword);
                marketSearchHistory.setUserId(primaryPrincipal.getId());
                if (marketSearchHistories.size() == 0) {
                    marketSearchHistory.setAddTime(new Date());
                    marketSearchHistory.setUpdateTime(new Date());
                    marketSearchHistoryMapper.insertSelective(marketSearchHistory);
                } else {
                    marketSearchHistory.setUpdateTime(new Date());
                    marketSearchHistory.setId(marketSearchHistories.get(0).getId());
                    marketSearchHistoryMapper.updateByPrimaryKeySelective(marketSearchHistory);
                }
            }
        }


        Integer categoryId = listWxBo.getCategoryId();
        Integer limit = listWxBo.getLimit();
        Integer page = listWxBo.getPage();


        //根据类目id找到属于这个品类下商品
        MarketGoodsExample marketGoodsExample = new MarketGoodsExample();
        MarketGoodsExample.Criteria criteria = marketGoodsExample.createCriteria();

        if (isHot!=null){
            criteria.andIsHotEqualTo(isHot);
        }
        if (isNew!=null){
            criteria.andIsNewEqualTo(isNew);
        }
        if (sort!=null&&order!=null){
            marketGoodsExample.setOrderByClause(sort+" "+order);
        }
        if (categoryId!=null && categoryId !=0){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if (brandId!=null){
            criteria.andBrandIdEqualTo(brandId);
        }
        if (keyword!=null){
            criteria.andNameLike("%"+keyword+"%");
        }
        PageHelper.startPage(page,limit);
        List<MarketGoods> list = marketGoodsMapper.selectByExample(marketGoodsExample);


        //二级类目
        MarketCategoryExample marketCategoryExample = new MarketCategoryExample();
        MarketCategoryExample.Criteria marketCategoryExampleCriteria = marketCategoryExample.createCriteria();
        marketCategoryExampleCriteria.andLevelEqualTo("L2");
        List<MarketCategory> filterCategoryList = marketCategoryMapper.selectByExample(marketCategoryExample);


        PageInfo<MarketGoods> PageInfo = new PageInfo<>(list);

        //封装
        PageInfoDataVo pageInfoDataVo = new PageInfoDataVo(PageInfo.getTotal(),PageInfo.getPages(),PageInfo.getSize(),
                PageInfo.getPageNum(),list,filterCategoryList);

        return pageInfoDataVo;
    }

    @Override
    public Long count() {
        long count = marketGoodsMapper.countByExample(null);
        return count;
    }

    @Override
    public CommonData related(int i) {
        MarketGoods marketGoods = marketGoodsMapper.selectByPrimaryKey(i);
        Integer categoryId = marketGoods.getCategoryId();
        MarketGoodsExample marketGoodsExample = new MarketGoodsExample();
        MarketGoodsExample.Criteria criteria = marketGoodsExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        PageHelper.startPage(1,6);
        List<MarketGoodsVo> marketGoodsVos = marketGoodsMapper.selectByExampleVo(marketGoodsExample);
        PageInfo<MarketGoodsVo> marketGoodsVoPageInfo = new PageInfo<>(marketGoodsVos);

        //相关商品 显示
        CommonData<MarketGoodsVo> commonData = new CommonData<>();
        commonData.setTotal((int) marketGoodsVoPageInfo.getTotal());
        commonData.setPages(marketGoodsVoPageInfo.getPages());
        commonData.setPage(marketGoodsVoPageInfo.getPageNum());
        commonData.setLimit(6);
        commonData.setList(marketGoodsVoPageInfo.getList());

        return commonData;
    }

    @Override
    @Transactional
    public DetailWxVo detail(int i) {
        DetailWxVo detailWxVo = new DetailWxVo();

        //获取用户id
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        if (principals != null) {
            MarketUser primaryPrincipal = (MarketUser) principals.getPrimaryPrincipal();
            Integer userId = primaryPrincipal.getId();
            //加入足迹
            MarketFootprint marketFootprint = new MarketFootprint(null, userId, i, new Date(), null, false);
            marketFootprintMapper.insertSelective(marketFootprint);

            //userHasCollect
            MarketCollectExample marketCollectExample = new MarketCollectExample();
            MarketCollectExample.Criteria collectExampleCriteria = marketCollectExample.createCriteria();
            collectExampleCriteria.andValueIdEqualTo(i);
            collectExampleCriteria.andUserIdEqualTo(userId);

            List<MarketCollect> marketCollects = marketCollectMapper.selectByExample(marketCollectExample);
            if (marketCollects.size()==1){
                detailWxVo.setUserHasCollect(true);
            }
        }

        //specificationList → valueList → specification
        MarketGoodsSpecificationExample marketGoodsSpecificationExample = new MarketGoodsSpecificationExample();
        MarketGoodsSpecificationExample.Criteria specificationExampleCriteria = marketGoodsSpecificationExample.createCriteria();
        specificationExampleCriteria.andGoodsIdEqualTo(i);
        //根据商品id查询所有 规格表Specification
        List<MarketGoodsSpecification> marketGoodsSpecifications = marketGoodsSpecificationMapper.selectByExample(marketGoodsSpecificationExample);


        //根据条件查询所有name
        HashMap<String, String> map = new HashMap<>();
        map.put("spec","规格");
        map.put("colu","颜色");

        ArrayList<SpecificationList> midLists = new ArrayList<>(); //两类

        ArrayList<MarketGoodsSpecification> smallLists = new ArrayList<>(); //一类的数量

            //根据goodsid搜索全部规格
            for (MarketGoodsSpecification marketGoodsSpecification : marketGoodsSpecifications) {
                SpecificationList midSinglespecificationspec =null;
                //符合规格的
                if (marketGoodsSpecification.getSpecification().equals(map.get("spec"))){
                    //单个SpecificationList 只装 规格
                    midSinglespecificationspec = new SpecificationList();

                    smallLists.add(marketGoodsSpecification);
                    midSinglespecificationspec.setValueList(smallLists);
                    midSinglespecificationspec.setName(marketGoodsSpecification.getSpecification());

                }
                midLists.add(midSinglespecificationspec);

                SpecificationList midSinglespecificationcolu =null;
                //符合颜色的 单个SpecificationList 只装 颜色
                if (marketGoodsSpecification.getSpecification().equals(map.get("colu"))){

                    midSinglespecificationcolu = new SpecificationList();

                    smallLists.add(marketGoodsSpecification);
                    midSinglespecificationcolu.setValueList(smallLists);
                    midSinglespecificationcolu.setName(marketGoodsSpecification.getSpecification());
                }
                midLists.add(midSinglespecificationcolu);
            }
            detailWxVo.setSpecificationList(midLists);



        //groupon 默认null

        //issue
        List<MarketIssue> marketIssues = marketIssueMapper.selectByExample(null);
        detailWxVo.setIssue(marketIssues);


        //shareImage

        //comment
        MarketCommentExample marketCommentExample = new MarketCommentExample();
        MarketCommentExample.Criteria marketCommentExampleCriteria = marketCommentExample.createCriteria();
        marketCommentExampleCriteria.andValueIdEqualTo(i);
        List<MarketComment> marketComments = marketCommentMapper.selectByExample(marketCommentExample);
        CommentVo commentVo = new CommentVo();
        commentVo.setData(marketComments);
        commentVo.setCount(marketComments.size());
        detailWxVo.setComment(commentVo);

        //share

        //attribute
        MarketGoodsAttributeExample marketGoodsAttributeExample = new MarketGoodsAttributeExample();
        MarketGoodsAttributeExample.Criteria attributeExampleCriteria = marketGoodsAttributeExample.createCriteria();
        attributeExampleCriteria.andGoodsIdEqualTo(i);
        List<MarketGoodsAttribute> marketGoodsAttributes = marketGoodsAttributeMapper.selectByExample(marketGoodsAttributeExample);
        detailWxVo.setAttribute(marketGoodsAttributes);

        //brand
        MarketGoodsExample marketGoodsExample = new MarketGoodsExample();
        MarketGoodsExample.Criteria goodsExampleCriteria = marketGoodsExample.createCriteria();
        String id = String.valueOf(i);
        goodsExampleCriteria.andGoodsSnEqualTo(id);
        List<MarketGoodsVo> marketGoodsVos = marketGoodsMapper.selectByExampleVo(marketGoodsExample);
        Integer brandId = marketGoodsVos.get(0).getBrandId();
        MarketBrand marketBrand = marketBrandMapper.selectByPrimaryKey(brandId);
        detailWxVo.setBrand(marketBrand);

        //info
        MarketGoodsExample goodsExample = new MarketGoodsExample();
        MarketGoodsExample.Criteria goodsExampleCriteria1 = goodsExample.createCriteria();
        goodsExampleCriteria1.andIdEqualTo(i);
        List<MarketGoodsVo> marketGoodsVos1 = marketGoodsMapper.selectByExampleVo(goodsExample);
        for (MarketGoodsVo marketGoodsVo : marketGoodsVos1) {
            detailWxVo.setInfo(marketGoodsVo);
        }

        return detailWxVo;
    }
}
