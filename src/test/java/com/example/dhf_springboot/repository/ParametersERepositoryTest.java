package com.example.dhf_springboot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileName: ParametersERepositoryTest.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/28
 */
@SpringBootTest
class ParametersERepositoryTest {
    @Autowired
    private ParametersERepository parametersERepository;
    @Test
    void testFindAll() {
        // 查询所有数据
        System.out.println(parametersERepository.findAll());
    }

}