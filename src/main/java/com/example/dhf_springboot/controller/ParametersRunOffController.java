package com.example.dhf_springboot.controller;

import com.example.dhf_springboot.entity.ParametersRunOff;
import com.example.dhf_springboot.repository.ParametersRunOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileName: ParametersRunOffController.java
 * 用于处理与ParametersRunOff相关的请求
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/28
 */
@RestController
@RequestMapping("/parametersRunOff")
public class ParametersRunOffController {
    @Autowired
    private ParametersRunOffRepository parametersRunOffRepository;
    @GetMapping("/list")//展示所有参数
    public List<ParametersRunOff> getAllParameters() {
        return parametersRunOffRepository.findAll();
    }
    @PostMapping("/update")
    public List<ParametersRunOff> updateParameters(@RequestBody List<ParametersRunOff> parametersRunOffList) {
        //@RequestBody注解用于将前端传来的JSON数据转换为Java对象
        return parametersRunOffRepository.saveAll(parametersRunOffList);
    }
}
