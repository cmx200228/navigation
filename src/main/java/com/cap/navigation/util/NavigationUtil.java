package com.cap.navigation.util;

import com.cap.navigation.model.TO.HttpClientResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author 陈蒙欣
 * @date 2023/3/20 21:26
 */
@Component
public class NavigationUtil {
    @Value("${gaode.url}")
    private String url;

    @Value("${gaode.key}")
    private String key;

    @Value("${gaode.privateKey}")
    private String privateKey;

    /**
     * 发送请求
     * @param startingPointAccuracy 起点经度
     * @param startLatitude 起点纬度
     * @param endPointAccuracy 终点经度
     * @param endLatitude 终点纬度
     * @return 响应结果
     */
    public HttpClientResult getNavigation(String startingPointAccuracy , String startLatitude
    , String endPointAccuracy , String endLatitude)throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put("key",key);
        params.put("origin",startingPointAccuracy+","+startLatitude);
        params.put("destination",endPointAccuracy+","+endLatitude);
        params.put("output","json");
        params.put("sig",generateSignature(params,privateKey));
        HttpClientResult httpClientResult = HttpClientUtils.doGet(url, params);
        if (httpClientResult.getCode() == 200){
            return httpClientResult;
        }
        return null;
    }

    /**
     * 生成签名
     * @param params 请求参数
     * @param privateKey 私钥
     * @return 签名字符串
     */
    public static String generateSignature(Map<String, String> params, String privateKey) {
        // 将参数按照键名的升序排序
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        // 将排序后的参数按照key=value的形式拼接
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            sb.append(key).append("=").append(value).append("&");
        }

        sb.delete(sb.length()-1, sb.length());
        // 添加私钥
        sb.append(privateKey);
        // 进行MD5加密
        return md5(sb.toString());
    }

    /**
     * MD5加密
     * @param str 待加密字符串
     * @return 加密后字符串
     */
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                //将byte类型的变量转换成一个十六进制字符串，先使用使用"& 0xff"操作将byte类型的值转换成等价的无符号整数，即0~255之间的数，
                // 然后再使用Integer.toHexString()方法将其转换成十六进制字符串
                String s = Integer.toHexString(b & 0xff);
                if (s.length() == 1) {
                    sb.append("0");
                }
                sb.append(s);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
