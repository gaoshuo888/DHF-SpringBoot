package com.example.dhf_springboot.controller;

import com.example.dhf_springboot.entity.ParametersConfluence;
import com.example.dhf_springboot.repository.ParametersConfluenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileName: ParametersConfluenceController.java
 * 汇流参数控制器
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/28
 */
@RestController
@RequestMapping("/parametersConfluence")
public class ParametersConfluenceController {
    @Autowired
    private ParametersConfluenceRepository parametersConfluenceRepository;
    @GetMapping("/list")//展示所有参数
    public List<ParametersConfluence> getAllParameters() {
        return parametersConfluenceRepository.findAll();
    }
    @PostMapping("/update")
    public List<ParametersConfluence> updateParameters(@RequestBody List<ParametersConfluence> parametersConfluenceList) {
        //@RequestBody注解用于将前端传来的JSON数据转换为Java对象
        return parametersConfluenceRepository.saveAll(parametersConfluenceList);
    }
}
