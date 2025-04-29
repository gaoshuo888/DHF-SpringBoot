package com.example.dhf_springboot.model.dhf.module;


import com.example.dhf_springboot.model.dhf.data.Parameter;
import com.example.dhf_springboot.model.dhf.data.ProgramRuntimeData;

import static java.lang.Double.isNaN;

public class ModuleOfDHF {

    private ProgramRuntimeData prd;

    private double area = Parameter.getArea();
    private int timeSpan = Parameter.getTimeSpan();
    private double L = Parameter.getL();

    private double KC;            //流域蒸散发能力与大水体蒸散发量ER的比值
    private double g;            //不透水面积比例
    private double S0;            //表层蓄水容量
    private double U0;            //下层蓄水容量
    private double D0;            //下层蓄水容量与地下水蓄水容量之和
    private double a;            //表层蓄水容量分配曲线形状参数
    private double B;            //时段平均下渗率流域分配曲线形状参数
    private double K2;            //下层下渗曲线曲率系数
    private double B0;            //最大河长与特征河长之比
    private double K0;            //待定参数，用于计算TM汇流曲线底宽
    private double K;            //前期净雨影响程度衰减系数
    private double CC;            //汇流曲线形状参数
    private double DD;            //汇流曲线形状参数
    private double COE;            //COE=T0/TM 地下汇流曲线参数
    private double N;            //地下径流与壤中流汇流曲线底宽的比例系数
    private double KW;            //yl与RL的比值


    public ModuleOfDHF() {
        super();
    }

    public ModuleOfDHF(Parameter par, ProgramRuntimeData prd) {

        super();

        this.prd = prd;

        KC = par.getKC();
        g = par.getG();
        S0 = par.getS0();
        U0 = par.getU0();
        D0 = par.getD0();
        a = par.getA();
        B = par.getB();
        K2 = par.getK2();
        B0 = par.getB0();
        K0 = par.getK0();
        K = par.getK();
        CC = par.getCC();
        DD = par.getDD();
        COE = par.getCOE();
        N = par.getN();
        KW = par.getKW();
    }


    //主要用于计算洪水开始时的表层蓄水量S0、下层蓄水量U0、下层蓄水量与地下水库蓄水容量之和D0

    /**
     * 日模型
     **/
    private void methodOfRunoffYield_day(int n) {//n--洪水场次，[0,floodNum)

        double[] P = ProgramRuntimeData.getP_day(n);        //日降雨量

        double[] ER = ProgramRuntimeData.getER_day(n);        //水面日蒸散发能力,是否重新执行GetEM

        int length = P.length;    //时间长度,降雨持续了几天

        double ED = 0;    //雨间蒸散发量
        double PE = 0;    //净雨强

        /** 原程序g = 0 是否错误 **/
//        double g = 0;   //之前的程序日模型g一直为0，错误? 发现程序不一定是错的，g=0，前期按不产流计算Sa和Ua
        double y0 = 0;  //不透水面积上的直接径流

        /** Sa初始值按照0计算是否正确？？ **/ //若资料较少，计算的Pa偏小，预报R偏小。
        double Sa = S0 / 3;    //流域表层蓄水量  //数字变蓝说明用到了该数字
        double Ua = U0 / 2;    //流域下层蓄水量
        double Da = 0;    //流域下层蓄水量与地下水库蓄水量之和
//        double Sa = 0;    //流域表层蓄水量  //数字变蓝说明用到了该数字
//        double Ua = 0;    //流域下层蓄水量

        double PC = 0;    //净渗雨强
        double R = 0;    //下渗强度
        double Sm = 0;    //流域表层蓄水量对应的表层蓄水容容量曲线纵坐标值
        double Z2 = 1 - Math.exp(-K2 * timeSpan);
        double Un = 0;    //时段平均下层下渗率曲线的初始纵坐标值Z2*Un中的Un

        double EC = 0;    //雨间蒸发量大于时段降雨量的时，雨间蒸发量扣除降雨量之后蒸发量
        double EB = 0;  //已蒸发量对应的表层蓄水容容量曲线纵坐标值
        double ES = 0;    //流域表层蒸散发量
        double EU = 0;    //流域下层蒸散发量

        for (int i = 0; i < length; i++) { //length代表第几天

            ED = KC * ER[i];      //ED = KC*ER/C;  ED是日蒸散发量，ER[i]是大水体日蒸发

            PE = P[i] - ED;

            if (PE > 0) {

                y0 = g * PE;

                PC = PE - y0;

                Sm = a * S0 * (1 - Math.pow((1 - Sa / S0), 1 / a));        //Sa=SM/a[1-(1-Sm/SM)]

                if (Sm + PC < a * S0) {
                    R = PC + Sa - S0 + S0 * Math.pow((1 - (Sm + PC) / (a * S0)), a);
                } else {
                    R = PC - (S0 - Sa);
                }

                Sa = Sa + PC - R;        /**疑似错误，？？？**/

                Un = B * U0 * (1 - Math.pow(1 - Ua / U0, 1 / B));

                if (Z2 * Un + R < Z2 * B * U0) {
                    Ua = Ua + Z2 * (U0 - Ua) - Z2 * U0 * Math.pow(1 - (Z2 * Un + R) / (Z2 * B * U0), B);
                } else {
                    Ua = Ua + Z2 * (U0 - Ua);
                }

                EB = 0;

            } else {

                EC = ED - P[i];
                ES = S0 * (Math.pow((1 - EB / (a * S0)), a) - Math.pow((1 - (EB + EC) / (a * S0)), a));

                //当表层蓄水量大于蒸发量时，直接减去蒸发量；小于时，表层蓄水量变为0
                if (Sa > ES) {
                    Sa -= ES;
                    EU = (EC - ES) * Ua / U0;
                } else {
                    Sa = 0;
                    EU = (EC - Sa) * Ua / U0;
                }

                if (Ua > EU) {
                    Ua -= EU;
                } else {
                    Ua = 0;
                }
                EB += EC;

                //添加限制条件，Ua必须小于U0
                if(Ua > U0){
                    Ua = U0;
                }

            }
        }

        Da = D0 * (1 - Math.pow(1 - Ua / U0, U0 / D0));

        prd.setSa(n, Sa);//得到Sa
        prd.setUa(n, Ua);//得到Ua
        prd.setDa(n, Da);//得到Da
    }


    /**
     * 次洪模型
     **/
    private void methodOfRunoffYield_timeSpan(int n) {
        double[] P = ProgramRuntimeData.getP_timeSpan(n);       //时段降雨量
        double[] ER = ProgramRuntimeData.getER_timeSpan(n);    //水面时段蒸散发能力

        int length = P.length;
        double ED = 0;    //雨间蒸散发量
        double PE = 0;    //净雨强
        double[] y0 = new double[length];      //不透水面积上的直接径流
        double[] y = new double[length];      //总径流（表层与地下径流）
        double[] yu = new double[length];    //表层流（即地面径流与壤中流之和）
        double[] yl = new double[length];    //地下径流
        double Sa = prd.getSa(n);    //流域表层蓄水量
        double Ua = prd.getUa(n);    //流域下层蓄水量

        double PC = 0;            //净渗雨强
        double Sm = 0;            //流域表层蓄水量对应的表层蓄水容容量曲线纵坐标值
        double R = 0;            //下渗强度
        double RL = 0;            //地下水库下渗强度

        double EC = 0;            //雨间蒸发量大于时段降雨量的时，雨间蒸发量扣除降雨量之后蒸发量
        double EB = 0;           //已蒸发量对应的表层蓄水容容量曲线纵坐标值
        double ES = 0;            //流域表层蒸散发量
        double EU = 0;            //流域下层蒸散发量

        double Z2 = 1 - Math.exp(-K2 * timeSpan);
        double Z1 = 1 - Math.exp(-K2 * timeSpan * U0 / D0);
        double Un = 0;            //时段平均下层下渗率曲线的初始纵坐标值Z2*Un中的Un
        double Dn = 0;            //时段平均下流域总下渗率曲线的初始纵坐标值Z1*Dn中的Dn

        for (int i = 0; i < length; i++) {

            ED = KC * ER[i];
            PE = P[i] - ED;

            if (PE > 0) {
                y0[i] = g * PE;

                PC = PE - y0[i];

                Sm = a * S0 * (1 - Math.pow((1 - Sa / S0), 1 / a));

                if (Sm + PC < a * S0) {
                    R = PC + Sa - S0 + S0 * Math.pow((1 - (Sm + PC) / (a * S0)), a);
                } else {
                    R = PC - (S0 - Sa);
                }
                Sa = Sa + PC - R;

                Un = B * U0 * (1 - Math.pow(1 - Ua / U0, 1 / B));

                Dn = B * D0 * (1 - Math.pow(1 - Ua / U0, U0 / (B * D0)));

                if (Z1 * Dn + R < Z1 * B * D0) {//if(Z1*Dn<Z1*B*D0) {
                    yu[i] = R - Z1 * D0 * Math.pow(1 - Ua / U0, U0 / D0) + Z1 * D0 * Math.pow(1 - (Z1 * Dn + R) / (Z1 * B * D0), B);
                } else {
                    yu[i] = R - Z1 * D0 * Math.pow(1 - Ua / U0, U0 / D0);
                }
//				System.out.println("yu[" + i + "] = " + yu[i]);

                //找到yu[]=NaN的位置
                if (isNaN(yu[i])){
                    System.out.println("--------+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("n=" + n + " yu[" + i + "]=" + yu[i] );
                    //第二个公式有问题 , yu[i] = R - Z1 * D0 * Math.pow(1 - Ua / U0, U0 / D0);
//                    if (Z1 * Dn + R < Z1 * B * D0){
//                        System.out.println("1");
//                    }else{
//                        System.out.println("第二公式");
//                    }
                    System.out.println("R=" + R + " Z1=" + Z1 + " D0=" + D0 + " Ua=" + Ua + " U0=" + U0);
//                    System.out.println(Math.pow(1 - Ua / U0, U0 / D0));sss

//                    if (Z2 * Un + R < Z2 * B * U0){
//                        System.out.println("1-----------------------------------------------------");
//                    }else{
//                        System.out.println("dier");
//                    }

                }

                if (Z2 * Un + R < Z2 * B * U0) {
                    y[i] = R + Z2 * (Ua - U0) + Z2 * U0 * Math.pow(1 - (Z2 * Un + R) / (Z2 * B * U0), B);
                } else {
                    y[i] = R - Z2 * (U0 - Ua);
                }

                Ua = Ua + R - y[i];

                //限制条件，Ua必须小于U0
                if(Ua > U0){
                    Ua = U0;
                }

                RL = y[i] - yu[i];
                yl[i] = KW * RL;

                EB = 0;

            } else {

                EC = ED - P[i];

                ES = S0 * (Math.pow((1 - EB / (a * S0)), a) - Math.pow((1 - (EB + EC) / (a * S0)), a));

                //当表层蓄水量大于蒸发量时，直接减去蒸发量；小于时，表层蓄水量变为0
                if (Sa > ES) {
                    Sa -= ES;
                    EU = (EC - ES) * Ua / U0;
                } else {
                    Sa = 0;
                    EU = (EC - Sa) * Ua / U0;
                }

//                if(EU < 0){//出现EU为负值
//                    System.out.println("+++++++++++++++++++++++++++++++++++++++" + EU + "  ED =" + ED);
//                }

                if (Ua > EU) {
                    Ua -= EU;
                } else {
                    Ua = 0;
                }

                //限制条件。Ua必须小于U0
                if(Ua > U0){
                    Ua = U0;
                }

                EB += EC;
            }
        }

        prd.setY(n, y);//n--代表场次洪水数
        prd.setY0(n, y0);
        prd.setYu(n, yu);
        prd.setYl(n, yl);

    }


    private void mothodOfConfluence(int n) {

        double[] y0 = prd.getY0(n);    //不透水面积上的直接径流
        double[] yu = prd.getYu(n);    //表层流（即地面径流与壤中流之和）
        double[] yl = prd.getYl(n);    //地下径流
        int length = y0.length;
        double[] yt = new double[length];  //总径流

        for (int i = 0; i < length; i++) {
            yt[i] = y0[i] + yu[i] + yl[i];
        }

        double[][] q = new double[length][];    //各时段的总径流在出口段面处形成的经验单位线，即为出口流量过程
        double ya = 0;        //前期影响净雨量
        double LB = L / B0;    //特征河长
        for (int j = 0; j < length; j++) {
            if (yt[j] != 0) {

                double w = yt[j] * area / (3.6 * timeSpan);        //单位线纵高值之和

                double TM = LB * Math.pow(ya + yt[j], -K0);        //汇流曲线底宽

                //反映汇流曲线形状的参数，原AA计算公式
                double AA = CC / (DD * Math.pow((Math.PI * COE) / N, DD - 1) * Math.tan((Math.PI * COE) / N));
                /** 修改AA的计算公式 **/ //？？是否正确 (×)
//                double AA = CC / DD * Math.pow((Math.PI * COE) / N, DD - 1) * Math.tan((Math.PI * COE) / N);

                double tempt = 0;
                for (int i = 0; (i * timeSpan) <= (N * TM); i++) {        /** i*timeSpan **/  //是否可以用于Z1和Z2的计算
                    tempt += Math.exp(-AA * Math.pow((Math.PI * i * timeSpan) / (N * TM), DD)) * Math.pow(Math.sin((Math.PI * i * timeSpan) / (N * TM)), CC);

                    /** 《+》 **/
                    if((i != 0) &&(tempt == 0)){
                        System.out.println("tempt = 0"+ "  i = " + i+" ①= " + Math.exp(-AA * Math.pow((Math.PI * i * timeSpan) / (N * TM), DD))
                        + " ②= " + Math.pow(Math.sin((Math.PI * i * timeSpan) / (N * TM)), CC));
//                        System.out.println("AA= " + AA + "  DD= " +DD+"  N * TM= " + N * TM);
//                        System.out.println("-AA * Math.pow((Math.PI * i * timeSpan) / (N * TM), DD)= " + (-AA * Math.pow((Math.PI * i * timeSpan) / (N * TM), DD)));
                        System.out.println("AA= " + AA + " CC= "+CC+ " DD= "+DD +" COE= "+COE+" (Math.PI * COE) / N= "+(Math.PI * COE) / N+
                                "  式1= " + Math.pow((Math.PI * COE) / N, DD - 1));
                    }
                }
                double K3 = (N * TM * w) / tempt;  //汇流曲线的比例系数
                int q_length = (int) (N * TM / timeSpan);
                q[j] = new double[q_length];

                for (int t = 0; t < q_length; t++) {
                    q[j][t] = K3 / (N * TM) * Math.exp(-AA * Math.pow((Math.PI * t * timeSpan) / (N * TM), DD)) * Math.pow(Math.sin((Math.PI * t * timeSpan) / (N * TM)), CC);
//					System.out.println("q: " + q[j][t] + "  j: " + j + "   t:" + t );

                    /** 《+》 **/
//                    if(isNaN(q[j][t])){
//                        System.out.println("TM= "+TM+ " K3="+K3+"  N * TM= "+(N * TM)+ "  tempt= "+tempt+"      ////////////////////85552552222222222222222222222222222222222");
//                        System.out.println(Math.exp(-AA * Math.pow((Math.PI * t * timeSpan) / (N * TM), DD)) + "  " +Math.pow(Math.sin((Math.PI * t * timeSpan) / (N * TM)), CC));
//                        System.out.println(Math.exp(-AA * Math.pow((Math.PI * t * timeSpan) / (N * TM), DD)) * Math.pow(Math.sin((Math.PI * t * timeSpan) / (N * TM)), CC));
//                    }else{
//                        System.out.println("TM= "+TM+ " K3="+K3+ "  tempt= "+tempt+"       ---------***********************85552552222222222222222222222222222222222");
//                    }

                }
            } else {
                q[j] = new double[1];
                q[j][0] = 0;
            }

            ya = K * (ya + yt[j]);

        }

        //将各时段的产生的流量过程叠加
        double[] Q = new double[length];
        for (int k = 0; k < Q.length; k++) {

            Q[k] = 0;

            for (int l = 0; l <= k; l++) {
                if ((k - l) < q[l].length) {
                    Q[k] += q[l][k - l];

                    /** 《+》 **/
//                    if(isNaN(Q[k])){
//                        System.out.println("k="+k+"   q["+l+"]["+(k - l)+"]="+q[l][k - l]+"    ~~~~2222222222222222222222222222222222222222222222222222222222222222222222");
//                    }
                }
            }
        }

        prd.setQ(n, Q);

        for (int i = 0; i < prd.getQ(n).length; i++) {//prd.getQ(n)[i]出现NaN
            if(isNaN(prd.getQ(n)[i])){
                System.out.println(i + "    ~~88888888888888888888888888888888888888888888888888888888");
            }
        }

        /**模拟洪水洪量**/
//        System.out.println("n= " + n+"   洪水洪号：" + prd.getStcd()[n] + "------------------------------------");
//
//        for (int i = 0; i < prd.getQ(n).length; i++) {
//            System.out.println(prd.getQ(n)[i]);
//        }
    }

    public void run() {

        int floodNum = ProgramRuntimeData.getFloodNum();    //洪水场次数

        for (int n = 0; n < floodNum; n++) {

            this.methodOfRunoffYield_day(n);
            this.methodOfRunoffYield_timeSpan(n);
            this.mothodOfConfluence(n);

        }
    }

}
