package com.cap.navigation.service.impl;

import com.cap.navigation.service.NavigationService;
import com.cap.navigation.util.NavigationUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 陈蒙欣
 * @date 2023/3/22 9:51
 */
@Service("navigationService")
public class NavigationServiceImpl implements NavigationService {
    @Resource
    NavigationUtil navigationUtil;

    /**
     * 获取导航信息
     *
     * @param origin      起点
     * @param destination 终点
     * @return 导航信息
     */
    @Override
    public String getNavigation(String origin, String destination) {
        String[] split = origin.split(",");
        String[] split1 = destination.split(",");
        try {
            return navigationUtil.getNavigation(split[0], split[1], split1[0], split1[1]).getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
