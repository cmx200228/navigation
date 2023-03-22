package com.cap.navigation.service;

/**
 * @author 陈蒙欣
 * @date 2023/3/22 9:39
 */
public interface NavigationService {
    /**
     * 获取导航信息
     * @param origin 起点
     * @param destination 终点
     * @return 导航信息
     */
    String getNavigation(String origin, String destination);
}
