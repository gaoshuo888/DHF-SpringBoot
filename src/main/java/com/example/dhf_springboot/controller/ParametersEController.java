package com.example.dhf_springboot.controller;

import com.example.dhf_springboot.entity.ParametersE;
import com.example.dhf_springboot.repository.ParametersERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileName: ParametersEController.java
 * 用于处理与ParametersE相关的请求
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/28
 */
@RestController
@RequestMapping("/parametersE")
public class ParametersEController {
    @Autowired
    private ParametersERepository parametersERepository;
    @GetMapping("/list")//展示所有参数
    public List<ParametersE> getAllParameters() {
        return parametersERepository.findAll();
    }
    @PostMapping("/update")
    public List<ParametersE> updateParameters(@RequestBody List<ParametersE> parametersEList) {
        return parametersERepository.saveAll(parametersEList);
    }

}
