package com.example.dhf_springboot.model.dhf.data;

public class Result {		//定义了一些参数，并将参数封装，使用数组存储不同场次洪水的结果。

	double[] totalY_real;	//实际总径流深
	double[] totalY_simu;	//模拟总径流深
	double[] totalQ_real;	//洪水实际的洪量
	double[] totalQ_simu;	//洪水模拟的洪量
	double[] absHL;			//洪量绝对误差=模拟的洪量-实际的洪量
	double[] DC;			//确定性系数
	double[] absY;			//产流绝对误差
	double[] relaY;			//产流相对误差
	double[] totalP;		//总降雨量
	double[] alefa;			//实测产流系数
	double[] QMax_real;		//实际洪峰
	double[] QMax_simu;		//模拟洪峰
	double[] absQMax;		//洪峰绝对误差
	double[] relaQMax;		//洪峰相对误差
	//注：利用洪峰在流量数组中的角标表示，在时间间隔一定的情况下，即为峰现时间
	int[] QMax_real_time;	//实际峰现时间
	int[] QMax_simu_time;	//模拟峰现时间
	int[] QMax_time_differ;	//峰现时间差

	int[] passY;			//产流合格
	int[] passQ;			//汇流合格
	int[] passFlood;		//洪水合格

	int passY_sum;			//产流合格总场次
	int passQ_sum;			//汇流合格总场次
	int passFlood_sum;		//洪水合格总场次
	double DC_sum;			//洪水确定性系数的和

//    Parameter par;			//结果所对应的模型参数

	double[][] Q;			//洪水模拟结果

	/**以下为新参数*/
	double total_s_r01 = 0;		//Q预报值减去实测值的平方和
	double total_s_a01 = 0;		//Q预报值减去实测均值的平方和
	double total_relaY = 0;		//产流相对误差之和
	double total_relaQMax = 0;	//洪峰相对误差之和
	int total__QMax_time_differ = 0;    //峰现时间差之和
	double TYTC = 0;            //N次洪水计算产流与实际产流误差总和

	public double getTotal_s_r01() {
		return total_s_r01;
	}

	public void setTotal_s_r01(double total_s_r01) {
		this.total_s_r01 = total_s_r01;
	}

	public double getTotal_s_a01() {
		return total_s_a01;
	}

	public void setTotal_s_a01(double total_s_a01) {
		this.total_s_a01 = total_s_a01;
	}

	public double getTotal_relaY() {
		return total_relaY;
	}

	public void setTotal_relaY(double total_relaY) {
		this.total_relaY = total_relaY;
	}

	public double getTotal_relaQMax() {
		return total_relaQMax;
	}

	public void setTotal_relaQMax(double total_relaQMax) {
		this.total_relaQMax = total_relaQMax;
	}

	public int getTotal__QMax_time_differ() {
		return total__QMax_time_differ;
	}

	public void setTotal__QMax_time_differ(int total__QMax_time_differ) {
		this.total__QMax_time_differ = total__QMax_time_differ;
	}

	public double getTYTC() {
		return TYTC;
	}

	public void setTYTC(double TYTC) {
		this.TYTC = TYTC;
	}

	/*****封装******/
	public double[] getTotalY_real() {
		return totalY_real;
	}
	public void setTotalY_real(double[] totalY_real) {
		this.totalY_real = totalY_real;
	}


	public double[] getTotalY_simu() {
		return totalY_simu;
	}
	public void setTotalY_simu(double[] totalY_simu) {
		this.totalY_simu = totalY_simu;
	}


	public double[] getTotalQ_real() {
		return totalQ_real;
	}
	public void setTotalQ_real(double[] totalQ_real) {
		this.totalQ_real = totalQ_real;
	}


	public double[] getTotalQ_simu() {
		return totalQ_simu;
	}
	public void setTotalQ_simu(double[] totalQ_simu) {
		this.totalQ_simu = totalQ_simu;
	}


	public double[] getAbsHL() {
		return absHL;
	}
	public void setAbsHL(double[] absHL) {
		this.absHL = absHL;
	}


	public double[] getDC() {
		return DC;
	}
	public void setDC(double[] dC) {
		DC = dC;
	}


	public double[] getAbsY() {
		return absY;
	}
	public void setAbsY(double[] absY) {
		this.absY = absY;
	}


	public double[] getRelaY() {
		return relaY;
	}
	public void setRelaY(double[] relaY) {
		this.relaY = relaY;
	}


	public double[] getTotalP() {
		return totalP;
	}
	public void setTotalP(double[] totalP) {
		this.totalP = totalP;
	}


	public double[] getAlefa() {
		return alefa;
	}
	public void setAlefa(double[] alefa) {
		this.alefa = alefa;
	}


	public double[] getQMax_real() {
		return QMax_real;
	}
	public void setQMax_real(double[] qMax_real) {
		QMax_real = qMax_real;
	}


	public double[] getQMax_simu() {
		return QMax_simu;
	}
	public void setQMax_simu(double[] qMax_simu) {
		QMax_simu = qMax_simu;
	}


	public double[] getAbsQMax() {
		return absQMax;
	}
	public void setAbsQMax(double[] absQMax) {
		this.absQMax = absQMax;
	}


	public double[] getRelaQMax() {
		return relaQMax;
	}
	public void setRelaQMax(double[] relaQMax) {
		this.relaQMax = relaQMax;
	}


	public int[] getQMax_real_time() {
		return QMax_real_time;
	}
	public void setQMax_real_time(int[] qMax_real_time) {
		QMax_real_time = qMax_real_time;
	}


	public int[] getQMax_simu_time() {
		return QMax_simu_time;
	}
	public void setQMax_simu_time(int[] qMax_simu_time) {
		QMax_simu_time = qMax_simu_time;
	}


	public int[] getQMax_time_differ() {
		return QMax_time_differ;
	}
	public void setQMax_time_differ(int[] qMax_time_differ) {
		QMax_time_differ = qMax_time_differ;
	}


	public int[] getPassY() {
		return passY;
	}
	public void setPassY(int[] passY) {
		this.passY = passY;
	}


	public int[] getPassQ() {
		return passQ;
	}
	public void setPassQ(int[] passQ) {
		this.passQ = passQ;
	}


	public int[] getPassFlood() {
		return passFlood;
	}
	public void setPassFlood(int[] passFlood) {
		this.passFlood = passFlood;
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
	public void setDC_sum(double dC_sum) {
		DC_sum = dC_sum;
	}

	public double[][] getQ() {
		return Q;
	}
	public void setQ(double[][] q) {
		Q = q;
	}

//	public Parameter getPar() {
//		return par;
//	}
//	public void setPar(Parameter par) {
//		this.par = par;
//	}

}
