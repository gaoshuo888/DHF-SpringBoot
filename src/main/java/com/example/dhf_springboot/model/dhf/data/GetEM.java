package com.example.dhf_springboot.model.dhf.data;

import java.util.Calendar;
import java.util.Date;


public class GetEM {                     //EM蒸发

    private double[] value = {5, 40, 1000}; //分级蒸发日降雨量分级点

    //每个月日蒸散发量分级，数组长度为3
    private double[] E1;        //E1 = {par.getE1(),par.getE10(),par.getE11()}  E10和E11的作用
    private double[] E2;
    private double[] E3;
    private double[] E4;
    private double[] E5;
    private double[] E6;
    private double[] E7;
    private double[] E8;
    private double[] E9;
    private double[] E10;
    private double[] E11;
    private double[] E12;


    public GetEM() {
        super();
    }


    public GetEM(double[] E1, double[] E2, double[] E3, double[] E4, double[] E5, double[] E6, double[] E7, double[] E8, double[] E9) {
//		构造器，初始化
        this.E1 = E1;
        this.E2 = E2;
        this.E3 = E3;
        this.E4 = E4;
        this.E5 = E5;
        this.E6 = E6;
        this.E7 = E7;
        this.E8 = E8;
        this.E9 = E9;
    }


    public double[] getValue() {
        return value;
    }

    public void setValue(double[] value) {
        this.value = value;
    }

    public double[] getE1() {
        return E1;
    }

    public void setE1(double[] e1) {
        E1 = e1;
    }

    public double[] getE2() {
        return E2;
    }

    public void setE2(double[] e2) {
        E2 = e2;
    }

    public double[] getE3() {
        return E3;
    }

    public void setE3(double[] e3) {
        E3 = e3;
    }

    public double[] getE4() {
        return E4;
    }

    public void setE4(double[] e4) {
        E4 = e4;
    }

    public double[] getE5() {
        return E5;
    }

    public void setE5(double[] e5) {
        E5 = e5;
    }

    public double[] getE6() {
        return E6;
    }

    public void setE6(double[] e6) {
        E6 = e6;
    }

    public double[] getE7() {
        return E7;
    }

    public void setE7(double[] e7) {
        E7 = e7;
    }

    public double[] getE8() {
        return E8;
    }

    public void setE8(double[] e8) {
        E8 = e8;
    }

    public double[] getE9() {
        return E9;
    }

    public void setE9(double[] e9) {
        E9 = e9;
    }

    public double[] getE10() {
        return E10;
    }

    public void setE10(double[] e10) {
        E10 = e10;
    }

    public double[] getE11() {
        return E11;
    }

    public void setE11(double[] e11) {
        E11 = e11;
    }

    public double[] getE12() {
        return E12;
    }

    public void setE12(double[] e12) {
        E12 = e12;
    }

    public double f_emt(double p, Date dt_rain, int timeSpan) {//得到该时刻的时段蒸发能力，

        //timeSpan—表示时段间隔,emt—表示各月蒸发能力，
        //p—表示时段降雨，dt_rain—表示降雨时间

        double Ev[];
        Calendar time = Calendar.getInstance();
        time.setTime(dt_rain);                            //降雨现在时间
        int i_month = time.get(Calendar.MONTH) + 1;    //得到月数，+1是因为 Calendar.JANUARY开始是0，Calendar.MONTH返回值为(月份-1)
        switch (i_month) {
            case 1:
                Ev = E1;        //为什么E1会是数组？--E1{P<5mm,5mm<P<40mm,P>40mm}利用隶属度计算E
                break;        //日蒸发能力，break跳出switch
            case 2:
                Ev = E2;
                break;
            case 3:
                Ev = E3;
                break;
            case 4:
                Ev = E4;
                break;
            case 5:
                Ev = E5;
                break;
            case 6:
                Ev = E6;
                break;
            case 7:
                Ev = E7;
                break;
            case 8:
                Ev = E8;
                break;
            case 9:
                Ev = E9;
                break;
            case 10:
                Ev = E10;
                break;
            case 11:
                Ev = E11;
                break;
            case 12:
                Ev = E12;
                break;
            default:        //蒸发计算错误，计算时间超出4-10月
                Ev = null;
        }

        double p_day = p * 24 / timeSpan;                //将时段降雨转换成日降雨,p—表示时段降雨，timeSpan时段间隔
        double De[] = getEmtWeight(p_day, value);       //获取计算蒸发时，对小、中、大雨的隶属度
        double ev = 0;
//        System.out.println("月份i_month=" + i_month);

        for (int i = 0; i < De.length; i++) {

            ev += Ev[i] * De[i];            //日蒸发量:  隶属度*蒸发之和
//            System.out.println("Ev["+ i + "]="+ Ev[i]);
        }
        ev = ev * timeSpan / 24;       //换算成时段蒸发量
        return ev;

    }

    public double[] getEmtWeight(double P, double value[]) {//获取计算蒸发时，对小、中、大雨的隶属度 //P--p_day:日降雨
        int n = value.length;                //value = {5, 40, 1000}; //分级蒸发日降雨量分级点
        double U[] = new double[n];
        double Umin = 0;
        double Umid = 0;
        double Umax = 0;
        if (P <= 0) {
            Umin = 1;       //隶属度，属于小雨，Umin=1
            Umid = 0;
            Umax = 0;
        } else if (P > 0 && P < value[0]) {
            Umin = 1 - (P / value[0]);
            Umid = P / value[0];
            Umax = 0;
        } else if (P >= value[0] && P < value[1]) {
            Umin = 0;
            Umid = (value[1] - P) / (value[1] - value[0]);
            Umax = (P - value[0]) / (value[1] - value[0]);
        } else {
            Umin = 0;
            Umid = 0;
            Umax = 1;
        }
        U[0] = Umin;
        U[1] = Umid;
        U[2] = Umax;
        return U;
    }

}
