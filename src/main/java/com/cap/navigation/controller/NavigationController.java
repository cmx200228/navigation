package com.cap.navigation.controller;

import com.cap.navigation.service.NavigationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 陈蒙欣
 * @date 2023/3/22 9:56
 */
@RestController
@RequestMapping("/navigation")
public class NavigationController {
    @Resource
    NavigationService navigationService;

    /**
     * 获取导航信息
     *
     * @param origin      起点
     * @param destination 终点
     * @return 导航信息
     */
    @PostMapping("/getNavigation")
    public String getNavigation(@RequestParam String origin, @RequestParam String destination) {
        System.out.println(origin);
        String navigation = navigationService.getNavigation(origin, destination);
        System.out.println(navigation);
        return navigation;
    }
}
