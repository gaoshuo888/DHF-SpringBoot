package com.example.dhf_springboot.model.dhf.composable;

import com.example.dhf_springboot.model.dhf.calibration.TargetFunction;
import com.example.dhf_springboot.model.dhf.data.Parameter;
import com.example.dhf_springboot.model.dhf.data.ProgramRuntimeData;
import com.example.dhf_springboot.model.dhf.data.Result;
import com.example.dhf_springboot.model.dhf.module.ModuleOfDHF;

import java.text.DecimalFormat;

/**
 * FileName: Test3.java
 * 目标函数，f(x)=0.4*总合格+0.3*产流+0.3*汇流+0.7*确定性系数。（权重）
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2023/11/27
 */
public class Test3 {
    private static int popsize = AlgorithmDefineDomain.getPopsize();      //种群数量
    private static int trails_sum = 0;  //记录总循环次数
    private static int trails;//记录第几次循环
    int countf1 = 0;//记录迭代次数
    int countf2 = 0;//记录迭代次数
    int countf3 = 0;//记录迭代次数
    int countf4 = 0;//记录迭代次数
    double rateOfProgress = 0;//记录循环迭代的进度

    int PassFlood_sum = 0;  //洪水总合格场次
    int PassY_sum = 0;      //洪水产流合格场次
    int PassQ_sum = 0;      //洪水汇流合格场次
    double DC_average = 0;  //平均确定性系数
    double DC_sum = 0;

    double total_s_r01 = 0;     //Q预报值减去实测值的平方和
    double total_s_a01 = 0;     //Q预报值减去实测均值的平方和
    double total_relaY = 0;     //产流相对误差之和
    double total_relaQMax = 0;  //洪峰相对误差之和
    int total_QMax_time_differ = 0;    //峰现时间差之和
    double TYTC = 0;            //N次洪水计算产流与实际产流误差总和
    double[] totalY_real;    //实际总径流深
    double[] totalY_simu;    //模拟总径流深
    double[] QMax_real;        //实际洪峰
    double[] QMax_simu;        //模拟洪峰
    int[] QMax_real_time;        //实际峰现时间
    int[] QMax_simu_time;        //实际峰现时间

    public double f1(Double[] x) {
        double value = 0;

        Parameter par_pop = new Parameter(x);
        ProgramRuntimeData prd = new ProgramRuntimeData(par_pop);
        ModuleOfDHF dhf = new ModuleOfDHF(par_pop, prd);
        dhf.run();

        Result re = new Result();                        //各参数对应的结果
        re = TargetFunction.target(prd);
//        System.out.println(re.getPassFlood_sum());

        PassFlood_sum = re.getPassFlood_sum();
        PassY_sum = re.getPassY_sum();
        PassQ_sum = re.getPassQ_sum();
        DC_sum = re.getDC_sum();
        DC_average = re.getDC_sum() / ProgramRuntimeData.getFloodNum();
//        System.out.println(re.getDC_sum());
//        DC_average = re.getDC_sum();

        total_s_r01 = re.getTotal_s_r01();
        total_s_a01 = re.getTotal_s_a01();
        total_relaY = re.getTotal_relaY();
        total_relaQMax = re.getTotal_relaQMax();
        total_QMax_time_differ = re.getTotal__QMax_time_differ();
        TYTC = re.getTYTC();

        totalY_real = re.getTotalY_real();//场次洪水实际径流
        totalY_simu = re.getTotalY_simu();//场次洪水模拟径流
        double totalY_real_sum = 0;//实际径流总和
        double totalY_real_average = 0;//实际径流总和的均值
        for (int i = 0; i < totalY_real.length; i++) {
            totalY_real_sum += totalY_real[i];
        }
        totalY_real_average = totalY_real_sum / totalY_real.length;
        double sum_s = 0;//分子，实际减去模拟的平方和
        double sum_x = 0;//分母，实际减去均值的平方和
        double DC_Y = 0;//不同场次洪水之间径流的确定性系数
        for (int i = 0; i < totalY_real.length; i++) {
            sum_s += Math.pow((totalY_simu[i] - totalY_real[i]), 2);
            sum_x += Math.pow((totalY_simu[i] - totalY_real_average), 2);
        }
        DC_Y = 1 - (sum_s / sum_x);
        if (Double.isNaN(DC_Y)) {
            DC_Y = -10;
        }

        QMax_real = re.getQMax_real();//场次洪水实际洪峰
        QMax_simu = re.getQMax_simu();//场次洪水模拟洪峰
        double QMax_real_sum = 0;//实际洪峰总和
        double QMax_real_average = 0;//实际洪峰总和的均值
        for (int i = 0; i < QMax_real.length; i++) {
            QMax_real_sum += QMax_real[i];
        }
        QMax_real_average = QMax_real_sum / QMax_real.length;
        double sum_a = 0;//分子，实际减去模拟的平方和
        double sum_b = 0;//分母，实际减去均值的平方和
        double DC_QMax = 0;//不同场次洪水之间径流的确定性系数
        for (int i = 0; i < QMax_real.length; i++) {
            sum_a += Math.pow((QMax_simu[i] - QMax_real[i]), 2);
            sum_b += Math.pow((QMax_simu[i] - QMax_real_average), 2);
        }
        DC_QMax = 1 - (sum_a / sum_b);
        if (Double.isNaN(DC_QMax)) {
            DC_QMax = -10;
        }

        QMax_real_time = re.getQMax_real_time();        //实际峰现时间
        QMax_simu_time = re.getQMax_simu_time();        //实际峰现时间
        double QMax_real_time_sum = 0;//实际峰现时间总和
        double QMax_real_time_average = 0;//实际峰现时间总和的均值
        for (int i = 0; i < QMax_real_time.length; i++) {
            QMax_real_time_sum += QMax_real_time[i];
        }
        QMax_real_time_average = QMax_real_time_sum / QMax_real_time.length;
        double sum_c = 0;//分子，实际减去模拟的平方和
        double sum_d = 0;//分母，实际减去均值的平方和
        double DC_QMax_time = 0;//不同场次洪水之间径流的确定性系数
        for (int i = 0; i < QMax_real_time.length; i++) {
            sum_c += Math.pow((QMax_simu_time[i] - QMax_real_time[i]), 2);
            sum_d += Math.pow((QMax_simu_time[i] - QMax_real_time_average), 2);
        }
        DC_QMax_time = 1 - (sum_c / sum_d);
        if (Double.isNaN(DC_QMax_time)) {
            DC_QMax = -10;
        }

        countf1 += 1;//迭代次数
        // int / int 运算取整
        rateOfProgress = (trails - 1 + (double) countf1 / AlgorithmDefineDomain.getTerminateNum()) / trails_sum * 100;

        DecimalFormat threedf = new DecimalFormat("#0.000");    //平均确定性
        DecimalFormat onedf = new DecimalFormat("#00.00");//格式化数据，保留两位小数

        /**控制台输出显示**/
//        System.out.println("迭代：" + countf1 + "  洪水合格：" + PassFlood_sum + "  产流合格：" + PassY_sum
//                + "  汇流合格：" + PassQ_sum + " 平均确定性系数：" + DC_average);
        System.out.println("总进度：" + onedf.format(rateOfProgress) + "%" + '\t' + "循环数：" + trails + '\t' + "迭代数：" + countf1 + '\t' +
                "洪水合格：" + PassFlood_sum + '\t' + "产流合格：" + PassY_sum + '\t'
                + "汇流合格：" + PassQ_sum + '\t' + "平均确定性系数：" + threedf.format(DC_average));
//        System.out.println("迭代：" + countf1 + "  洪水合格：" + PassFlood_sum + "  产流合格：" + PassY_sum
//                + "  汇流合格：" + PassQ_sum + " 平均确定性系数：" + DC_average +
//                "  产流相对误差之和：" + total_relaY + "\n" +
//                "  洪峰相对误差之和：" + total_relaQMax + "  峰现时间误差总和：" + total_QMax_time_differ
//                + "  DC_Y：" + DC_Y + "  DC_QMax：" + DC_QMax + "  DC_QMax_time：" + DC_QMax_time);

//        System.out.println("迭代" + countf1 + "  洪水合格：" + PassFlood_sum + "  产流合格：" + PassY_sum
//                + "  汇流合格：" + PassQ_sum + " 平均确定性系数：" + DC_average + "  产流相对误差之和：" + total_relaY  +
//                "  洪峰相对误差之和：" + total_relaQMax + "  峰现时间误差总和：" + total_QMax_time_differ);

//        DecimalFormat onedf = new DecimalFormat("##00.000"); //控制输出的格式。#--不存在输出为空；0--不存在输出为0。
//        System.out.println("迭代：" + countf1 + " 洪水合格：" + PassFlood_sum + " 产流合格：" + PassY_sum
//                + " 汇流合格：" + PassQ_sum + " DC_sum：" + onedf.format(DC_sum) + " total_relaQMax：" + onedf.format(total_relaQMax) + " total_QMax_time_differ= "+total_QMax_time_differ);

        /**选择目标函数--进化方向**/
        value = PassFlood_sum + PassY_sum + PassQ_sum + DC_average;
//        value = 0.6 * PassFlood_sum + 0.3 * PassY_sum + 0.5 * PassQ_sum;
//        value = 0.4 * PassFlood_sum + 0.2 * PassY_sum + 0.4 * PassQ_sum - 0.3 * (total_relaY + total_relaQMax);
//        value = 0.8 * DC_average - 0.6 * total_relaY - 0.6*total_relaQMax- 0.06*total_QMax_time_differ;
//        value =0.2 * PassFlood_sum + 0.2 * PassY_sum + 0.2 * PassQ_sum +
//                0.8 * DC_average - 0.6 * total_relaY - 0.6*total_relaQMax- 0.06*total_QMax_time_differ;
//        value = PassQ_sum + 2.4*DC_average- total_relaY - total_relaQMax- 0.06*total_QMax_time_differ;
//        value = DC_average;
//        value = DC_average + 0.2 * PassFlood_sum;
//        value = PassFlood_sum;

//        value = DC_QMax + DC_average+DC_QMax_time;
//        value = DC_QMax_time;
//        value = 2.3*PassFlood_sum + 4.0* PassY_sum + 2.3*PassQ_sum +
//                DC_average + DC_Y + DC_QMax + DC_QMax_time
//                - 0.3 * (total_relaY + total_relaQMax) - 0.03 * total_QMax_time_differ;
//        value = 2.3*PassFlood_sum + DC_average + DC_Y + DC_QMax + DC_QMax_time
//                - 0.3 * (total_relaY + total_relaQMax) - 0.03 * total_QMax_time_differ;
//        value = 2.3*PassFlood_sum + 4.0* PassY_sum + 2.3*PassQ_sum + DC_average;

//        value = 0.7 * PassFlood_sum + 0.4 * PassY_sum + 0.7 * PassQ_sum + DC_average +      //公式越多效果越好，是否可以进一步完善目标函数
//                DC_Y + DC_QMax + DC_QMax_time
//                - 0.3 * (total_relaY + total_relaQMax) - 0.03 * total_QMax_time_differ;
        /**单场次洪水率定**/  //对于单场次洪水，要求模拟误差小
//        value = 2*(PassFlood_sum + PassY_sum + PassQ_sum) + 2*DC_average
//                - 0.05 * (total_relaY + total_relaQMax + Math.abs(total_QMax_time_differ));
        /**产汇流分别率定**/
//        value = 20 * PassY_sum + PassFlood_sum + PassQ_sum - 5 * total_relaY;    //目标：以产流合格为主要目标。
//        value = 10 * DC_sum - 5 * total_relaY;    //目标：产流，相对误差尽量减少。

//        value = 20 * PassFlood_sum + 10* PassQ_sum + DC_sum - total_relaQMax - total_QMax_time_differ;       //目标：以洪水合格为主要目标
//        value = 50 * PassFlood_sum + 30 * PassQ_sum + 10 * DC_sum - 5 * total_relaQMax - 0.5*total_QMax_time_differ;       //目标：以洪水合格为主要目标
//
        /**率定一次，洪水合格>产流>汇流**/ //分别率定可能导致汇流不是最优，产流参数会影响汇流
//        value = 50 * PassFlood_sum + 30 * PassY_sum +20 * PassQ_sum + 10 * DC_sum - 5 * total_relaY- 5 * total_relaQMax - 0.5*total_QMax_time_differ;

        return value;
    }

    public double f2(Double[] x) {
        double value = 0;

        Parameter par_pop = new Parameter(x);
        ProgramRuntimeData prd = new ProgramRuntimeData(par_pop);
        ModuleOfDHF dhf = new ModuleOfDHF(par_pop, prd);
        dhf.run();

        Result re = new Result();                        //各参数对应的结果
        re = TargetFunction.target(prd);
//        System.out.println(re.getPassFlood_sum());

        PassFlood_sum = re.getPassFlood_sum();
        PassY_sum = re.getPassY_sum();
        PassQ_sum = re.getPassQ_sum();
        DC_average = re.getDC_sum() / ProgramRuntimeData.getFloodNum();
//        System.out.println(re.getDC_sum());
//        DC_average = re.getDC_sum();

        total_s_r01 = re.getTotal_s_r01();
        total_s_a01 = re.getTotal_s_a01();
        total_relaY = re.getTotal_relaY();
        total_relaQMax = re.getTotal_relaQMax();
        total_QMax_time_differ = re.getTotal__QMax_time_differ();
        TYTC = re.getTYTC();

        countf2 += 1;//迭代次数
        System.out.println("迭代：" + countf2 + "  洪水合格：" + PassFlood_sum + "  产流合格：" + PassY_sum
                + "  汇流合格：" + PassQ_sum + " 平均确定性系数：" + DC_average +
                "  产流相对误差之和：" + total_relaY + "  洪峰相对误差之和：" + total_relaQMax);

//        value = 0.6 * PassFlood_sum + 0.3 * PassY_sum + 0.5 * PassQ_sum;
//        value = 0.4 * PassFlood_sum + 0.2 * PassY_sum + 0.4 * PassQ_sum - 0.3 * (total_relaY + total_relaQMax);
//        value = 0.8 * DC_average - 0.6 * total_relaY - 0.6*total_relaQMax- 0.06*total_QMax_time_differ;
//        value = PassQ_sum + 2.4*DC_average- total_relaY - total_relaQMax- 0.06*total_QMax_time_differ;
//        value = DC_average;
        value = PassY_sum + DC_average;

        return value;
    }

    public double f3(Double[] x) {
        double value = 0;

        Parameter par_pop = new Parameter(x);
        ProgramRuntimeData prd = new ProgramRuntimeData(par_pop);
        ModuleOfDHF dhf = new ModuleOfDHF(par_pop, prd);
        dhf.run();

        Result re = new Result();                        //各参数对应的结果
        re = TargetFunction.target(prd);
//        System.out.println(re.getPassFlood_sum());

        PassFlood_sum = re.getPassFlood_sum();
        PassY_sum = re.getPassY_sum();
        PassQ_sum = re.getPassQ_sum();
        DC_average = re.getDC_sum() / ProgramRuntimeData.getFloodNum();
//        System.out.println(re.getDC_sum());
//        DC_average = re.getDC_sum();

        total_s_r01 = re.getTotal_s_r01();
        total_s_a01 = re.getTotal_s_a01();
        total_relaY = re.getTotal_relaY();
        total_relaQMax = re.getTotal_relaQMax();
        total_QMax_time_differ = re.getTotal__QMax_time_differ();
        TYTC = re.getTYTC();

        countf3 += 1;//迭代次数
        System.out.println("迭代：" + countf3 + "  洪水合格：" + PassFlood_sum + "  产流合格：" + PassY_sum
                + "  汇流合格：" + PassQ_sum + " 平均确定性系数：" + DC_average +
                "  产流相对误差之和：" + total_relaY + "  洪峰相对误差之和：" + total_relaQMax);

//        value = 0.6 * PassFlood_sum + 0.3 * PassY_sum + 0.5 * PassQ_sum;
//        value = 0.4 * PassFlood_sum + 0.2 * PassY_sum + 0.4 * PassQ_sum - 0.3 * (total_relaY + total_relaQMax);
//        value = 0.8 * DC_average - 0.6 * total_relaY - 0.6*total_relaQMax- 0.06*total_QMax_time_differ;
//        value = PassQ_sum + 2.4*DC_average- total_relaY - total_relaQMax- 0.06*total_QMax_time_differ;
//        value = DC_average;
        value = PassQ_sum + 0.5 * PassFlood_sum + DC_average;

        return value;
    }

    public double f4(Double[] x) {
        double value = 0;

        Parameter par_pop = new Parameter(x);
        ProgramRuntimeData prd = new ProgramRuntimeData(par_pop);
        ModuleOfDHF dhf = new ModuleOfDHF(par_pop, prd);
        dhf.run();

        Result re = new Result();                        //各参数对应的结果
        re = TargetFunction.target(prd);
//        System.out.println(re.getPassFlood_sum());

        PassFlood_sum = re.getPassFlood_sum();
        PassY_sum = re.getPassY_sum();
        PassQ_sum = re.getPassQ_sum();
        DC_average = re.getDC_sum() / ProgramRuntimeData.getFloodNum();
//        System.out.println(re.getDC_sum());
//        DC_average = re.getDC_sum();

        total_s_r01 = re.getTotal_s_r01();
        total_s_a01 = re.getTotal_s_a01();
        total_relaY = re.getTotal_relaY();
        total_relaQMax = re.getTotal_relaQMax();
        total_QMax_time_differ = re.getTotal__QMax_time_differ();
        TYTC = re.getTYTC();

        countf4 += 1;//迭代次数
        System.out.println("迭代：" + countf4 + "  洪水合格：" + PassFlood_sum + "  产流合格：" + PassY_sum
                + "  汇流合格：" + PassQ_sum + " 平均确定性系数：" + DC_average +
                "  产流相对误差之和：" + total_relaY + "  洪峰相对误差之和：" + total_relaQMax);

//        value = 0.6 * PassFlood_sum + 0.3 * PassY_sum + 0.5 * PassQ_sum;
//        value = 0.4 * PassFlood_sum + 0.2 * PassY_sum + 0.4 * PassQ_sum - 0.3 * (total_relaY + total_relaQMax);
//        value = 0.8 * DC_average - 0.6 * total_relaY - 0.6*total_relaQMax- 0.06*total_QMax_time_differ;
//        value = PassQ_sum + 2.4*DC_average- total_relaY - total_relaQMax- 0.06*total_QMax_time_differ;
//        value = DC_average;
        value = DC_average;

        return value;
    }

    public static int getTrails_sum() {
        return trails_sum;
    }

    public static void setTrails_sum(int trails_sum) {
        Test3.trails_sum = trails_sum;
    }

    public static int getTrails() {
        return trails;
    }

    public static void setTrails(int trails) {
        Test3.trails = trails;
    }
}