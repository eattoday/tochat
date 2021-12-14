package com.dawu.tochat.util;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/***
 *  @description
 *  @author DimStar
 *  @date: 2020/4/15
 *  @version: v1.0
 */
@Slf4j
public class OkHttp3Util {


    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().build();;


    /**
     * get
     *
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static Object get(String url, Map<String, String> queries) {
        String realUrl = urlJoint(url, queries);
        Request request = new Request.Builder()
                .url(realUrl)
                .build();

        return execNewCall(request, String.class);
    }

    public static Object getwithHeader(String url, Map<String, String> queries, String header) {
        String realUrl = urlJoint(url, queries);
        Request request = new Request.Builder()
                .url(realUrl)
                .addHeader("Authorization", "Basic " + header)
                .build();

        return execNewCall(request, String.class);
    }

    /**
     * 发送 post 表单提交
     *
     * @param url
     * @param params
     * @return
     */
    public static Object postFormParams(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        addMapParamsToFromBody(params, builder);
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        return execNewCall(request, String.class);
    }

    /**
     * 发送post json形式
     *
     * @param url
     * @param jsonParams
     * @return
     */
    public static Object postJsonParams(String url, String jsonParams) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return execNewCall(request, String.class);
    }

    /**
     * 发送post json形式+头 声网专用
     *
     * @param url
     * @param jsonParams
     * @return
     */
    public static Object postJsonParamsWithHeader(String url, String jsonParams, String header) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Basic " + header)
                .post(requestBody)
                .build();
        return execNewCall(request, String.class);
    }

    private static Object execNewCall(Request request, Class clazz) {
        try {
            Response response = okHttpClient.newCall(request).execute();
            log.info("请求{},参数{},状态{},响应{}", request.url(), request.body(), response.code(), response.toString());
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                // 响应结果
                if (clazz.equals(String.class)) {
                    return body.string();
                }
                if (clazz.equals(InputStream.class)) {
                    return body.byteStream();
                }

            }

        } catch (Exception e) {
            log.error("请求 {} 异常 {}", request.url(), Throwables.getStackTraceAsString(e));
        }
        return null;
    }


    /**
     * @param url    实际URL的path
     * @param params getURL参数
     * @return
     */
    private static String urlJoint(String url, Map<String, String> params) {
        StringBuilder realURL = new StringBuilder("");
        realURL = realURL.append(url);
        boolean isFirst = true;
        if (params == null) {
            return realURL.toString();
        } else {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                if (isFirst && !url.contains("?")) {
                    isFirst = false;
                    realURL.append("?");
                } else {
                    realURL.append("&");
                }
                realURL.append(entry.getKey());
                realURL.append("=");
                if (entry.getValue() == null) {
                    realURL.append(" ");
                } else {
                    realURL.append(entry.getValue());
                }

            }
        }

        return realURL.toString();
    }


    /**
     * 参数传入请求体
     *
     * @param params
     * @param builder
     */
    private static void addMapParamsToFromBody(Map<String, String> params, FormBody.Builder builder) {
        if (null == params || params.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey();
            String value;
            /**
             * 判断值是否是空的
             */
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            /**
             * 把key和value添加到formbody中
             */
            builder.add(key, value);
        }
    }

}
