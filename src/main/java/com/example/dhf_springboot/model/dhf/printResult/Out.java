package com.example.dhf_springboot.model.dhf.printResult;

import com.example.dhf_springboot.model.dhf.calibration.TargetFunction;
import com.example.dhf_springboot.model.dhf.data.Parameter;
import com.example.dhf_springboot.model.dhf.data.ProgramRuntimeData;
import com.example.dhf_springboot.model.dhf.data.Result;
import com.example.dhf_springboot.model.dhf.module.ModuleOfDHF;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Out {      //用于打印--1.模拟产流--2.模拟汇流

    Parameter par= new Parameter();

    double[] totalY_real;   //实际总径流深
    double[] totalY_simu;   //模拟总径流深
    double[] totalQ_real;   //洪水实际的洪量
    double[] totalQ_simu;   //洪水模拟的洪量
    double[] absHL;         //洪量绝对误差：洪水模拟的洪量减去洪水实际的洪量
    double[] DC;            //确定性系数
    double[] absY;          //产流绝对误差
    double[] relaY;         //产流相对误差
    double[] totalP;        //总降雨量
    double[] alefa;        //实测产流系数
    double[] QMax_real;    //实际洪峰
    double[] QMax_simu;    //模拟洪峰
    double[] absQMax;      //洪峰绝对误差
    double[] relaQMax;     //洪峰相对误差
    //注：利用洪峰在流量数组中的角标表示，在时间间隔一定的情况下，即为峰现时间
    int[] QMax_real_time;    //实际峰现时间
    int[] QMax_simu_time;    //模拟峰现时间
    int[] QMax_time_differ;  //峰现时间差

    int[] passY;             //产流是否合格，合格为1，不合格为0
    int[] passQ;             //汇流是否合格，合格为1，不合格为0
    int[] passFlood;         //洪水合格
    int passY_sum;           //产流合格场次
    int passQ_sum;           //汇流合格场次
    int passFlood_sum;      //洪水合格场次
    double DC_sum;

    double[] Sa;           //流域初始表层蓄水量
    double[] Ua;           //流域初始下层蓄水量
    double[] Da;           //流域初始下层蓄水量与地下水库蓄水量之和


    public Out(Parameter par) {
        this.par = par;
    }

    public Out() {
    }

    /** 数字格式 **/       //2.5123  02.512 ...
    DecimalFormat bfb = new DecimalFormat("#00.0");//更改格式，保留一位小数
//    DecimalFormat bfb = new DecimalFormat("#00.00");

    //决定保留几位小数
    //DecimalFormat能够分析和格式化任意语言环境中的数,#--如果不存在则显示为空，存在则显示数的值;0--如果不存在则显示为0，存在则显示数的值。
    DecimalFormat zs = new DecimalFormat("#000");

    DecimalFormat onedf = new DecimalFormat("#00.0");//更改格式，保留一位小数
//    DecimalFormat onedf = new DecimalFormat("#00.000");

    DecimalFormat one = new DecimalFormat("#00");    //合格为1，不合格为0
    DecimalFormat threedf = new DecimalFormat("#0.000");    //平均确定性

    @Test
    public void testSimulation() {     //Simulation--模拟
        //添加形参Parameter par，调研方法时实参为par_best
//        Parameter par = new Parameter();          //有关最后选用的参数不是最优的问题，原因出在这里，原程序的作用是--原始输出，即par[0]，采用原程序的话可以将Parameter中的参数人为修改为最优
        ProgramRuntimeData prd = new ProgramRuntimeData(par);
        ModuleOfDHF dhf = new ModuleOfDHF(par, prd);
        dhf.run();

        Result re = TargetFunction.target(prd);

//        /************  生成.txt文件  **************/
//        for (int n = 0; n < ProgramRuntimeData.getFloodNum(); n++) {
//            int length = prd.getQ(n).length;
//
//            String temp = "Flood_ID" + ProgramRuntimeData.getStcd(n) + ".txt";
//            File Q = new File(temp);            //输出的文件--Flood_ID19590722.txt
//            BufferedWriter bout = null;
//            try {
//                FileWriter out = new FileWriter(Q);
//                bout = new BufferedWriter(out);
//                String str = null;
//                for (int j = 0; j < length; j++) {
//                    if (j == 0) {
//                        str = prd.getQ(n)[j] + "\n";
//                    } else {
//                        str += prd.getQ(n)[j] + "\n";
//                    }
//                }
//                bout.write(str);
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } finally {
//                if (bout != null) {
//                    try {
//                        bout.close();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }

        /************  生成.txt文件到指定文件夹  **************/
        String folderPath = "flood data"; // 指定文件夹名称
        File folder = new File(folderPath);
// 如果文件夹不存在，则创建
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (int n = 0; n < ProgramRuntimeData.getFloodNum(); n++) {
            int length = prd.getQ(n).length;

            // 构造文件路径
            String temp = folderPath + File.separator + "Flood_ID" + ProgramRuntimeData.getStcd(n) + ".txt";
            File Q = new File(temp); // 输出文件路径，例如 FloodData/Flood_ID19590722.txt
            BufferedWriter bout = null;

            try {
                FileWriter out = new FileWriter(Q);
                bout = new BufferedWriter(out);
                StringBuilder str = new StringBuilder();

                for (int j = 0; j < length; j++) {
                    str.append(prd.getQ(n)[j]).append("\n");
                }
                bout.write(str.toString());

            } catch (IOException e) {
                e.printStackTrace(); // 打印异常
            } finally {
                if (bout != null) {
                    try {
                        bout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        totalY_real = re.getTotalY_real();    //实际总径流深
        totalY_simu = re.getTotalY_simu();    //模拟总径流深
        totalQ_real = re.getTotalQ_real();    //洪水实际的洪量
        totalQ_simu = re.getTotalQ_simu();    //洪水模拟的洪量
        absHL = re.getAbsHL();                //洪量绝对误差：洪水模拟的洪量减去洪水实际的洪量
        DC = re.getDC();                    //确定性系数
        absY = re.getAbsY();                //产流绝对误差
        relaY = re.getRelaY();                //产流相对误差
        totalP = re.getTotalP();            //总降雨量
        alefa = re.getAlefa();                //实测产流系数
        QMax_real = re.getQMax_real();        //实际洪峰
        QMax_simu = re.getQMax_simu();        //模拟洪峰
        absQMax = re.getAbsQMax();            //洪峰绝对误差
        relaQMax = re.getRelaQMax();        //洪峰相对误差
        //注：利用洪峰在流量数组中的角标表示，在时间间隔一定的情况下，即为峰现时间
        QMax_real_time = re.getQMax_real_time();        //实际峰现时间
        QMax_simu_time = re.getQMax_simu_time();        //模拟峰现时间
        QMax_time_differ = re.getQMax_time_differ();    //峰现时间差

        passY = re.getPassY();            //产流是否合格，合格为1，不合格为0
        passQ = re.getPassQ();            //汇流是否合格，合格为1，不合格为0
        passFlood = re.getPassFlood();    //洪水合格
        passY_sum = re.getPassY_sum();    //产流合格场次
        passQ_sum = re.getPassQ_sum();    //汇流合格场次
        passFlood_sum = re.getPassFlood_sum();    //洪水合格场次
        DC_sum = re.getDC_sum();

        Sa = prd.getSa();    //流域初始表层蓄水量
        Ua = prd.getUa();    //流域初始下层蓄水量
        Da = prd.getDa();    //流域初始下层蓄水量与地下水库蓄水量之和

        System.out.println();
        System.out.println("~~~~~~~~~~~ 洪水总场次 = " + ProgramRuntimeData.getFloodNum() + " ,产流合格场次 = " + passY_sum + " ,产流合格率 = " + onedf.format((passY_sum * 100.0) / (ProgramRuntimeData.getFloodNum() * 1.0)) + "%"+ " ,平均确定性系数 = " + threedf.format(DC_sum/ProgramRuntimeData.getFloodNum()));
        System.out.println("——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————");
        System.out.println("洪" + "\t\t\t" + "预报径流深" + "\t" + "实际径流深" + "\t" + "R绝对误差" + "\t" + "R相对误差" + "\t" + "含水量" + "\t\t" + "Sa0" + "\t\t\t" + "Ua0" + "\t\t\t" + "Da0" + "\t\t\t" + "是否" + "\t\t" + "实际产流" + "\t\t" + "洪量模拟值" + "\t" + "实际洪量" + "\t\t" + "洪量绝对误差");
        System.out.println("      号" + "\t" + "R(mm)" + "\t\t" + "R0(mm) " + "\t\t" + "(mm)" + "\t\t" + "(%)" + "\t\t\t" + "W(mm)" + "\t\t" + "(mm)" + "\t\t" + "(mm)" + "\t\t" + "(mm)" + "\t\t" + "合格" + "\t\t" + "系数" + "\t\t\t" + "(m3)" + "\t\t" + "(m3)" + "\t\t" + "(m3)");
        System.out.println("——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————");
        for (int m = 0; m < ProgramRuntimeData.getFloodNum(); m++) {

            System.out.println(ProgramRuntimeData.getStcd(m) + "\t" + onedf.format(totalY_simu[m]) + "\t\t" + onedf.format(totalY_real[m]) + "\t\t" +
                    bfb.format(absY[m]) + "\t\t" + bfb.format(relaY[m]) + "\t\t" + onedf.format(Ua[m] + Sa[m] + Da[m])
                    + "\t\t" + onedf.format(Sa[m]) + "\t\t" + onedf.format(Ua[m]) + "\t\t" + onedf.format(Da[m]) + "\t\t" + one.format(passY[m])
                    + "\t\t" + bfb.format(alefa[m]) + "\t\t" + zs.format(totalQ_simu[m]) + "\t\t" + zs.format(totalQ_real[m]) + "\t\t" + zs.format(absHL[m]));

        }
        System.out.println("——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————");
        System.out.println();
        System.out.println("~~~~~~~~~~~ 洪水总场次 = " + ProgramRuntimeData.getFloodNum() + " ,汇流合格场次 = " + passQ_sum + " ,汇流合格率 = " + onedf.format((passQ_sum * 100.0) / (ProgramRuntimeData.getFloodNum() * 1.0)) + "%");
        System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————————————");
        System.out.println("洪" + "\t\t\t" + "洪峰模拟值" + "\t" + "洪峰实测值" + "\t" + "洪峰绝对误差" + "\t" + "洪峰相对误差" + "\t" + "峰现时间" + "\t\t" + "峰现时间" + "\t\t" + "峰现时间" + "\t\t" + "确定性" + "\t\t" + "是否");
        System.out.println("      号" + "\t" + "(m3/s)" + "\t\t" + "(m3/s)" + "\t\t" + "(m3/s)" + "\t\t" + "(%)" + "\t\t\t" + "模拟" + "\t\t\t" + "实测" + "\t\t\t" + "绝对误差" + "\t\t" + "系数" + "\t\t\t" + "合格");
        System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————————————");

        for (int m = 0; m < ProgramRuntimeData.getFloodNum(); m++) {

            System.out.println(ProgramRuntimeData.getStcd(m) + "\t" + zs.format(QMax_simu[m]) + " \t\t" + zs.format(QMax_real[m]) + " \t\t" + zs.format(absQMax[m]) + " \t\t" + bfb.format(relaQMax[m])
                    + "\t\t" + one.format(QMax_simu_time[m]) + "\t\t\t" + one.format(QMax_real_time[m]) + "\t\t\t" + one.format(QMax_time_differ[m]) + "\t\t\t" + onedf.format(DC[m]) + "\t\t" + one.format(passQ[m]));
        }
        System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————————————");
        System.out.println();
    }

    public void excelSimulation() {     //Simulation--模拟

        System.out.println();
        System.out.println("~~~~~~~~~~~ 洪水总场次 = " + ProgramRuntimeData.getFloodNum() + " ,产流合格场次 = " + passY_sum + " ,产流合格率 = " + onedf.format((passY_sum * 100.0) / (ProgramRuntimeData.getFloodNum() * 1.0)) + "%"+ " ,平均确定性系数 = " + threedf.format(DC_sum/ProgramRuntimeData.getFloodNum()));
        System.out.println("———————————————————————————————————————————————————————————————————");
        System.out.println("洪" + "\t" + "预报径流深" + "\t" + "实际径流深" + "\t" + "R绝对误差" + "\t" + "R相对误差" + "\t" + "含水量" + "\t" + "Sa0" + "\t" + "Ua0" + "\t" + "Da0" + "\t" + "是否" + "\t" + "实际产流" + "\t" + "洪量模拟值" + "\t" + "实际洪量" + "\t" + "洪量绝对误差");
        System.out.println("      号" + "\t" + "R(mm)" + "\t" + "R0(mm) " + "\t" + "(mm)" + "\t" + "(%)" + "\t" + "W(mm)" + "\t" + "(mm)" + "\t" + "(mm)" + "\t" + "(mm)" + "\t" + "合格" + "\t" + "系数" + "\t" + "(m3)" + "\t" + "(m3)" + "\t" + "(m3)");
        System.out.println("———————————————————————————————————————————————————————————————————");

        for (int m = 0; m < ProgramRuntimeData.getFloodNum(); m++) {

            System.out.println(ProgramRuntimeData.getStcd(m) + "\t" + onedf.format(totalY_simu[m]) + "\t" + onedf.format(totalY_real[m]) + "\t" +
                    bfb.format(absY[m]) + "\t" + bfb.format(relaY[m]) + "\t" + onedf.format(Ua[m] + Sa[m] + Da[m])
                    + "\t" + onedf.format(Sa[m]) + "\t" + onedf.format(Ua[m]) + "\t" + onedf.format(Da[m]) + "\t" + one.format(passY[m])
                    + "\t" + bfb.format(alefa[m]) + "\t" + zs.format(totalQ_simu[m]) + "\t" + zs.format(totalQ_real[m]) + "\t" + zs.format(absHL[m]));

        }
        System.out.println("———————————————————————————————————————————————————————————————————");
        System.out.println();

        System.out.println("~~~~~~~~~~~ 洪水总场次 = " + ProgramRuntimeData.getFloodNum() + " ,汇流合格场次 = " + passQ_sum + " ,汇流合格率 = " + onedf.format((passQ_sum * 100.0) / (ProgramRuntimeData.getFloodNum() * 1.0)) + "%");
        System.out.println("————————————————————————————————————————————————");
        System.out.println("洪" + "\t" + "洪峰模拟值" + "\t" + "洪峰实测值" + "\t" + "洪峰绝对误差" + "\t" + "洪峰相对误差" + "\t" + "峰现时间" + "\t" + "峰现时间" + "\t" + "峰现时间" + "\t" + "确定性" + "\t" + "是否");
        System.out.println("      号" + "\t" + "(m3/s)" + "\t" + "(m3/s)" + "\t" + "(m3/s)" + "\t" + "(%)" + "\t" + "模拟" + "\t" + "实测" + "\t" + "绝对误差" + "\t" + "系数" + "\t" + "合格");
        System.out.println("————————————————————————————————————————————————");

        for (int m = 0; m < ProgramRuntimeData.getFloodNum(); m++) {

            System.out.println(ProgramRuntimeData.getStcd(m) + "\t" + zs.format(QMax_simu[m]) + " \t" + zs.format(QMax_real[m]) + " \t" + zs.format(absQMax[m]) + " \t" + bfb.format(relaQMax[m])
                    + "\t" + one.format(QMax_simu_time[m]) + "\t" + one.format(QMax_real_time[m]) + "\t" + one.format(QMax_time_differ[m]) + "\t" + onedf.format(DC[m]) + "\t" + one.format(passQ[m]));
        }
        System.out.println("————————————————————————————————————————————————");
        System.out.println();
    }
    public void resultSimulation(){//方法testSimulation的一部分，只参与运算，不打印结果
        ProgramRuntimeData prd = new ProgramRuntimeData(par);
        ModuleOfDHF dhf = new ModuleOfDHF(par, prd);
        dhf.run();

        Result re = TargetFunction.target(prd);

        totalY_real = re.getTotalY_real();    //实际总径流深
        totalY_simu = re.getTotalY_simu();    //模拟总径流深
        totalQ_real = re.getTotalQ_real();    //洪水实际的洪量
        totalQ_simu = re.getTotalQ_simu();    //洪水模拟的洪量
        absHL = re.getAbsHL();                //洪量绝对误差：洪水模拟的洪量减去洪水实际的洪量
        DC = re.getDC();                    //确定性系数
        absY = re.getAbsY();                //产流绝对误差
        relaY = re.getRelaY();                //产流相对误差
        totalP = re.getTotalP();            //总降雨量
        alefa = re.getAlefa();                //实测产流系数
        QMax_real = re.getQMax_real();        //实际洪峰
        QMax_simu = re.getQMax_simu();        //模拟洪峰
        absQMax = re.getAbsQMax();            //洪峰绝对误差
        relaQMax = re.getRelaQMax();        //洪峰相对误差
        //注：利用洪峰在流量数组中的角标表示，在时间间隔一定的情况下，即为峰现时间
        QMax_real_time = re.getQMax_real_time();        //实际峰现时间
        QMax_simu_time = re.getQMax_simu_time();        //模拟峰现时间
        QMax_time_differ = re.getQMax_time_differ();    //峰现时间差

        passY = re.getPassY();            //产流是否合格，合格为1，不合格为0
        passQ = re.getPassQ();            //汇流是否合格，合格为1，不合格为0
        passFlood = re.getPassFlood();    //洪水合格
        passY_sum = re.getPassY_sum();    //产流合格场次
        passQ_sum = re.getPassQ_sum();    //汇流合格场次
        passFlood_sum = re.getPassFlood_sum();    //洪水合格场次
        DC_sum = re.getDC_sum();

        Sa = prd.getSa();    //流域初始表层蓄水量
        Ua = prd.getUa();    //流域初始下层蓄水量
        Da = prd.getDa();    //流域初始下层蓄水量与地下水库蓄水量之和
    }

    public int getPassY_sum() {
        return passY_sum;
    }

    public void setPassY_sum(int passY_sum) {
        this.passY_sum = passY_sum;
    }

    public int getPassQ_sum() {
        return passQ_sum;
    }

    public void setPassQ_sum(int passQ_sum) {
        this.passQ_sum = passQ_sum;
    }

    public int getPassFlood_sum() {
        return passFlood_sum;
    }

    public void setPassFlood_sum(int passFlood_sum) {
        this.passFlood_sum = passFlood_sum;
    }

    public double getDC_sum() {
        return DC_sum;
    }

    public void setDC_sum(double DC_sum) {
        this.DC_sum = DC_sum;
    }
}
