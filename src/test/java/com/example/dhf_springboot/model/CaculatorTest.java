package com.example.dhf_springboot.model;

import com.example.dhf_springboot.vo.CalculatorForm;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileName: CaculatorTest.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/14
 */
class CaculatorTest {
    Caculator caculator = new Caculator();
    CalculatorForm calculatorForm = new CalculatorForm();

    @org.junit.jupiter.api.Test
    void calculate() {
        calculatorForm.setNum1(10);
        calculatorForm.setNum2(5);
        calculatorForm.setOperator("+");
        double result = caculator.calculate(calculatorForm);
//        assertEquals(15, result);
        System.out.println(result);

        calculatorForm.setOperator("-");
        result = caculator.calculate(calculatorForm);
        assertEquals(5, result);

        calculatorForm.setOperator("*");
        result = caculator.calculate(calculatorForm);
        assertEquals(50, result);

        calculatorForm.setOperator("/");
        result = caculator.calculate(calculatorForm);
        assertEquals(2, result);

        calculatorForm.setNum2(0);
        try {
            result = caculator.calculate(calculatorForm);
            fail("Expected ArithmeticException");
        } catch (ArithmeticException e) {
            assertEquals("不能除以0", e.getMessage());
        }
    }

}