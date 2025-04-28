package com.example.dhf_springboot.controller;

import com.example.dhf_springboot.entity.ParametersBasin;
import com.example.dhf_springboot.repository.ParametersBasinRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileName: ParametersBasinController.java
 * 流域参数控制器
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/29
 */
@RestController
@RequestMapping("/parametersBasin")
public class ParametersBasinController {
    @Autowired
    private ParametersBasinRepository parametersBasinRepository;
    @GetMapping("/list")//展示所有参数
    public List<ParametersBasin> getAllParameters() {
        return parametersBasinRepository.findAll();
    }
    @PostMapping("/update")
    public List<ParametersBasin> updateParameters(@RequestBody List<ParametersBasin> parametersBasinList) {
        //@RequestBody注解用于将前端传来的JSON数据转换为Java对象
        return parametersBasinRepository.saveAll(parametersBasinList);
    }
}
