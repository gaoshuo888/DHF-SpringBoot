package com.example.dhf_springboot.model.dhf.tools;

public class MathFun {

	//给定数组，及相应底标返回相应的插值，线性插值
	public static double insertArray(double l,double y[]) {

		int i = (int) l;
		if(i==l) {
			return y[i];
		}else {
			return (y[i+1]-y[i])*(l-i)+y[i];
		}

	}

	//得到一组数组的最大值，
	public static double getArrayMax(double[] arr){

		double max = 0;
		int length = arr.length;
		max = arr[0];
		for (int i = 0; i < length; i++) {
			if (max < arr[i]) {
				max = arr[i];

			}
		}
		return max;

	}
	//得到数组最大值的索引底标
	public static int getArrayMaxIndex(double[] arr){

		double max = 0;
		int index = 0;
		int length = arr.length;
		max = arr[0];
		for (int i = 0; i < length; i++) {
			if (max < arr[i]) {
				max = arr[i];
				index = i;
			}
		}
		return index;

	}

}
