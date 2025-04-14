package com.example.dhf_springboot.vo;

import lombok.Data;

/**
 * FileName: Calculator.java
 * 计算器类
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/14
 */
@Data
public class Calculator {
    double num1;
    double num2;
    String operator;

    double result;

}
