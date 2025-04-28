package com.example.dhf_springboot.vo;

import lombok.Data;

/**
 * FileName: Calculator.java
 * 前端传入的计算器表单，接收前端传入的json数据
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/14
 */
@Data
public class CalculatorForm {
    private Double num1;
    private Double num2;
    String operator;

    double result;
}
