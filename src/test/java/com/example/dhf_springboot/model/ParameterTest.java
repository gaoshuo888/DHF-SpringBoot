package com.example.dhf_springboot.model;

import com.example.dhf_springboot.model.dhf_springboot.Parameter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileName: ParameterTest.java
 * 参数测试类
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/29
 */
@SpringBootTest
class ParameterTest {
    @Autowired
    private Parameter parameter;
    //parameter对象创建后，可以实现数据库的参数加载
    @Test
    void testInit() {
        // 测试参数是否正确加载
        assertNotNull(parameter.getArea(), "流域面积不应为null");

        System.out.println("流域面积: " + parameter.getArea());
        System.out.println("E1: " + parameter.getE1());
        System.out.println("DD: " + parameter.getDD());
    }

}