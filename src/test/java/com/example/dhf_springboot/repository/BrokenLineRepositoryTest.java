package com.example.dhf_springboot.repository;

import com.example.dhf_springboot.repository.isnotdhf.BrokenLineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * FileName: BrokenLineRepositoryTest.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/13
 */
@SpringBootTest
class BrokenLineRepositoryTest {
    @Autowired
    private BrokenLineRepository brokenLineRepository;
    @Test
    void testFindAll() {
        // 查询所有数据
        System.out.println(brokenLineRepository.findAll());
    }

}