package com.example.dhf_springboot.controller;

import com.example.dhf_springboot.entity.Parameter;
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
    @GetMapping("/list")
    public List<Parameter> getAllParameters() {
        return parameterRepository.findAll();
    }

    @PostMapping("/add")
    public Parameter addParameter(@RequestBody Parameter parameter) {
        return parameterRepository.save(parameter);
    }
}
