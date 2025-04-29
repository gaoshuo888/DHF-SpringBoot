package com.example.dhf_springboot.model.dhf.data;

import com.example.dhf_springboot.model.dhf.JDBC.JDBCDao;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author 武坤
 *
 */

public class ProgramRuntimeData {            //*****算法慢，尝试调用一次数据库，不要每次重新调用数据库

	private static String[] stcd = null;    //洪水洪号

	static {            //static初始化语句块，和构造器类似
		String query = "SELECT DISTINCT stcd FROM rain_timespan";        //操作数据库语句，query--查询，timespan时段
		stcd = JDBCDao.getString(query);                                //SELECT DISTINCT column_name FROM table_name
	}                                                                    //表名--rain_timespan，列名--stcd

	private static int floodNum = stcd.length;    //洪水场次数

	private static double[][] P_timeSpan = new double[floodNum][];        //时段降雨量
	private static Date[][] P_timeSpan_date = new Date[floodNum][];        //时段降雨量对应的时间
	private static double[][] P_day = new double[floodNum][];            //日降雨量
	private static Date[][] P_day_date = new Date[floodNum][];            //日降雨量对应的时间
	private static double[][] ER_day = new double[floodNum][];            //日降雨对应的大水体蒸散发能力
	private static double[][] ER_timeSpan = new double[floodNum][];        //时段降雨对应的大水体蒸散发能力
	private static double[][] Q_real = new double[floodNum][];            //实测洪水流量？

	private double[][] y0 = new double[floodNum][];    //不透水面积上的直接径流
	private double[][] yu = new double[floodNum][];    //表层流（即地面径流与壤中流之和）
	private double[][] y = new double[floodNum][];    //总净流（指表层与地下径流）
	private double[][] yl = new double[floodNum][];    //地下径流
	private double[][] Q = new double[floodNum][];    //模拟洪水流量

	private double[] Sa = new double[floodNum];    //流域初始表层蓄水量
	private double[] Ua = new double[floodNum];    //流域初始下层蓄水量
	private double[] Da = new double[floodNum];    //流域初始下层蓄水量与地下水库蓄水量之和


	static {

		for(int i=0;i<floodNum;i++) {

			String str = stcd[i];
			//sql--查询数据库，可以分析得到武坤建立的数据库表格的各列名，建立自己的数据库
			// 表名--rain_timespan
			String sql1 = "SELECT p_timespan as p FROM rain_timespan Where stcd = ? ORDER BY date_timespan asc";
			String sql2 = "SELECT date_timespan as date FROM rain_timespan Where stcd = ? ORDER BY date_timespan asc";
			String sql3 = "SELECT e_timespan as e FROM rain_timespan Where stcd = ? ORDER BY date_timespan asc";
			String sql4 = "SELECT q_timespan as q FROM rain_timespan Where stcd = ? ORDER BY date_timespan asc";

			P_timeSpan[i] = JDBCDao.getDouble(sql1,str);    //str 对应 sql1 中的 ？
			P_timeSpan_date[i] = JDBCDao.getDate(sql2,str);

//			if( JDBCDao.getDouble(sql3,str).length!=0) {     //数据库中没有蒸发数据
//				ER_timeSpan[i] = JDBCDao.getDouble(sql3,str);
//				System.out.println("执行ER_timeSpan" +ER_timeSpan[i][0] );
//			}

			Q_real[i] = JDBCDao.getDouble(sql4,str);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String end = formatter.format(P_timeSpan_date[i][0]);    //end--rain_timeSpan表中降雨开始时间。
			String year = end.substring(0, 4);
			String begin = year+"-01-01 00:00:00";        //获取的数据：begin--每场洪水所在的年份的1月1日。

//			System.out.println("begin= "+begin);	//洪水发生之前的日降雨资料
//			System.out.println("end= "+end);

			String sql5 = "SELECT p_day as p FROM rain_day WHERE date_day BETWEEN ? AND ? ORDER BY date_day asc";//按date_day排序产生的数组
			//as p 和前面的命名重复了，在不同的表中，表名--rain_day
			//表中采用where date_day---yyyy-MM-dd，判断不同的洪水。也可以有stcd列，？？？？？
			String sql6 = "SELECT date_day as date FROM rain_day WHERE date_day BETWEEN ? AND ? ORDER BY date_day asc";
			String sql7 = "SELECT e_day as e FROM rain_day WHERE date_day BETWEEN ? AND ? ORDER BY date_day asc";
			//sql5~7,表rain_day用来计算前期影响雨量Pa=含水量W=Sa+Ua+Da

			P_day[i] = JDBCDao.getDouble(sql5,begin,end);    //double[] 数组
			P_day_date[i] = JDBCDao.getDate(sql6,begin,end);
//			if( JDBCDao.getDouble(sql7,begin,end).length!=0 ) {
////			这个地方无论执行与否，ER_day[i][j]等于0
//				ER_day[i] = JDBCDao.getDouble(sql7,begin,end);
//				System.out.println("执行ER_day=" +ER_day[i][0] );
//			}
		}
	}

	public ProgramRuntimeData() {
		super();
	}


	public ProgramRuntimeData(Parameter par) {        //构造器

		super();

		GetEM gem = this.setEM(par);

		for(int i=0;i<floodNum;i++) {

//			System.out.println("执行");

			this.setER_day(gem, i);
			this.setER_timeSpan(gem, i);

//			if(ER_day[i] == null) {//ER_day[i]是数组，而不是一个数
//				this.setER_day(gem, i);
//			}
//
//			if(ER_timeSpan[i] == null) {//这里提取了Par0的数据，而率定的时候没有执行
//				System.out.print(ER_timeSpan[i]);//null
//				this.setER_timeSpan(gem, i);
//				System.out.print(ER_timeSpan[i]);//->指向地址，ER_timeSpan是static类变量导致后面不为null
//				System.out.println("执行");
//
//			}

		}
	}


	public static double[] getP_timeSpan(int i) {
		return P_timeSpan[i];
	}

	public static void setP_timeSpan(int i,double[] p_timeSpan) {
		P_timeSpan[i] = p_timeSpan;
	}


	public static Date[] getP_timeSpan_date(int i) {
		return P_timeSpan_date[i];
	}

	public static void setP_timeSpan_date(int i,Date[] p_timeSpan_date) {
		P_timeSpan_date[i] = p_timeSpan_date;
	}


	public static double[] getP_day(int i) {
		return P_day[i];
	}

	public static void setP_day(int i,double[] p_day) {
		P_day[i] = p_day;
	}


	public static Date[] getP_day_date(int i) {
		return P_day_date[i];
	}

	public static void setP_day_date(int i,Date[] p_day_date) {
		P_day_date[i] = p_day_date;
	}


	public static double[] getQ_real(int i) {
		return Q_real[i];
	}

	public static void setQ_real(int i,double[] q_real) {
		Q_real[i] = q_real;
	}


	public static double[] getER_day(int i) {
		return ER_day[i];
	}

	public static void setER_day(int i,double[] eR_day) {
		ER_day[i] = eR_day;
	}

	public static double[] getER_timeSpan(int i) {
		return ER_timeSpan[i];
	}

	public static void setER_timeSpan(int i,double[] eR_timeSpan) {
		ER_timeSpan[i] = eR_timeSpan;
	}


	public static String getStcd(int n) {
		return stcd[n];
	}

	public static void setStcd(int n,String stcd) {
		ProgramRuntimeData.stcd[n] = stcd;
	}


	public static int getFloodNum() {
		return floodNum;
	}

	public static void setFloodNum(int floodNum) {
		ProgramRuntimeData.floodNum = floodNum;
	}

	public double[] getQ(int i) {
		return Q[i];
	}

	public void setQ(int i,double[] q) {
		Q[i] = q;
	}

	public double[][] getQ() {
		return Q;
	}


	public double[] getY0(int i) {
		return y0[i];
	}

	public void setY0(int i,double[] y0) {
		this.y0[i] = y0;
	}


	public double[] getYu(int i) {
		return yu[i];
	}

	public void setYu(int i,double[] yu) {
		this.yu[i] = yu;
	}


	public double[] getY(int i) {
		return y[i];
	}

	public void setY(int i,double[] y) {
		this.y[i] = y;
	}


	public double[] getYl(int i) {
		return yl[i];
	}

	public void setYl(int i,double[] yl) {
		this.yl[i] = yl;
	}


	public double getSa(int i) {
		return Sa[i];
	}

	public void setSa(int i,double sa) {
		Sa[i] = sa;
	}


	public double getUa(int i) {
		return Ua[i];
	}

	public void setUa(int i,double ua) {
		Ua[i] = ua;
	}

	public double getDa(int i) {
		return Da[i];
	}

	public void setDa(int i,double da) {
		Da[i] = da;
	}

	public static String[] getStcd() {
		return stcd;
	}

	public static void setStcd(String[] stcd) {
		ProgramRuntimeData.stcd = stcd;
	}

	private GetEM setEM(Parameter par) {     //返回类型GetEM对象

		double[] E1 = {par.getE1(),par.getE10(),par.getE11()};//这个地方有错误，E1是一月小雨的蒸发，后面两个应该分别是中雨和大雨的蒸发
//		System.out.println("E1[0]=" + E1[0] + "E1[2]=" + E1[1] + "E1[3]=" + E1[2]);
		double[] E2 = {par.getE2(),par.getE10(),par.getE11()};//没必要更改！！
		double[] E3 = {par.getE3(),par.getE10(),par.getE11()};//在本程序中E1~E3没用上，E4~E9 4~9月P<5mm的蒸发,
		double[] E4 = {par.getE4(),par.getE10(),par.getE11()};//E10：4~9月5mm<P<40mm的蒸发，E11：4~9月P>40mm的蒸发
		double[] E5 = {par.getE5(),par.getE10(),par.getE11()};
		double[] E6 = {par.getE6(),par.getE10(),par.getE11()};
		double[] E7 = {par.getE7(),par.getE10(),par.getE11()};
		double[] E8 = {par.getE8(),par.getE10(),par.getE11()};
		double[] E9 = {par.getE9(),par.getE10(),par.getE11()};

		GetEM gem = new GetEM(E1,E2,E3,E4,E5,E6,E7,E8,E9);

		return gem;
	}

	private void setER_day(GetEM gem,int i) {

		ER_day[i] =  new double[P_day[i].length];

		for(int j=0;j<P_day[i].length;j++) {
			ER_day[i][j] = gem.f_emt(P_day[i][j],P_day_date[i][j],24);

		}
	}

	private void setER_timeSpan(GetEM gem,int i) {

		ER_timeSpan[i] =  new double[P_timeSpan[i].length];

		for(int j=0;j<P_timeSpan[i].length;j++) {
			ER_timeSpan[i][j] = gem.f_emt(P_timeSpan[i][j],P_timeSpan_date[i][j],Parameter.getTimeSpan());

		}
	}


	public double[] getSa() {
		return Sa;
	}

	public void setSa(double[] sa) {
		Sa = sa;
	}

	public double[] getUa() {
		return Ua;
	}

	public void setUa(double[] ua) {
		Ua = ua;
	}

	public double[] getDa() {
		return Da;
	}

	public void setDa(double[] da) {
		Da = da;
	}


}




