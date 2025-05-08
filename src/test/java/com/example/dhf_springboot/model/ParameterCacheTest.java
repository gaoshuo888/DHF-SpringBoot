package com.example.dhf_springboot.model;

import com.example.dhf_springboot.model.dhf_springboot.ParameterCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * FileName: ParameterCacheTest.java
 * 测试参数缓存加载情况
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/29
 */
@SpringBootTest
class ParameterCacheTest {

    @Autowired
    private ParameterCache parameterCache;

    @Test
    void testParameterLoading() {
        // 假设有一个名为 "basinArea" 的参数，值为 Double 类型
        Double basinArea = parameterCache.getParameter("area");
        System.out.println("流域面积: " + basinArea);

        // 加个简单的断言，确保参数存在且不为null
        assertNotNull(basinArea, "流域面积不应为null");

        // 假设期望流域面积为某个具体数值，比如100.0（可根据实际数据库数据修改）
        assertTrue(basinArea > 0.0, "流域面积应该大于0");
    }
}
