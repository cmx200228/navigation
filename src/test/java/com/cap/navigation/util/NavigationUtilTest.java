package com.cap.navigation.util;

import com.cap.navigation.model.TO.HttpClientResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 陈蒙欣
 * @date 2023/3/21 22:15
 */
@SpringBootTest
class NavigationUtilTest {

  @Resource
  NavigationUtil navigationUtil;
  @Test
  void getNavigation() throws Exception {
    HttpClientResult navigation = navigationUtil.getNavigation("116.481028", "39.989643", "116.465302", "39.80423");
    System.out.println(navigation.getContent());
  }
}
