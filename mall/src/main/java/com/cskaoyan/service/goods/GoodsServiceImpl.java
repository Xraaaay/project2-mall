package com.cskaoyan.service.goods;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.common.CommonData;
import com.cskaoyan.bean.goodsVo.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.error.Mark;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品信息模块
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
        PageHelper.startPage(page,limit);
        MarketGoodsExample example = new MarketGoodsExample();
        MarketGoodsExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(sort + " " + order);
        //根据条件查询到 商品列表集合
        List<MarketGoods> marketGoods = marketGoodsMapper.selectByExample(example);
        //创建2级VO
        CommonData<MarketGoods> marketGoodsCommonData = new CommonData<>();

        PageInfo<MarketGoods> marketGoodsPageInfo = new PageInfo<>(marketGoods);

        marketGoodsCommonData.setTotal(marketGoodsPageInfo.getSize());
        marketGoodsCommonData.setLimit(marketGoodsPageInfo.getPageSize());
        marketGoodsCommonData.setList(marketGoods);
        marketGoodsCommonData.setPage(marketGoodsPageInfo.getPageNum());
        marketGoodsCommonData.setPages(marketGoodsPageInfo.getPages());

        return marketGoodsCommonData;
    }

    @Override
    public void delete(Integer id) {
        marketGoodsMapper.deleteByPrimaryKey(id);
        return;
    }

    @Override
    @Transactional
    public DetailVo detail(Integer id) {
        //根据传入id查询到商品详情
        MarketGoods goods = marketGoodsMapper.selectByPrimaryKey(id);
        //根据传入id查商品参数表
        MarketGoodsAttributeExample goodsAttributeExample = new MarketGoodsAttributeExample();
        MarketGoodsAttributeExample.Criteria goodsAttributeExampleCriteria = goodsAttributeExample.createCriteria().andGoodsIdEqualTo(id);
        List<MarketGoodsAttribute> attributes = marketGoodsAttributeMapper.selectByExample(goodsAttributeExample);
        //根据传入id查商品规格表
        MarketGoodsSpecificationExample goodsSpecificationExample = new MarketGoodsSpecificationExample();
        MarketGoodsSpecificationExample.Criteria specificationExampleCriteria = goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(id);
        List<MarketGoodsSpecification> specifications = marketGoodsSpecificationMapper.selectByExample(goodsSpecificationExample);
        //根据传入id查商品货品表
        MarketGoodsProductExample goodsProductExample = new MarketGoodsProductExample();
        MarketGoodsProductExample.Criteria productExampleCriteria = goodsProductExample.createCriteria().andGoodsIdEqualTo(id);
        List<MarketGoodsProduct> products = marketGoodsProductMapper.selectByExample(goodsProductExample);

        //根据传入id返回商品类别 ,类别表id，商品分类id
        ArrayList<Integer> arrayList = new ArrayList<>();
        //根据id返回父类pid
        Integer categoryId = goods.getCategoryId();
        MarketCategory marketCategory = marketCategoryMapper.selectByPrimaryKey(categoryId);

        arrayList.add(marketCategory.getPid());
        arrayList.add(goods.getCategoryId());

        DetailVo detailVo = new DetailVo();
        detailVo.setCategoryIds(arrayList);
        detailVo.setAttributes(attributes);
        detailVo.setGoods(goods);
        detailVo.setProducts(products);
        detailVo.setSpecifications(specifications);

        return detailVo;
    }

    @Override
    public CatAndBrandVo catAndBrand() {

        MarketCategoryExample marketCategoryExample = new MarketCategoryExample();
        MarketCategoryExample.Criteria criteria = marketCategoryExample.createCriteria();
        criteria.andPidEqualTo(0);
        //查询所有pid=0的父分类集合
        List<MarketCategory> pids = marketCategoryMapper.selectByExample(marketCategoryExample);
        //查询所有分类集合 需要id
        List<MarketCategory> allMarketCategories = marketCategoryMapper.selectByExample(null);

        CatAndBrandVo catAndBrandVo = new CatAndBrandVo();

        ArrayList<CategoryListVo> categoryListVos = new ArrayList<>();
        //CategoryList
        for (MarketCategory pid : pids) {
            //哪个子分类的pid=父类id 说明是children
            for (MarketCategory allMarketCategory : allMarketCategories) {
                if (pid.getId().equals(allMarketCategory.getPid())){


                    CategoryListVo categoryListVo = new CategoryListVo();

                    categoryListVo.setLabel(pid.getName());
                    categoryListVo.setValue(pid.getId());

                    Children children = new Children();
                    children.setLabel(allMarketCategory.getName());
                    children.setValue(allMarketCategory.getId());

                    ArrayList<Children> childrenList = new ArrayList<>();
                    childrenList.add(children);

                    categoryListVo.setChildren(childrenList);
                    categoryListVos.add(categoryListVo);
                }
            }
            catAndBrandVo.setCategoryList(categoryListVos);
        }
        //BrandListVo
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
}
