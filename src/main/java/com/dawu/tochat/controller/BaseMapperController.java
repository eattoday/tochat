package com.dawu.tochat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dawu.tochat.service.BaseMapperService;
import com.dawu.tochat.util.RedisUtils;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 创建于   : guohaotian
 * 创建时间 : 2021年12月9日17:45:01
 * 详细描述 :
 */
@Api(tags = "单表基础操作")
@Controller()
@RequestMapping("/base")
@Slf4j
public class BaseMapperController {

    @Autowired
    private BaseMapperService baseMapperService;

    /**
     * 获取所有表结构
     */
    @ResponseBody
    @GetMapping("/getTables")
    @ApiOperation(value = "获取所有表结构,若传递参数则只查询对应表")
    @ApiOperationSupport(order = 1)
    public Object getTables(String tableName) {
        return baseMapperService.getTables(tableName);
    }
    /**
     * 根据 ID 查询单条
     */
    @ResponseBody
    @GetMapping("/{tableName}/{id}")
    @ApiOperation(value = "根据 ID 查询单条")
    @Cacheable(cacheNames="redissonCacheMap", key="#tableName + #id")
    @ApiOperationSupport(order = 2)
    public Object selectById(@PathVariable("tableName") String tableName,@PathVariable("id") Long id) {
        return baseMapperService.selectById(tableName,id);
    }

    /**
     * 根据 Map 查询列表
     */
    @ResponseBody
    @PostMapping("/{tableName}/list")
    @ApiOperation(value = "根据 MAP 查询列表")
    @ApiOperationSupport(order = 4)
    public List selectList(@PathVariable("tableName")String tableName, @RequestBody Map<String, Object> param) {
        return baseMapperService.selectList(tableName, param);
    }

    /**
     * 根据 Map 查询分页
     */
    @ResponseBody
    @PostMapping("/{tableName}/page")
    @ApiOperation(value = "根据 MAP 查询分页")
    @ApiOperationSupport(order = 6)
    public Page selectPage(@PathVariable("tableName")String tableName, Long current ,Long size,@RequestBody Map<String, Object> param) {
        Page page =new Page();
        page.setCurrent(current);
        page.setMaxLimit(size);
        return baseMapperService.selectPage(tableName,page,param);
    }


    /**
     * 新增
     */
    @ResponseBody
    @PostMapping("/{tableName}/insert")
    @ApiOperation(value = "新增单条")
    @ApiOperationSupport(order = 8)
    public Integer insert(@PathVariable("tableName")String tableName,@RequestBody Object entity) {
        return baseMapperService.insert(tableName, entity);
    }

    /**
     * 修改
     */
    @ResponseBody
    @PostMapping("/{tableName}/update")
    @ApiOperation(value = "修改单条")
    @CachePut(cacheNames="redissonCacheMap", key="#tableName + #id")
    @ApiOperationSupport(order = 10)
    public Integer updateById(@PathVariable("tableName")String tableName, @RequestBody Object entity) {
        return baseMapperService.updateById(tableName, entity);
    }

    ///**
    // * 删除
    // */
    //@ResponseBody
    //@PostMapping("/{tableName}/deleteOne")
    //@ApiOperation(value = "根据对象删除单条")
    //@ApiOperationSupport(order = 12)
    //public Integer deleteById(@PathVariable("tableName")String tableName,@RequestBody Object entity) {
    //    return baseMapperService.deleteById(tableName, entity);
    //}

    /**
     * 删除
     */
    @ResponseBody
    @DeleteMapping("/{tableName}/{id}")
    @ApiOperation(value = "根据 ID 删除单条")
    @CacheEvict(cacheNames="redissonCacheMap", key="#tableName + #id")
    @ApiOperationSupport(order = 14)
    public Integer deleteById(@PathVariable("tableName")String tableName,@PathVariable("id") Long id) {
        return baseMapperService.deleteById(tableName, id);
    }

    /**
     * 批量删除
     */
    @ResponseBody
    @PostMapping("/{tableName}/deleteList")
    @ApiOperation(value = "根据 ID 批量删除")
    @ApiOperationSupport(order = 16)
    public Integer deleteBatchIds(@PathVariable("tableName")String tableName,@RequestBody List<Long> ids) {
        RedisUtils.deleteObject("redissonCacheMap");
        return baseMapperService.deleteBatchIds(tableName, ids);
    }



}
