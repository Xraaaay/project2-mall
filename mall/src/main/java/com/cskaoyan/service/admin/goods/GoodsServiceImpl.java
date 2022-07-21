package com.cskaoyan.service.admin.goods;

import com.cskaoyan.bean.admin.goods.po.MarketGoodsPo;
import com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo;
import com.cskaoyan.bean.common.*;
import com.cskaoyan.bean.admin.goods.bo.Children;
import com.cskaoyan.bean.admin.goods.bo.CreateBo;
import com.cskaoyan.bean.admin.goods.bo.UpdateBo;
import com.cskaoyan.bean.admin.goods.vo.*;
import com.cskaoyan.mapper.common.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品信息模块
 *
 * @author pqk
 * @since 2022/07/16 21:14
 */
@Component
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    MarketGoodsMapper marketGoodsMapper;
    @Autowired
    MarketGoodsAttributeMapper marketGoodsAttributeMapper;
    @Autowired
    MarketGoodsSpecificationMapper marketGoodsSpecificationMapper;
    @Autowired
    MarketGoodsProductMapper marketGoodsProductMapper;
    @Autowired
    MarketCategoryMapper marketCategoryMapper;
    @Autowired
    MarketBrandMapper marketBrandMapper;

    @Override
    public CommonData list(Integer page, Integer limit, String sort, String order) {
        if (page != null || limit != 0) {
            PageHelper.startPage(page, limit);
        }

        MarketGoodsExample example = new MarketGoodsExample();
        MarketGoodsExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(sort + " " + order);
        // 根据条件查询到 商品列表集合
        List<MarketGoods> marketGoods = marketGoodsMapper.selectByExample(example);
        // 创建2级VO
        CommonData<MarketGoods> marketGoodsCommonData = new CommonData<>();

        PageInfo<MarketGoods> marketGoodsPageInfo = new PageInfo<>(marketGoods);

        marketGoodsCommonData.setTotal((int) marketGoodsPageInfo.getTotal());
        marketGoodsCommonData.setLimit(marketGoodsPageInfo.getPageSize());
        marketGoodsCommonData.setList(marketGoods);
        marketGoodsCommonData.setPage(marketGoodsPageInfo.getPageNum());
        marketGoodsCommonData.setPages(marketGoodsPageInfo.getPages());

        return marketGoodsCommonData;
    }

    /**
     * 商品列表
     *
     * @param param
     * @param marketGoods
     * @return com.cskaoyan.bean.admin.mallmanagement.IssueAndKeywordListVo
     * @author shn
     * @date 2022/7/20 17:37
     */
    @Override
    public IssueAndKeywordListVo list1(BaseParam param, MarketGoods marketGoods) {
        //分页
        if (param.getLimit() != 0) {
            PageHelper.startPage(param.getPage(), param.getLimit());
        }
        MarketGoodsExample goodsExample = new MarketGoodsExample();
        if (param.getOrder() != null && param.getSort() != null) {
            goodsExample.setOrderByClause(param.getSort() + " " + param.getOrder());
        }

        MarketGoodsExample.Criteria criteria = goodsExample.createCriteria();
        //条件查询
        if (marketGoods.getId() != null) {
            criteria.andIdEqualTo(marketGoods.getId());
        }
        if (marketGoods.getGoodsSn() != null) {
            criteria.andGoodsSnEqualTo(marketGoods.getGoodsSn());
        }
        if (marketGoods.getName() != null) {
            criteria.andNameLike("%" + marketGoods.getName() + "%");
        }
        //查找
        List<MarketGoods> marketGoodsList = marketGoodsMapper.selectByExample(goodsExample);
        PageInfo pageInfo = new PageInfo(marketGoodsList);
        IssueAndKeywordListVo listVo;
        if (param.getLimit() != 0) {
            listVo = IssueAndKeywordListVo.listVo(pageInfo.getTotal(), pageInfo.getPages(), param.getLimit(), param.getPage(), marketGoodsList);
        } else {
            listVo = IssueAndKeywordListVo.listVo(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getPageSize(), pageInfo.getSize(), marketGoodsList);
        }
        return listVo;
    }

    @Override
    public void delete(Integer id) {
        marketGoodsMapper.deleteByPrimaryKey(id);
        return;
    }

    @Override
    @Transactional
    public DetailVo detail(Integer id) {
        // 根据传入id查询到商品详情
        MarketGoods goods = marketGoodsMapper.selectByPrimaryKey(id);
        String[] galleryArr = goods.getGallery();

        /*String galleryString = goods.getGallery().replace("[", "").replace("]", "");
        String[] galleryArr = galleryString.split(",");*/

        for (int i = 0; i < galleryArr.length; i++) {
            String replaceAll = galleryArr[i].trim().replaceAll("\"", "");
            galleryArr[i] = replaceAll;
        }

        // 根据传入id查商品参数表
        MarketGoodsAttributeExample goodsAttributeExample = new MarketGoodsAttributeExample();
        MarketGoodsAttributeExample.Criteria goodsAttributeExampleCriteria =
                goodsAttributeExample.createCriteria().andGoodsIdEqualTo(id);
        List<MarketGoodsAttribute> attributes = marketGoodsAttributeMapper.selectByExample(goodsAttributeExample);
        // 根据传入id查商品规格表
        MarketGoodsSpecificationExample goodsSpecificationExample = new MarketGoodsSpecificationExample();
        MarketGoodsSpecificationExample.Criteria specificationExampleCriteria =
                goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(id);
        List<MarketGoodsSpecification> specifications =
                marketGoodsSpecificationMapper.selectByExample(goodsSpecificationExample);
        // 根据传入id查商品货品表
        MarketGoodsProductExample goodsProductExample = new MarketGoodsProductExample();
        MarketGoodsProductExample.Criteria productExampleCriteria =
                goodsProductExample.createCriteria().andGoodsIdEqualTo(id);
        List<MarketGoodsProduct> products = marketGoodsProductMapper.selectByExample(goodsProductExample);

        // 根据传入id返回商品类别 ,类别表id，商品分类id
        ArrayList<Integer> arrayList = new ArrayList<>();
        // 根据id返回父类pid
        Integer categoryId = goods.getCategoryId();
        MarketCategory marketCategory = marketCategoryMapper.selectByPrimaryKey(categoryId);

        arrayList.add(marketCategory.getPid());
        arrayList.add(goods.getCategoryId());

        MarketGoodsVo marketGoodsVO = new MarketGoodsVo(goods.getId(), goods.getGoodsSn(), goods.getName(),
                goods.getCategoryId(), goods.getBrandId(), galleryArr, goods.getKeywords(), goods.getBrief(),
                goods.getIsOnSale(), goods.getSortOrder(), goods.getPicUrl(), goods.getShareUrl(), goods.getIsNew(),
                goods.getIsHot(), goods.getUnit(), goods.getCounterPrice(), goods.getRetailPrice(), goods.getAddTime(),
                goods.getUpdateTime(), goods.getDeleted(), goods.getDetail());

        DetailVo detailVo = new DetailVo();
        detailVo.setCategoryIds(arrayList);
        detailVo.setAttributes(attributes);
        detailVo.setGoods(marketGoodsVO);
        detailVo.setProducts(products);
        detailVo.setSpecifications(specifications);

        return detailVo;
    }

    @Override
    public CatAndBrandVo catAndBrand() {

        MarketCategoryExample marketCategoryExample = new MarketCategoryExample();
        MarketCategoryExample.Criteria criteria = marketCategoryExample.createCriteria();
        criteria.andPidEqualTo(0);
        // 查询所有pid=0的父分类集合
        List<MarketCategory> pids = marketCategoryMapper.selectByExample(marketCategoryExample);
        // 查询所有分类集合 需要id
        List<MarketCategory> allMarketCategories = marketCategoryMapper.selectByExample(null);

        CatAndBrandVo catAndBrandVo = new CatAndBrandVo();

        ArrayList<CategoryListVo> categoryListVos = new ArrayList<>();

        // CategoryList
        for (MarketCategory pid : pids) {
            // 哪个子分类的pid=父类id 说明是children
            CategoryListVo categoryListVo = new CategoryListVo();
            ArrayList<Children> childrenList = new ArrayList<>();

            for (MarketCategory allMarketCategory : allMarketCategories) {
                if (pid.getId().equals(allMarketCategory.getPid())) {

                    categoryListVo.setLabel(pid.getName());
                    categoryListVo.setValue(pid.getId());

                    Children children = new Children();
                    children.setLabel(allMarketCategory.getName());
                    children.setValue(allMarketCategory.getId());

                    childrenList.add(children);

                }
            }
            categoryListVo.setChildren(childrenList);
            categoryListVos.add(categoryListVo);
        }
        catAndBrandVo.setCategoryList(categoryListVos);

        // BrandListVo
        List<MarketBrand> marketBrands = marketBrandMapper.selectByExample(null);
        ArrayList<BrandListVo> brandListVos = new ArrayList<>();
        for (MarketBrand marketBrand : marketBrands) {
            BrandListVo brandListVo = new BrandListVo();
            brandListVo.setVlaue(marketBrand.getId());
            brandListVo.setLabel(marketBrand.getName());
            brandListVos.add(brandListVo);
        }
        catAndBrandVo.setBrandList(brandListVos);

        return catAndBrandVo;
    }

    // 上架需要向4张表插入数据
    @Override
    @Transactional
    public void create(CreateBo createBo) {
        // 获得json中goods的数据
        // 向goods中插入上架数据
        MarketGoodsVo goods = createBo.getGoods();
        goods.setAddTime(new Date());

        // 向specification中插入数据
        List<MarketGoodsSpecification> specifications = createBo.getSpecifications();
        for (MarketGoodsSpecification specification : specifications) {
            specification.setGoodsId(goods.getId());
            specification.setAddTime(new Date());
            marketGoodsSpecificationMapper.insertSelective(specification);
        }

        // retail当前价格 使用规格中最低的插入数据库
        BigDecimal LowPrice = null;
        // 向products中插入数据
        List<MarketGoodsProduct> products = createBo.getProducts();
        for (MarketGoodsProduct product : products) {
            product.setAddTime(new Date());
            product.setId(null);
            product.setGoodsId(goods.getId());

            LowPrice = product.getPrice();
            if (LowPrice.compareTo(product.getPrice()) == -1) {
                LowPrice = product.getPrice();
            }

            marketGoodsProductMapper.insertSelective(product);
        }

        goods.setRetailPrice(LowPrice);
        marketGoodsMapper.insertSelectiveVo(goods);

        // 向attributes中插入数据
        List<MarketGoodsAttribute> attributes = createBo.getAttributes();
        for (MarketGoodsAttribute attribute : attributes) {
            attribute.setGoodsId(goods.getId());
            attribute.setAddTime(new Date());
            marketGoodsAttributeMapper.insertSelective(attribute);
        }

    }

    /**
     * 更新商品
     *
     * @param updateBo
     */
    @Override
    @Transactional
    public void update(UpdateBo updateBo) {

        MarketGoodsVo goods = updateBo.getGoods();
        marketGoodsMapper.updateByPrimaryKeySelectiveVo(goods);

        List<MarketGoodsProduct> products = updateBo.getProducts();
        for (MarketGoodsProduct product : products) {
            marketGoodsProductMapper.updateByPrimaryKeySelective(product);
        }

        List<MarketGoodsAttribute> attributes = updateBo.getAttributes();
        for (MarketGoodsAttribute attribute : attributes) {
            marketGoodsAttributeMapper.updateByPrimaryKeySelective(attribute);
        }

        List<MarketGoodsSpecification> specifications = updateBo.getSpecifications();
        for (MarketGoodsSpecification specification : specifications) {
            marketGoodsSpecificationMapper.updateByPrimaryKeySelective(specification);
        }

    }


}
