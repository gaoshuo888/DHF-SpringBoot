package com.example.dhf_springboot.controller;

import com.example.dhf_springboot.repository.BrokenLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileName: Book2Controller.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/9
 */
@RestController
@RequestMapping("/chart")
public class BrokenLineController {
    @Autowired
    private BrokenLineRepository brokenLineRepository;

    @GetMapping("/list")//http://127.0.0.1:8082/chart/list中显示结果
    public Object getAllBooks() {
        return brokenLineRepository.findAll();
    }
}
