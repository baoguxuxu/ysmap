package com.lebaoxun.modules.mall.dao;

import com.lebaoxun.modules.mall.entity.MallProductEntity;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 商品表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@Mapper
public interface MallProductDao extends BaseMapper<MallProductEntity> {
	
}
