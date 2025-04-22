package com.example.dhf_springboot.model;

import com.example.dhf_springboot.vo.CalculatorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FileName: caculator.java
 * 负责计算器的逻辑
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/14
 */
@Component//这样就可以使用@Autowired注入
public class Caculator {

//    CalculatorForm calculatorForm;

    public double calculate(CalculatorForm calculatorForm) {
//        this.calculatorForm = calculatorForm;
        double result;
        double num1 = calculatorForm.getNum1();
        double num2 = calculatorForm.getNum2();

        switch (calculatorForm.getOperator()) {
            case "+":
                result = num1 + num2; break;
            case "-":
                result = num1 - num2; break;
            case "*":
                result = num1 * num2; break;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("不能除以0");
                }
                result = num1 / num2; break;
            default:
                throw new IllegalArgumentException("不支持的运算符");
        }

        return result;
    }


}
