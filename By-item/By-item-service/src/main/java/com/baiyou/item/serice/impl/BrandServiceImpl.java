package com.baiyou.item.serice.impl;

import com.baiyou.common.pojo.PageResult;
import com.baiyou.item.dao.BrandMapper;
import com.baiyou.item.pojo.Brand;
import com.baiyou.item.pojo.Category;
import com.baiyou.item.serice.BrandService;
import com.baiyou.parameter.pojo.BrandQueryByPageParameter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author shkstart
 * @date 2019/9/28 - 20:06
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页查询
     *
     * @param brandQueryByPageParameter
     * @return
     */
    @Override
    public PageResult<Brand> queryBrandByPage(BrandQueryByPageParameter brandQueryByPageParameter) {
        /**
         * 分页
         */
        PageHelper.startPage(brandQueryByPageParameter.getPage(), brandQueryByPageParameter.getRows());
        /**
         *  2.排序
         */
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(brandQueryByPageParameter.getSortBy())) {
            example.setOrderByClause(brandQueryByPageParameter.getSortBy() + (brandQueryByPageParameter.getDesc() ? "DESC" : "ASC"));
        }
        /**
         * 查询
         */
        if (StringUtils.isNotBlank(brandQueryByPageParameter.getKey())) {
            example.createCriteria().orLike("name", brandQueryByPageParameter.getKey() + "%").
                    andEqualTo("letter", brandQueryByPageParameter.getKey().toUpperCase());
        }
        List<Brand> list = brandMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        /**
         * 5.返回分页结果
         */
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(),pageInfo.getList());
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param categories
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBrand(Brand brand, List<Long> categories) {
        //新增品牌信息
        this.brandMapper.insertSelective(brand);
        //新增品牌分类和中间表
        for (Long cid : categories) {
            this.brandMapper.insertCategoryBrand(cid, brand.getId());
        }
    }

    /**
     * 品牌更新
     *
     * @param brand
     * @param categories
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Brand brand, List<Long> categories) {
        //删除与原来的数据
        deleteByBrandIdInCategoryBrand(brand.getId());

        //修改品牌信息
        this.brandMapper.updateByPrimaryKeySelective(brand);
        //维护中间表
        for (Long cid : categories) {
            this.brandMapper.insertCategoryBrand(cid, brand.getId());
        }
    }

    /**
     * 品牌删除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrand(Long id) {
        //删除品牌信息
        this.brandMapper.deleteByPrimaryKey(id);
        //维护中间表
        this.brandMapper.deleteByBrandIdInCategoryBrand(id);

    }
    /**
     * 删除中间表中的数据
     * @param bid
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByBrandIdInCategoryBrand(Long bid) {
        this.brandMapper.deleteByBrandIdInCategoryBrand(bid);
    }
    /**
     * 根据category id查询brand
     * @param cid
     * @return
     */
    @Override
    public List<Brand> queryBrandByCategoryId(Long cid) {
        return brandMapper.queryBrandByCategoryId(cid);
    }
    /**
     * 根据品牌id集合查询品牌信息
     * @param ids
     * @return
     */
    @Override
    public List<Brand> queryBrandByBrandIds(List<Long> ids) {
        return brandMapper.selectByIdList(ids);
    }
}
