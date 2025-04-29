package com.example.dhf_springboot.model.dhf.calibration;

import com.example.dhf_springboot.model.dhf.data.Parameter;
import com.example.dhf_springboot.model.dhf.data.ProgramRuntimeData;
import com.example.dhf_springboot.model.dhf.data.Result;
import com.example.dhf_springboot.model.dhf.tools.MathFun;

import static java.lang.Double.isNaN;

public class TargetFunction {     //目标函数

	public static Result target(ProgramRuntimeData prd) {

		int floodNum = ProgramRuntimeData.getFloodNum();//洪水场次数

		double timeSpan = Parameter.getTimeSpan();        //降雨时段数
		double area = Parameter.getArea();                //流域面积

		double[] totalY_real = new double[floodNum];    //实际总径流深
		double[] totalY_simu = new double[floodNum];    //模拟总径流深
		double[] totalQ_real = new double[floodNum];    //洪水实际的洪量
		double[] totalQ_simu = new double[floodNum];    //洪水模拟的洪量
		double[] absHL = new double[floodNum];            //洪量绝对误差：洪水模拟的洪量减去洪水实际的洪量
		double[] DC = new double[floodNum];                //确定性系数
		double[] absY = new double[floodNum];            //产流绝对误差
		double[] relaY = new double[floodNum];            //产流相对误差
		double[] totalP = new double[floodNum];            //总降雨量
		double[] alefa = new double[floodNum];            //实测产流系数
		double[] QMax_real = new double[floodNum];        //实际洪峰
		double[] QMax_simu = new double[floodNum];        //模拟洪峰
		double[] absQMax = new double[floodNum];        //洪峰绝对误差
		double[] relaQMax = new double[floodNum];        //洪峰相对误差
		//注：利用洪峰在流量数组中的角标表示，在时间间隔一定的情况下，即为峰现时间
		int[] QMax_real_time = new int[floodNum];        //实际峰现时间
		int[] QMax_simu_time = new int[floodNum];        //实际峰现时间
		int[] QMax_time_differ = new int[floodNum];        //峰现时间差

		int[] passY = new int[floodNum];        //产流合格
		int[] passQ = new int[floodNum];        //汇流合格
		int[] passFlood = new int[floodNum];    //洪水合格

		int passY_sum = 0;            //产流合格总场次
		int passQ_sum = 0;            //汇流合格总场次
		int passFlood_sum = 0;        //洪水合格总场次
		double DC_sum = 0;            //洪水确定性系数的和

		/**以下为新加入的参数*/
		double total_s_r01 = 0;                //预报值减去实测值的平方和
		double total_s_a01 = 0;                //预报值减去实测均值的平方和
		double total_relaY = 0;                //产流相对误差之和
		double total_absY = 0;                //产流绝对误差之和
		double total_relaQMax = 0;            //洪峰相对误差之和
		int total_QMax_time_differ = 0;        //峰现时间差之和
		double TYTC = 0;                    //N次洪水计算产流与实际产流误差总和

		for(int n=0;n<floodNum;n++) {

			double[] P = ProgramRuntimeData.getP_timeSpan(n);      //降雨
			double[] Q_real = ProgramRuntimeData.getQ_real(n);    //实际流量
			double[] Q_simu = prd.getQ(n);                        //模拟流量

			double[] y0 = prd.getY0(n);            //不透水面积上的直接径流
			double[] yu = prd.getYu(n);            //表层流（即地面径流与壤中流之和）
			double[] yl = prd.getYl(n);            //地下径流

			totalQ_real[n] = 0;
			totalQ_simu[n] = 0;
			totalY_simu[n] = 0;
			totalP[n] = 0;

			double avg_totalQ_real = 0;            //洪水实际流量的均值

			//洪水历时长度,实测洪水历时长度与模拟洪水历时长度不一定相等，故取小值
			int length = Q_real.length<Q_simu.length?Q_real.length:Q_simu.length;

			double[] s_r = new double[length];        //记录每个时间段预报值减去实测值的平方
			double[] s_a = new double[length];        //记录每个时间段预报值减去实测均值的平方
			double total_s_r = 0;                    //预报值减去实测值的平方和
			double total_s_a = 0;                    //预报值减去实测均值的平方和


			/*******************洪量问题***************************/
			for(int j=0;j<length;j++){

				totalQ_real[n] += Q_real[j];                //洪水实际的洪量
				totalQ_simu[n] += Q_simu[j];                //洪水模拟的洪量
				absHL[n] = totalQ_simu[n]-totalQ_real[n];    //洪量绝对误差

			}

			for(int j=0;j<y0.length;j++) {
				totalY_simu[n]  += (y0[j]+yu[j]+yl[j]);    //模拟总径流深
			}
			//yu[j]=NaN,yl[j]=NaN
//			if(isNaN(totalY_simu[n])){
//				System.out.println("totalY_simu[" + n + "]=" +totalY_simu[n]);
//				for(int j=0;j<y0.length;j++) {
//					System.out.println("y0["+ j +"]="+ y0[j] + "yu[" + j +"]=" + yu[j] +"yl[" + j +"]=" +yl[j]);
//				}
//			}

			//此部分为计算确定性系数
			for(int j=0;j<length;j++){

				avg_totalQ_real = totalQ_real[n]/length;

				s_r[j] = Math.pow((Q_simu[j]-Q_real[j]),2);
				s_a[j] = Math.pow((Q_simu[j]-avg_totalQ_real),2);
				total_s_r += s_r[j];
				total_s_a += s_a[j];

				total_s_r01 += s_r[j];
				total_s_a01 += s_a[j];

				/** 《+》 **/  //增加的代码，用于解析调试程序，可删除
//				if(isNaN(total_s_r)){  //Q_simu[j]出现NaN
//					System.out.println("s_r[" + j + "] = " + s_r[j] +" Q_simu[" + j +"]= " +Q_simu[j]);
//				}

			}

			DC[n] = 1-(total_s_r/total_s_a);
			//确保不会出现DC[n]=NaN的情况
			if(isNaN(DC[n])){
//				System.out.println("total_s_r/total_s_a = " + total_s_r/total_s_a);
//				System.out.println("total_s_a = " + total_s_a);
//				System.out.println("total_s_r = " + total_s_r);
				System.out.println("DC  ~5555555555555555555555555555555555555555555555555555555555555555555555555555555555");
//				DC[n] = -8;
			}

			/*****************产流问题**************************/
			totalY_real[n] = totalQ_real[n]*3.6*timeSpan/area;        //各场次实际径流深
			absY[n] = totalY_simu[n]-totalY_real[n];                //产流绝对误差
			relaY[n] = 100*absY[n]/totalY_real[n];                    //产流相对误差

//			System.out.println("n="+n +"  totalY_simu[" + n + "]=" +totalY_simu[n] + "  totalY_real[" + n +"]=" +totalY_real[n] + "  absY[n]=" + absY[n]);
			//abs[n] 有的为NaN,-->totalY_simu[n] 有的为NaN, --> yu[j]=NaN,yl[j]=NaN
			if((Math.abs(relaY[n])<20 && Math.abs(absY[n])<20)||Math.abs(absY[n])<3){
				total_relaY += 0;    //产流合格
			}else{
				total_relaY += Math.abs(relaY[n]) / 100;    //产流不合格
			}

			if(isNaN(total_relaY)){            //这里是否会对解产生影响？
				System.out.println("total_relaY  ~000000000000000000000000000000000000000000000000000000000000000000000000000");
//				total_relaY = 20;
			}
			TYTC += absY[n];

			for(int a=0;a<P.length;a++){

				totalP[n] += P[a];                                    //总降雨量

			}

			alefa[n] =  totalY_real[n]/totalP[n];                    //实测产流系数

			if((Math.abs(relaY[n])<20 && Math.abs(absY[n])<20)||Math.abs(absY[n])<3){

				passY[n] = 1;    //产流合格

			}else{

				passY[n] = 0;    //产流不合格

			}


			/******************洪峰问题************************/
			QMax_real[n] = MathFun.getArrayMax(Q_real);            //实测洪峰
			QMax_simu[n] = MathFun.getArrayMax(Q_simu);            //模拟洪峰
			absQMax[n] = QMax_simu[n]- QMax_real[n];            //洪峰绝对误差
			relaQMax[n] = 100*absQMax[n]/QMax_real[n];            //洪峰相对误差

//			if (Math.abs(relaQMax[n])<=20){
//				total_relaQMax += 0;
//			}else{
//				total_relaQMax += Math.abs(relaQMax[n])/100;
//			}
//			if(isNaN(total_relaQMax)){
//				System.out.println("---------------------------0000000000000000000000000000000000000________________-----------");
//				total_relaQMax = 20;
//			}


			/******************峰现时间问题*********************/
			QMax_real_time[n] = MathFun.getArrayMaxIndex(Q_real);            //实际峰现时间
			QMax_simu_time[n] = MathFun.getArrayMaxIndex(Q_simu);            //模拟峰现时间
			QMax_time_differ[n] = QMax_simu_time[n]-QMax_real_time[n];        //峰现时间差

//			total_QMax_time_differ +=Math.abs(QMax_time_differ[n]);

//			if(Math.abs(relaQMax[n])<20 && ((Math.abs(QMax_time_differ[n])*timeSpan)<=3 ) && DC[n]>=0.7){
//				passQ[n] = 1;   //汇流合格
//			}else{
//				passQ[n] = 0;   //汇流不合格
//			}
			//修改了汇流合格的评断标准
			if(Math.abs(relaQMax[n])<=20
					&& ((Math.abs(QMax_time_differ[n])*timeSpan)<=3||(Math.abs(QMax_time_differ[n])*timeSpan)<=QMax_real_time[n]*timeSpan*0.3)
					&& DC[n]>=0.7){
				passQ[n] = 1;   //汇流合格
				total_relaQMax += 0;
				DC_sum += 1;
				total_QMax_time_differ += 0;

			}else{
				passQ[n] = 0;   //汇流不合格
				total_relaQMax += Math.abs(relaQMax[n])/100;
				DC_sum += DC[n];
				total_QMax_time_differ += Math.abs(QMax_time_differ[n]);

			}

			if(isNaN(total_relaQMax)){
				System.out.println();
				System.out.println("total_relaQMax  ---------------------------0000000000000000000000000000000000000________________-----------");
				total_relaQMax = 20;
				System.out.println();
			}

			/***************洪水模拟是否合格******************/
			if(passY[n]==1&&passQ[n]==1) {
				passFlood[n] = 1;    //该场洪水合格
			}else {
				passFlood[n] = 0;    //该场洪水不合格
			}

			/***************总合格场次******************/
			passY_sum += passY[n];
			passQ_sum += passQ[n];
			passFlood_sum += passFlood[n];
		}

		Result re = new Result();

		re.setTotalY_real(totalY_real);
		re.setTotalY_simu(totalY_simu);
		re.setTotalQ_real(totalQ_real);
		re.setTotalQ_simu(totalQ_simu);
		re.setAbsHL(absHL);
		re.setDC(DC);
		re.setAbsY(absY);
		re.setRelaY(relaY);
		re.setTotalP(totalP);
		re.setAlefa(alefa);
		re.setQMax_real(QMax_real);
		re.setQMax_simu(QMax_simu);
		re.setAbsQMax(absQMax);
		re.setRelaQMax(relaQMax);
		re.setQMax_real_time(QMax_real_time);
		re.setQMax_simu_time(QMax_simu_time);
		re.setQMax_time_differ(QMax_time_differ);
		re.setPassY(passY);
		re.setPassQ(passQ);
		re.setPassFlood(passFlood);
		re.setPassY_sum(passY_sum);
		re.setPassQ_sum(passQ_sum);
		re.setPassFlood_sum(passFlood_sum);
		re.setDC_sum(DC_sum);
		re.setQ(prd.getQ());

		re.setTotal_s_a01(total_s_a01);
		re.setTotal_s_r01(total_s_r01);
		re.setTotal_relaY(total_relaY);
		re.setTotal_relaQMax(total_relaQMax);
		re.setTotal__QMax_time_differ(total_QMax_time_differ);
		re.setTYTC(TYTC);

		return re;
	}
}
