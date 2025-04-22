package com.example.dhf_springboot.controller;

import com.example.dhf_springboot.model.Caculator;
import com.example.dhf_springboot.vo.CalculatorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileName: CaculatorController.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/14
 */
@RestController
@RequestMapping("/calculator")
public class CaculatorController {
    @Autowired
    private Caculator caculator;
    @PostMapping("/calculate")
    public CalculatorForm calculate(@RequestBody CalculatorForm calculatorForm) {//requestBody接收前端传入的json数据
        double result = caculator.calculate(calculatorForm);
        calculatorForm.setResult(result);
//        return result;//后端返回一个double值，responseBody
        return calculatorForm;//后端返回一个对象
    }
}
