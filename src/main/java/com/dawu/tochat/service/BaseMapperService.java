package com.dawu.tochat.service;


import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dawu.tochat.util.MyUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 创建于   : guohaotian
 * 创建时间 : 2021/12/1 0001 18:04
 * 详细描述 :
 */
@Service
public class BaseMapperService {

    /**
     * 根据 ID 查询单条
     */
    public Object selectById(String tableName, Long id) {
        return getBaseMapper(tableName).selectById(id);
    }

    /**
     * 根据 Map 查询列表
     */
    public List selectList(String tableName, Map<String, Object> param) {
        return getBaseMapper(tableName).selectList(parseQueryWrapper(tableName, param));
    }

    /**
     * 根据 Map 查询分页
     */
    public Page selectPage(String tableName, Page page, Map<String, Object> param) {
        return (Page) getBaseMapper(tableName).selectPage(page, parseQueryWrapper(tableName, param));
    }


    /**
     * 新增
     */
    public Integer insert(String tableName, Object entity) {
        return getBaseMapper(tableName).insert(entity);
    }

    /**
     * 修改
     */
    public Integer updateById(String tableName, Object entity) {
        return getBaseMapper(tableName).updateById(entity);
    }

    /**
     * 删除
     */
    public Integer deleteById(String tableName, Object entity) {
        return getBaseMapper(tableName).deleteById(entity);
    }

    /**
     * 删除
     */
    public Integer deleteById(String tableName, Long id) {
        return getBaseMapper(tableName).deleteById(id);
    }

    /**
     * 批量删除
     */
    public Integer deleteBatchIds(String tableName, List<Long> ids) {
        return getBaseMapper(tableName).deleteBatchIds(ids);
    }


    public BaseMapper getBaseMapper(String tableName) {
        return SpringUtil.getBean(tableName + "Mapper");
    }

    public QueryWrapper parseQueryWrapper(String tableName, Map<String, Object> param) {
        Class<?> tableClazz = SpringUtil.getBeanFactory().getType(tableName);
        QueryWrapper queryWrapper = new QueryWrapper();
        for (String key : param.keySet()) {
            Field field = MyUtils.getField(key, tableClazz);
            String columeName = key;
            String columeType = "eq";

            if (field == null) {
                queryWrapper.eq(columeName, param.get(key));
                continue;
            }

            // 获取字段对应数据库表字段
            TableField annotation = field.getAnnotation(TableField.class);
            if (annotation != null) {
                columeName = annotation.value();
            }

            // 获取字段比较类型
            ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            if (apiModelProperty != null && StringUtils.isNotBlank(apiModelProperty.notes())) {
                columeType = apiModelProperty.notes();
            }

            switch (columeType) {
                case "eq":
                    queryWrapper.eq(columeName, param.get(key));
                    break;
                case "like":
                    queryWrapper.like(columeName, param.get(key));
                    break;
                case "ge":
                    queryWrapper.ge(columeName, param.get(key));
                    break;
                case "le":
                    queryWrapper.le(columeName, param.get(key));
                    break;
                case "between":
                    String between = (String) param.get(key);
                    queryWrapper.between(columeName, between.split(",")[0], between.split(",")[1]);
                    break;
                default:
            }
        }
        return queryWrapper;
    }

}
