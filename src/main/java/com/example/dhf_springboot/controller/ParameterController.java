package com.example.dhf_springboot.controller;

import com.example.dhf_springboot.entity.Parameter;
import com.example.dhf_springboot.model.DHF;
import com.example.dhf_springboot.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileName: ParameterController.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/25
 */
@RestController
@RequestMapping("/parameter")
public class ParameterController {
    @Autowired
    private ParameterRepository parameterRepository;
    @GetMapping("/list")//展示所有参数
    public List<Parameter> getAllParameters() {
        return parameterRepository.findAll();
    }

    @PostMapping("/add")//修改参数
    public Parameter addParameter(@RequestBody Parameter parameter) {
        return parameterRepository.save(parameter);
    }

    @Autowired
    private DHF dhf;
    @PostMapping("/calculate")//计算参数
    //前端传入json数据，后端不返回值
    public void calculate(@RequestBody Parameter parameter) {
        double result = dhf.calculate(parameter);
        System.out.println(result);
    }
}
