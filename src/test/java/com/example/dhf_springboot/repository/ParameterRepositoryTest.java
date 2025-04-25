package com.example.dhf_springboot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileName: ParameterRepositoryTest.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/25
 */
@SpringBootTest
class ParameterRepositoryTest {
    @Autowired
    private ParameterRepository parameterRepository;
    @Test
    void testFindAll() {
        // 查询所有数据
        System.out.println(parameterRepository.findAll());
    }

}