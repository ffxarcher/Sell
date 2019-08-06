package com.sq.sell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 在单元测试的类上加上注解：
 * @SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
 * 这里的Application是你SpringBoot启动类的类名。
 * 这里注解的作用是提供一个servlet环境，好让websocket能够初始化。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SellApplicationTests {

    @Test
    public void contextLoads() {
    }

}
