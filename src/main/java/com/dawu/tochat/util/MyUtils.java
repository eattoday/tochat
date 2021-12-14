package com.dawu.tochat.util;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建于   : guohaotian
 * 创建时间 : 2021/11/26 0026 18:27
 * 详细描述 :
 */
public class MyUtils {
    private static List<String> mapperPackageList;
    {
        mapperPackageList = new ArrayList<>();
        mapperPackageList.add("com.dawu.tochat.mapper.");
    }

    private static List<String> domainPackageList;
    {
        domainPackageList = new ArrayList<>();
        domainPackageList.add("com.dawu.tochat.domain.");
    }


    public static Class<?> getDomainClazz(String tableName) {
        for (String mapperPackage : domainPackageList) {
            Class<?> mapperClass = getClazz(mapperPackage + tableName);
            if (mapperClass != null) {
                return mapperClass;
            }
        }
        return null;
    }

    public static BaseMapper getBaseMapper(SqlSessionFactory sqlSessionFactory, String tableName) {
        for (String mapperPackage : mapperPackageList) {
            Class<?> mapperClass = getClazz(mapperPackage + tableName + "Mapper");
            if (mapperClass != null) {
                return (BaseMapper) sqlSessionFactory.openSession().getMapper(mapperClass);
            }
        }
        return null;
    }

    public static Class<?> getClazz(String tableName) {

        try {
            Class<?> mapperClass = Class.forName(tableName);
            return mapperClass;
        } catch (ClassNotFoundException e) {
        }
        return null;

    }

    public static Field getField(String fieldName, Class domainClazz) {

        try {
            return domainClazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
        }
        return null;

    }


}
