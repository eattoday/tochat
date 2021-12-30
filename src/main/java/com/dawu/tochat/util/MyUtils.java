package com.dawu.tochat.util;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /** 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)}) */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /** 驼峰转下划线,效率比上面高 */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
