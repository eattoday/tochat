package com.dawu.tochat.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dawu.tochat.domain.SpUser;
import com.dawu.tochat.util.MyUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建于   : guohaotian
 * 创建时间 : 2021/12/1 0001 18:04
 * 详细描述 :
 */
@Service
public class TestService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public String testMybatis(String tableName) {

        Map<String, Object> param = new HashMap();
        Map<String, Object> queryParam = new HashMap();
        param.put("userName", "a");
        param.put("user_id", "1");

        BaseMapper baseMapper = MyUtils.getBaseMapper(sqlSessionFactory, tableName);

        for (String key : param.keySet()) {
            Field field = MyUtils.getField(key, MyUtils.getDomainClazz(tableName));
            TableField annotation = null;
            if (field != null) {
                annotation = field.getAnnotation(TableField.class);
            }
            if (annotation != null) {
                queryParam.put(annotation.value(), param.get(key));
            } else {
                queryParam.put(key, param.get(key));
            }
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(queryParam);
        List<SpUser> spUsers1 = baseMapper.selectList(queryWrapper);
        spUsers1.forEach(spUser -> System.out.println(spUser.getUserName()));

        return JSON.toJSONString(spUsers1);
    }


}
