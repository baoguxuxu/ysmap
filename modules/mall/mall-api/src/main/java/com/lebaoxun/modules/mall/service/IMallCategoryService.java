package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.mall.entity.MallCategoryEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallCategoryServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 分类表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallCategoryServiceHystrix.class)
public interface IMallCategoryService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallcategory/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallcategory/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallcategory/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallCategoryEntity mallCategory);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallcategory/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallCategoryEntity mallCategory);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallcategory/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids);
    
}

