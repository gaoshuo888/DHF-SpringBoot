package com.example.dhf_springboot.model.dhf.test;

import com.example.dhf_springboot.model.dhf.tools.WriteResultToExcel;

/**
 * FileName: af.java
 * 测试类，与主程序无关
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2024/12/11
 */
public class test2 {
    public static void main(String[] args) {
//        String csvFile = "E:\\work\\1小论文\\小论文成果\\大伙房模型\\dhf\\VAR.csv"; // 请替换为实际的VAR.csv文件路径
//        VarToParameter0 varToParameter0 = new VarToParameter0(csvFile);
//        Parameter.setParameter0(varToParameter0.varToParameter0());
//
//        Parameter parameter = new Parameter();
//        System.out.println(Arrays.toString(Parameter.getParameter0()));
//        System.out.println(parameter.getA());
        WriteResultToExcel.writeResultToExcel();
    }
}
