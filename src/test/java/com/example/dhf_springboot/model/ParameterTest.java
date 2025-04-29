package com.example.dhf_springboot.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileName: ParameterTest.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/29
 */
@SpringBootTest
class ParameterTest {
    @Autowired
    private Parameter parameter;
    @Test
    void testInit() {
        // 测试参数是否正确加载
        assertNotNull(parameter.getArea(), "流域面积不应为null");
        assertNotNull(parameter.getTimeSpan(), "降雨时段数不应为null");
        assertNotNull(parameter.getL(), "流域最大河长不应为null");

        // 可以添加更多的断言来验证参数的具体值
        assertTrue(parameter.getArea() > 0, "流域面积应该大于0");
        assertTrue(parameter.getTimeSpan() > 0, "降雨时段数应该大于0");
        assertTrue(parameter.getL() > 0, "流域最大河长应该大于0");
        System.out.println("流域面积: " + parameter.getArea());
        System.out.println("E1: " + parameter.getE1());
        System.out.println("DD: " + parameter.getDD());
    }

}