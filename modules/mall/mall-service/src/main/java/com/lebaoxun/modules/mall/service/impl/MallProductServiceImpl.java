package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallProductDao;
import com.lebaoxun.modules.mall.entity.MallProductEntity;
import com.lebaoxun.modules.mall.service.MallProductService;


@Service("mallProductService")
public class MallProductServiceImpl extends ServiceImpl<MallProductDao, MallProductEntity> implements MallProductService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallProductEntity> page = this.selectPage(
                new Query<MallProductEntity>(params).getPage(),
                new EntityWrapper<MallProductEntity>()
        );

        return new PageUtils(page);
    }

}
