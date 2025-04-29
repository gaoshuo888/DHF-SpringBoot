package com.example.dhf_springboot.model.dhf.data;

/**
 * @author
 */

public class Parameter {      //Parameter 参数、定义封装了一些参数

    private static double area = 692;        //流域面积，static类变量，(km2)
    private static int timeSpan = 1;        //时间步长
    private static double L = 94.9;            //流域最大河长,(km)

    //参数名称
    private static String[] parName = {"KC", "g", "S0", "U0", "D0", "a", "B", "K2", "B0", "K0", "K", "CC", "DD", "COE", "N", "KW", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "E10", "E11"};
    //参数下界
    private static double[] parL = {0.8, 0, 0, 0, 70, 0, 1.0, 0.2, 0.5, 0, 0.7, 0.5, 0.5, 0, 2.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    //参数上界
    private static double[] parU = {1, 1, 50, 90, 160, 5, 3.0, 0.8, 2.0, 0.8, 1, 4.0, 4.0, 0.8, 6.0, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 4, 3};
    //初始参数
    private static double[] par0 = {0.88, 0.05, 50, 65, 125, 4.6, 2.68, 0.23, 1.4, 0.08, 0.6, 0.72, 0.75, 0.1, 4.8, 1, 4.4, 4.4, 4.4, 4.6, 4.4, 4.8, 3.5, 5.4, 3.4, 2, 0.5};

//	private static double[] parU = {1,0.2,50,90,160,5,3,0.8,2,0.8,1,4,4,0.8,6,1,5.0,5.0,5.0,5.0,5.0,5.0,5.5,5.5,5.0,4.0,2.5};
//	private static double[] parL = {0.8,0,0,0,70,0,1,0.2,0.5,0,0.9,0.5,0.5,0,2,0,3.5,3.5,3.5,3.5,3.5,3.5,3.5,3.5,3.5,2.0,0};

    private static int parLength = parName.length;    //参数数量

    /**
     * @Parameter0 原始输出时使用的参数, 算法迭代的最优值存在var.csv文件里
     */
    private static double[] Parameter0 = {0.88,0.05,50.0,70.0,125.0,2.608940174447831,2.109885014768723,0.3230049410965677,1.06,0.3192807840518108,0.8928148262720721,0.6513904145045433,0.6327583878371554,0.13385240818081703,2.0806743166413186,0.9684306966291251,0.0,0.0,0.0,3.5,4.0,4.6,5.0,5.4,4.8,2.0,0.8
    };

    public static double[] getParameter0() {
        return Parameter0;
    }

    public static void setParameter0(double[] parameter0) {
        Parameter0 = parameter0;
    }

    //产流参数,会影响产汇流
    private double KC = Parameter0[0];        //流域蒸散发能力与大水体蒸散发量ER的比值
    private double g = Parameter0[1];        //不透水面积比例
    private double S0 = Parameter0[2];        //表层蓄水容量
    private double U0 = Parameter0[3];                        //下层蓄水容量
    private double D0 = Parameter0[4];    //下层蓄水容量与地下水蓄水容量之和
    private double a = Parameter0[5];      //表层蓄水容量分配曲线形状参数，A
    private double B = Parameter0[6];        //时段平均下渗率流域分配曲线形状参数
    private double K2 = Parameter0[7];        //下层下渗曲线曲率系数

    //汇流参数，只影响汇流
    private double B0 = Parameter0[8];        //最大河长与特征河长之比，LB=L/B0，LB--特征河长，L--流域最大河长
    private double K0 = Parameter0[9];        //待定参数，用于计算TM汇流曲线底宽，拟合线的斜率
    private double K = Parameter0[10];        //前期净雨影响程度衰减系数
    private double CC = Parameter0[11];        //汇流曲线形状参数
    private double DD = Parameter0[12];        //汇流曲线形状参数
    private double COE = Parameter0[13];    //COE=T0/TM 地下汇流曲线参数,??
    private double N = Parameter0[14];        //地下径流与壤中流汇流曲线底宽的比例系数，n
    //参数影响产汇流
    private double KW = Parameter0[15];        //yl与RL的比值,??

    //蒸发参数，？？？E4~E9 4~9月P<5mm的蒸发,实际用不到E1~E3，E10：4~9月5mm<P<40mm的蒸发，E11：4~9月P>40mm的蒸发
    /**
     * ----蒸发参数不敏感的原因：程序只用了Par0中的蒸发参数！！！----
     **/
    private double E1 = Parameter0[16];
    private double E2 = Parameter0[17];
    private double E3 = Parameter0[18];
    private double E4 = Parameter0[19];
    private double E5 = Parameter0[20];
    private double E6 = Parameter0[21];
    private double E7 = Parameter0[22];
    private double E8 = Parameter0[23];
    private double E9 = Parameter0[24];
    private double E10 = Parameter0[25];
    private double E11 = Parameter0[26];

    public Parameter() {    //构造器，无参初始化
        super();
    }


    public Parameter(double kC, double g, double s0, double u0, double d0, double a, double b, double k2, double b0,
                     double k0, double k, double cC, double dD, double cOE, double n, double kW, double e1, double e2, double e3,
                     double e4, double e5, double e6, double e7, double e8, double e9, double e10, double e11) {
        super();
        KC = kC;
        this.g = g;
        S0 = s0;
        U0 = u0;
        D0 = d0;
        this.a = a;
        B = b;
        K2 = k2;
        B0 = b0;
        K0 = k0;
        K = k;
        CC = cC;
        DD = dD;
        COE = cOE;
        N = n;
        KW = kW;
        E1 = e1;
        E2 = e2;
        E3 = e3;
        E4 = e4;
        E5 = e5;
        E6 = e6;
        E7 = e7;
        E8 = e8;
        E9 = e9;
        E10 = e10;
        E11 = e11;
    }

    public Parameter(Double[] x) {//构造器，传入参数Double[] x
        super();
        KC = x[0];
        this.g = x[1];
        S0 = x[2];
        U0 = x[3];
        D0 = x[4];
        this.a = x[5];
        B = x[6];
        K2 = x[7];
        B0 = x[8];
        K0 = x[9];
        K = x[10];
        CC = x[11];
        DD = x[12];
        COE = x[13];
        N = x[14];
        KW = x[15];
        E1 = x[16];
        E2 = x[17];
        E3 = x[18];
        E4 = x[19];
        E5 = x[20];
        E6 = x[21];
        E7 = x[22];
        E8 = x[23];
        E9 = x[24];
        E10 = x[25];
        E11 = x[26];
    }

    public double getKC() {
        return KC;
    }

    public void setKC(double kC) {
        KC = kC;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getS0() {
        return S0;
    }

    public void setS0(double s0) {
        S0 = s0;
    }

    public double getU0() {
        return U0;
    }

    public void setU0(double u0) {
        U0 = u0;
    }

    public double getD0() {
        return D0;
    }

    public void setD0(double d0) {
        D0 = d0;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return B;
    }

    public void setB(double b) {
        B = b;
    }

    public double getK2() {
        return K2;
    }

    public void setK2(double k2) {
        K2 = k2;
    }

    public double getB0() {
        return B0;
    }

    public void setB0(double b0) {
        B0 = b0;
    }

    public double getK0() {
        return K0;
    }

    public void setK0(double k0) {
        K0 = k0;
    }

    public double getK() {
        return K;
    }

    public void setK(double k) {
        K = k;
    }

    public double getCC() {
        return CC;
    }

    public void setCC(double cC) {
        CC = cC;
    }

    public double getDD() {
        return DD;
    }

    public void setDD(double dD) {
        DD = dD;
    }

    public double getCOE() {
        return COE;
    }

    public void setCOE(double cOE) {
        COE = cOE;
    }

    public double getN() {
        return N;
    }

    public void setN(double n) {
        N = n;
    }


    public double getKW() {
        return KW;
    }

    public void setKW(double kW) {
        KW = kW;
    }


    public double getE1() {
        return E1;
    }

    public void setE1(double e1) {
        E1 = e1;
    }

    public double getE2() {
        return E2;
    }

    public void setE2(double e2) {
        E2 = e2;
    }

    public double getE3() {
        return E3;
    }

    public void setE3(double e3) {
        E3 = e3;
    }

    public double getE4() {
        return E4;
    }

    public void setE4(double e4) {
        E4 = e4;
    }

    public double getE5() {
        return E5;
    }

    public void setE5(double e5) {
        E5 = e5;
    }

    public double getE6() {
        return E6;
    }

    public void setE6(double e6) {
        E6 = e6;
    }

    public double getE7() {
        return E7;
    }

    public void setE7(double e7) {
        E7 = e7;
    }

    public double getE8() {
        return E8;
    }

    public void setE8(double e8) {
        E8 = e8;
    }


    public double getE9() {
        return E9;
    }

    public void setE9(double e9) {
        E9 = e9;
    }

    public double getE10() {
        return E10;
    }

    public void setE10(double e10) {
        E10 = e10;
    }

    public double getE11() {
        return E11;
    }

    public void setE11(double e11) {
        E11 = e11;
    }

    public static String[] getParName() {
        return parName;
    }

    public static void setParName(String[] parName) {
        Parameter.parName = parName;
    }

    public static double[] getParU() {
        return parU;
    }

    public static void setParU(double[] parU) {
        Parameter.parU = parU;
    }

    public static double[] getParL() {
        return parL;
    }

    public static void setParL(double[] parL) {
        Parameter.parL = parL;
    }

    public static int getParLength() {
        return parLength;
    }

    public static void setParLength(int parLength) {
        Parameter.parLength = parLength;
    }

    public static double getArea() {
        return area;
    }

    public static void setArea(double area) {
        Parameter.area = area;
    }

    public static int getTimeSpan() {
        return timeSpan;
    }

    public static void setTimeSpan(int timeSpan) {
        Parameter.timeSpan = timeSpan;
    }

    public static double getL() {
        return L;
    }

    public static void setL(double l) {
        L = l;
    }


    public static double[] getPar0() {
        return par0;
    }

    public static void setPar0(double[] par0) {
        Parameter.par0 = par0;
    }


    @Override
    public String toString() {
        return "Parameter 输出参数:" + "\n" + "KC= " + '\t' + KC + "\n" + "g=  " + '\t' + g + "\n" + "S0= " + '\t' + S0 + "\n" + "U0= " + '\t' + U0 + "\n" + "D0= " + '\t' + D0 + "\n" + "a=  " + '\t' + a + "\n" + "B=  " + '\t' + B
                + "\n" + "K2= " + '\t' + K2 + "\n" + "B0= " + '\t' + B0 + "\n" + "K0= " + '\t' + K0 + "\n" + "K=  " + '\t' + K + "\n" + "CC= " + '\t' + CC + "\n" + "DD= " + '\t' + DD + "\n" + "COE= " + '\t' + COE
                + "\n" + "N=  " + '\t' + N + "\n" + "KW= " + '\t' + KW + "\n" + "E1= " + '\t' + E1 + "\n" + "E2= " + '\t' + E2 + "\n" + "E3= " + '\t' + E3 + "\n" + "E4= " + '\t' + E4 + "\n" + "E5= " + '\t' + E5
                + "\n" + "E6= " + '\t' + E6 + "\n" + "E7= " + '\t' + E7 + "\n" + "E8= " + '\t' + E8 + "\n" + "E9= " + '\t' + E9 + "\n" + "E10= " + '\t' + E10 + "\n" + "E11= " + '\t' + E11;
    }

    public String Parameter_best() {
        return "Parameter=[" + KC + "," + g + "," + +S0 + "," + U0 + "," + D0 + "," + a + "," + B + "," + K2 + "," + B0 + "," + K0 + "," + K + "," + CC + "," + DD + "," + COE
                + "," + N + "," + KW + "," + E1 + "," + E2 + "," + E3 + "," + E4 + "," + E5 + "," + E6 + "," + E7 + "," + E8 + "," + E9 + "," + E10 + "," + "\n" + E11 + "]";
    }

}
