package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallOrderEntity;
import com.lebaoxun.modules.mall.service.MallOrderService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 订单表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@RestController
public class MallOrderController {
    @Autowired
    private MallOrderService mallOrderService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallorder/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallOrderService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallorder/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
		MallOrderEntity mallOrder = mallOrderService.selectById(id);
        return ResponseMessage.ok().put("mallOrder", mallOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallorder/save")
    @RedisLock(value="mall:mallorder:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallOrderEntity mallOrder){
		mallOrderService.insert(mallOrder);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallorder/update")
    @RedisLock(value="mall:mallorder:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallOrderEntity mallOrder){
		mallOrderService.updateById(mallOrder);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallorder/delete")
    @RedisLock(value="mall:mallorder:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids){
		mallOrderService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
