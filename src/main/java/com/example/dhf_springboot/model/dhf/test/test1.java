package com.example.dhf_springboot.model.dhf.test;

import java.util.Scanner;

/**
 * FileName: test1.java
 * 用于java学习,double
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2023/12/4
 */
public class test1 {
    public static void main(String[] args) {
//        double a = 2.7;
//        double b = 8.1 / 3;
//        System.out.println("b = " + b);
//        if (Math.abs(a - b) < 0.0000000000001) {
//            System.out.println("a与b相等");
//        } else {
//            System.out.println(a == b);
//        }
//        float c = 2.1F;
//        c = (float) (8.1/3);
//        c = 2.7F;
//        System.out.println("c = " + c);
//        if(a == c){
//            System.out.println("a与c相等");
//        }else{
//            System.out.println("a与c不等");
//        }

//
//        double x = 2.1;
//        float y = 2.1F;
//        if(x==y){
//            System.out.println("x与y相等");
//        }else{
//            System.out.println("x = " + x);
//            System.out.println("y = " + y);
//        }
//        System.out.println(x==y);
//        char char1 = '高';
//        System.out.println((int)char1);
//        System.out.println((char)39640);//'高'
//        System.out.println((int)1.9999);//1

        int a = 100;
        String b = a + "";
        int c = 99+6;
        System.out.println(b + 86);
        System.out.println(a > c ? c : a);//三元运算符
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入x的值：");
        int x = scanner.nextInt();
        System.out.println("请输入y的值：");
        int y = scanner.nextInt();
        System.out.println("x与y之和：" +( x + y));

    }
}
