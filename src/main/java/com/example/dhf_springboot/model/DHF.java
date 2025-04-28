package com.example.dhf_springboot.model;

import com.example.dhf_springboot.entity.Parameter;
import org.springframework.stereotype.Component;

/**
 * FileName: DHF.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/28
 */
@Component//这样就可以使用@Autowired在其他类注入DHF类
public class DHF {
    public double calculate(Parameter parameter){
        double result;
        double num1 = parameter.getE1();
        double num2 = parameter.getE2();

        result = num1 + num2;
        return result;
    }
}
